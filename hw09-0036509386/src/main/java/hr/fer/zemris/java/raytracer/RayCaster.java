package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that represents a normal ray caster.
 *
 * @author Mateo Imbrišak
 */

public class RayCaster {

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
             * Used to avoid false-positive obstruction.
             */
            private static final double ACCEPTABLE_DIFFERENCE = 1E-10;

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

                short[] rgb = new short[3];
                int offset = 0;
                for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) {
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

                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
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
            private void tracer(Scene scene, Ray ray, short[] rgb) {
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
            private void determineColorFor(Scene scene, RayIntersection intersection, short[] rgb, Ray ray) {
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
            private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
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
        };
    }
}
