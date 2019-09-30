package hr.fer.zemris.java.raytracer.model;

/**
 * A class that represents a sphere.
 *
 * @author Mateo Imbrišak
 */

public class Sphere extends GraphicalObject {

    /**
     * Keeps the center of this {@code Sphere}.
     */
    private Point3D center;

    /**
     * Keeps the radius of this {@code Sphere}.
     */
    private double radius;

    /**
     * Red diffusion component.
     */
    private double kdr;

    /**
     * Green diffusion component.
     */
    private double kdg;

    /**
     * Blue diffusion component.
     */
    private double kdb;

    /**
     * Red reflection component.
     */
    private double krr;

    /**
     * Green reflection component.
     */
    private double krg;

    /**
     * Blue reflection component.
     */
    private double krb;

    /**
     * Shininess factor.
     */
    private double krn;

    /**
     * Default constructor that assigns all values.
     *
     * @param center of this {@code Sphere}.
     * @param radius of this {@code Sphere}.
     * @param kdr red diffusion component.
     * @param kdg green diffusion component.
     * @param kdb blue diffusion component.
     * @param krr red reflection component.
     * @param krg green reflection component.
     * @param krb blue reflection component.
     * @param krn shininess factor.
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg,
                  double kdb, double krr, double krg, double krb, double krn) {
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    /**
     * Finds the closest intersection of this
     * {@code Sphere} and the given {@code Ray}.
     *
     * @param ray checked for intersection.
     *
     * @return a {@code RayIntersection} if it has been found,
     * otherwise {@code null}.
     */
    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {

        double b = ray.direction.scalarProduct(ray.start.sub(center));
        double delta = b * b - Math.pow((ray.start.sub(center)).norm(), 2) + radius * radius;

        if (delta < 0) {
            return null;
        }

        double t = -1 * b - Math.sqrt(delta); // first root of ax^2 + bx + c = 0

        Point3D intersection = ray.start.add(ray.direction.scalarMultiply(t));


        return new RayIntersection(intersection, t, true) {
            @Override
            public Point3D getNormal() {
                return intersection.sub(center).normalize();
            }

            @Override
            public double getKdr() {
                return kdr;
            }

            @Override
            public double getKdg() {
                return kdg;
            }

            @Override
            public double getKdb() {
                return kdb;
            }

            @Override
            public double getKrr() {
                return krr;
            }

            @Override
            public double getKrg() {
                return krg;
            }

            @Override
            public double getKrb() {
                return krb;
            }

            @Override
            public double getKrn() {
                return krn;
            }
        };
    }
}
