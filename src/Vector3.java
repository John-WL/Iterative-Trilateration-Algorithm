import java.util.Objects;

/**
 * A simple 3d vector class with the most essential operations.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can add to it as much
 * as you want, or delete it.
 */
public class Vector3 {

    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3 plus(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 minus(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 scaled(double scale) {
        return new Vector3(x * scale, y * scale, z * scale);
    }

    public Vector3 scaled(double scaleX, double scaleY, double scaleZ) {
        return new Vector3(x * scaleX, y * scaleY, z * scaleZ);
    }

    /**
     * If magnitude is negative, we will return a vector facing the opposite direction.
     */
    public Vector3 scaledToMagnitude(double magnitude) {
        if (isZero()) {
            return new Vector3();
        }
        double scaleRequired = magnitude / magnitude();
        return scaled(scaleRequired);
    }

    public double distance(Vector3 other) {
        double xDiff = x - other.x;
        double yDiff = y - other.y;
        double zDiff = z - other.z;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public double magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3 normalized() {

        if (isZero()) {
            return new Vector3();
        }
        return this.scaled(1 / magnitude());
    }

    public double dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    public double angle(Vector3 v) {
        double mag2 = magnitudeSquared();
        double vmag2 = v.magnitudeSquared();
        double dot = dotProduct(v);
        return Math.acos(dot / Math.sqrt(mag2 * vmag2));
    }

    public Vector3 crossProduct(Vector3 v) {
        double tx = y * v.z - z * v.y;
        double ty = z * v.x - x * v.z;
        double tz = x * v.y - y * v.x;
        return new Vector3(tx, ty, tz);
    }

    public Vector3 projectOnto(Vector3 vectorToProjectOnto) {
        return vectorToProjectOnto.scaled(this.dotProduct(vectorToProjectOnto)/vectorToProjectOnto.magnitudeSquared());
    }

    public double angleWith(Vector3 vector) {
        double cosine = this.dotProduct(vector)/(this.magnitude()*vector.magnitude());
        return Math.acos(cosine);
    }

    private Vector3 triangleEdgePosition(final Vector3 start, final Vector3 dir, final Vector3 p) {
        final double u = clamp(p.minus(start).dotProduct(dir)/dir.dotProduct(dir), 0, 1);
        return start.plus(dir.scaled(u));
    }

    private double clamp(final double valueToClamp, final double min, final double max) {
        return Math.max(min, Math.min(max, valueToClamp));
    }

    @Override
    public String toString() {
        return "[ x:" + this.x + ", y:" + this.y + ", z:" + this.z + " ]";
    }

    @Override
    public int hashCode() {
        return Objects.hash((int)x, (int)y, (int)z);
    }
}
