package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A {@code RayCasterParallel} that moves the
 * camera around the generated scene.
 *
 * @author Mateo Imbrišak
 */

public class RayCasterParallel2 {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        RayTracerViewer.show(
            getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30 );
    }

    /**
     * Generates an animator that moves the camera.
     *
     * @return a new {@code IRayTracerAnimator}.
     */
    private static IRayTracerAnimator getIRayTracerAnimator() {
        return new IRayTracerAnimator() {
            long time;

            @Override
            public void update(long deltaTime) {
                time += deltaTime;
            }

            @Override
            public Point3D getViewUp() { // fixed in time
                return new Point3D(0,0,10);
            }

            @Override
            public Point3D getView() { // fixed in time
                 return new Point3D(-2,0,-0.5);
            }

            @Override
            public long getTargetTimeFrameDuration() {
                return 150; // redraw scene each 150 milliseconds
            }

            @Override
            public Point3D getEye() { // changes in time
                double t = (double)time / 10000 * 2 * Math.PI;
                double t2 = (double)time / 5000 * 2 * Math.PI;
                double x = 50*Math.cos(t);
                double y = 50*Math.sin(t);
                double z = 30*Math.sin(t2);
                return new Point3D(x,y,z);
            }
        };
    }

    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {
            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp,
                                double horizontal, double vertical, int width,
                                int height, long requestNo,
                                IRayTracerResultObserver observer,
                                AtomicBoolean cancel) {

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

                Scene scene = RayTracerViewer.createPredefinedScene2();

                ForkJoinPool pool = new ForkJoinPool();
                pool.invoke(new RayCasterParallel.RayCasterJob(0, height, width, height, horizontal, vertical,
                        scene, red, green, blue, eye, view, xAxis, yAxis, zAxis, screenCorner, cancel));
                pool.shutdown();

                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }
}
