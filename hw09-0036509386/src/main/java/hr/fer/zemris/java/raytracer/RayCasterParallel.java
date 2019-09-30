package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that represents a parallel
 * {@code RayCaster}.
 *
 * @author Mateo Imbrišak
 */

public class RayCasterParallel {

    /**
     * Used to avoid false-positive obstruction.
     */
    private static final double ACCEPTABLE_DIFFERENCE = 1E-10;

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10,0,0),
                new Point3D(0,0,0),
                new Point3D(0,0,10),
                20, 20);
    }

    /**
     * Provides the producer used to compute
     * ray tracing.
     *
     * @return a new producer.
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {

            /**
             * Used to compute the colors of visible objects.
             *
             * @param eye position of the viewer.
             * @param view where the viewer is looking at.
             * @param viewUp direction of "up".
             * @param horizontal step.
             * @param vertical step.
             * @param width of the screen.
             * @param height of the screen.
             * @param requestNo used to identify the request.
             * @param observer that receives the processed data.
             * @param cancel used to abort the operation.
             */
            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp,
                                double horizontal, double vertical, int width, int height,
                                long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

                System.out.println("Započinjem izračune...");
                short[] red = new short[width*height];
                short[] green = new short[width*height];
                short[] blue = new short[width*height];

                Point3D vuv = viewUp.normalize();

                Point3D zAxis = view.sub(eye).normalize();
                Point3D yAxis = vuv.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vuv))).normalize();
                Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

                Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
                        .add(yAxis.scalarMultiply(vertical / 2));

                Scene scene = RayTracerViewer.createPredefinedScene();

                ForkJoinPool pool = new ForkJoinPool();
                pool.invoke(new RayCasterJob(0, height, width, height, horizontal, vertical,
                        scene, red, green, blue, eye, view, xAxis, yAxis, zAxis, screenCorner, cancel));
                pool.shutdown();

                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }

    /**
     * Used internally to trace a ray.
     *
     * @param scene where the ray is.
     * @param ray being traced.
     * @param rgb colors to be used for the
     *            first object it comes in contact
     *            with.
     */
    private static void tracer(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = findClosestIntersection(scene, ray);

        if (closest == null) {
            return;
        }

        determineColorFor(scene, closest, rgb, ray);
    }

    /**
     * Computes the colors to be used on the objects
     * that intersect the given ray.
     *
     * @param scene where the objects are.
     * @param intersection where the given ray
     *                     intersects an object.
     * @param rgb stores data about the colors.
     * @param ray intersecting an object.
     */
    private static void determineColorFor(Scene scene, RayIntersection intersection, short[] rgb, Ray ray) {
        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        for (LightSource ls : scene.getLights()) {
            Ray r = Ray.fromPoints(ls.getPoint(), intersection.getPoint());

            RayIntersection closest = findClosestIntersection(scene, r);

            if (closest == null ||
                    Math.abs(closest.getPoint().difference(closest.getPoint(),ls.getPoint()).norm()
                            - intersection.getPoint().difference(intersection.getPoint(), ls.getPoint()).norm())
                            > ACCEPTABLE_DIFFERENCE) {
                continue;
            }

            Point3D l = intersection.getPoint().difference(ls.getPoint(), intersection.getPoint()).normalize();
            Point3D n = intersection.getNormal();

            double diffusionCoefficient = Math.max(n.scalarProduct(l), 0);

            Point3D v = intersection.getPoint().difference(ray.start, intersection.getPoint()).normalize();
            Point3D projection = n.scalarMultiply(l.scalarProduct(n));
            Point3D b = l.sub(projection);
            Point3D r1 = projection.add(b.negate()).normalize();

            double reflectionCoefficient = Math.pow(Math.max(r1.scalarProduct(v), 0), intersection.getKrn());

            rgb[0] += (intersection.getKdr() * diffusionCoefficient +
                    intersection.getKrr() * reflectionCoefficient) * ls.getR();
            rgb[1] += (intersection.getKdg() * diffusionCoefficient +
                    intersection.getKrg() * reflectionCoefficient) * ls.getG();
            rgb[2] += (intersection.getKdb() * diffusionCoefficient +
                    intersection.getKrb() * reflectionCoefficient) * ls.getB();
        }
    }

    /**
     * Finds the closest intersection for the given {@code Ray}
     * on the given {@code Scene}.
     *
     * @param scene to be matched.
     * @param ray being checked for intersections.
     *
     * @return closest {@code RayIntersection} if it exists,
     * otherwise {@code null}.
     */
    private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
        RayIntersection intersection = null;

        for (GraphicalObject object : scene.getObjects()) {
            RayIntersection found = object.findClosestRayIntersection(ray);

            if (found != null) {
                intersection = (intersection == null) ? found :
                        (intersection.getDistance() < found.getDistance() ? intersection : found);
            }
        }

        return intersection;
    }

    /**
     * Used internally to compute the colors to be used in the scene.
     */
     static class RayCasterJob extends RecursiveAction {
        private static final long serialVersionUID = 1L;

        /**
         * Number of y axis lines calculated at once.
         */
        private static final int THRESHOLD = 16;

        /**
         * Starting y axis.
         */
        private int minY;

        /**
         * Ending y axis.
         */
        private int maxY;

        /**
         * Width of the screen.
         */
        private int width;

        /**
         * Height of the screen.
         */
        private int height;

        /**
         * Keeps the horizontal step.
         */
        private double horizontal;

        /**
         * Keeps the vertical step.
         */
        private double vertical;

        /**
         * Scene where the objects are.
         */
        private Scene scene;

        /**
         * Keeps data for red component.
         */
        private short[] red;

        /**
         * Keeps data for green component.
         */
        private short[] green;

        /**
         * Keeps data for blue component.
         */
        private short[] blue;

        /**
         * Keeps the position of the viewer.
         */
        private Point3D eye;

        /**
         * Keeps where the viewer is looking at.
         */
        private Point3D view;

        /**
         * Keeps the x axis.
         */
        private Point3D xAxis;

        /**
         * Keeps the y axis.
         */
        private Point3D yAxis;

        /**
         * Keeps the z axis.
         */
        private Point3D zAxis;

        /**
         * Location of the screen's corner.
         */
        private Point3D screenCorner;

        /**
         * Used to abort the operation.
         */
        private AtomicBoolean cancel;

        /**
         * Default constructor that assigns all values.
         *
         * @param minY starting y axis.
         * @param maxY last y axis.
         * @param width of the screen.
         * @param height of the screen.
         * @param horizontal step.
         * @param vertical step.
         * @param scene where the objects are.
         * @param red keeps data for color red.
         * @param green keeps data for color green.
         * @param blue keeps data for color blue.
         * @param eye position of the viewer.
         * @param view where the viewer is looking at.
         * @param xAxis of the scene.
         * @param yAxis of the scene.
         * @param zAxis of the scene.
         * @param ScreenCorner corner of the screen.
         * @param cancel used to abort the action.
         */
        public RayCasterJob(int minY, int maxY, int width, int height,
                            double horizontal, double vertical, Scene scene,
                            short[] red, short[] green, short[] blue,
                            Point3D eye, Point3D view, Point3D xAxis, Point3D yAxis, Point3D zAxis,
                            Point3D ScreenCorner, AtomicBoolean cancel) {
            this.minY = minY;
            this.maxY = maxY;
            this.width = width;
            this.height = height;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.scene = scene;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.eye = eye;
            this.view = view;
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.zAxis = zAxis;
            this.screenCorner = ScreenCorner;
            this.cancel = cancel;
        }

        /**
         * Used to split the workload until
         * the {@code THRESHOLD} has been reached
         * by calling {@link #computeDirect()}.
         */
        @Override
        protected void compute() {
            if (maxY - minY + 1 <= THRESHOLD) {
                computeDirect();
                return;
            }

            invokeAll(new RayCasterJob(minY, minY + (maxY - minY) / 2, width, height, horizontal, vertical,
                            scene, red, green, blue, eye, view, xAxis, yAxis, zAxis, screenCorner, cancel),
                    new RayCasterJob(minY + (maxY - minY) / 2, maxY, width, height, horizontal, vertical,
                            scene, red, green, blue, eye, view, xAxis, yAxis, zAxis, screenCorner, cancel));
        }

        /**
         * Computes the given data.
         */
        public void computeDirect() {
            short[] rgb = new short[3];
            int offset = minY * width;
            for(int y = minY; y < maxY && !cancel.get(); y++) {
                for(int x = 0; x < width && !cancel.get(); x++) {
                    Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
                            .sub(yAxis.scalarMultiply(vertical * y / (height - 1)));
                    Ray ray = Ray.fromPoints(eye, screenPoint);

                    tracer(scene, ray, rgb);

                    red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                    green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                    blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

                    offset++;
                }
            }
        }
    }
}
