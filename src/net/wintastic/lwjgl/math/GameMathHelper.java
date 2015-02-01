package net.wintastic.lwjgl.math;

import net.wintastic.util.math.MathHelper;
import org.lwjgl.util.vector.Vector2f;

import java.util.Collection;
import java.util.List;

public class GameMathHelper {

    /**
     * This method calculates the scalar distance between two points.
     *
     * @param firstPoint  The main coordinate from which to calculate the distance.
     * @param secondPoint The second coordinate from which to calculate the distance.
     */
    public static float getDistance(Vector2f firstPoint, Vector2f secondPoint) {
        return (float) Math.sqrt(
                Math.pow(Math.abs(firstPoint.getX() - secondPoint.getX()), 2) +
                        Math.pow(Math.abs(firstPoint.getY() - secondPoint.getY()), 2));
    }

    /**
     * Adds a vector to another
     *
     * @param v1 The base vector
     * @param v2 The vector to add
     */
    public static void addVector(Vector2f v1, Vector2f v2) {
        v1.set(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Returns a new vector multiplied by a scalar
     *
     * @param v1 The base vector
     * @param s  The scalar
     */
    public static Vector2f multiplyVectorByScalar(Vector2f v1, float s) {
        return new Vector2f(v1.x * s, v1.y * s);
    }

    public static boolean vector2fEquals(Vector2f v1, Vector2f v2) {
        return MathHelper.floatEquals(v1.x, v2.x) && MathHelper.floatEquals(v1.y, v2.y);
    }


    /**
     * Asserts a Vector2f point is contained within a two dimensional boundary
     *
     * @param vector The vector whose position will be clamped.
     * @param minX   The minimum X value the Vector can have.
     * @param maxX   The maximum X value the Vector can have.
     * @param minY   The minimum Y value the Vector can have.
     * @param maxY   The maximum Y value the Vector can have.
     * @return The clamped Vector, whose position is contained within the boundary.
     */
    public static void clamp(Vector2f vector, float minX, float maxX, float minY, float maxY) {
        vector.set(MathHelper.clamp(vector.x, minX, maxX), MathHelper.clamp(vector.y, minY, maxY));
    }

    /**
     * Linearly interpolates a fist point towards the location of a second using a speed modifier.
     *
     * @param start The start point to use.
     * @param end   The end point to use.
     * @param speed The speed at which to interpolate the first point towards the second.
     */
    public static void linearStep(Vector2f start, Vector2f end, Vector2f speed) {
        start.set(MathHelper.lerp(start.x, end.getX(), speed.getX()),
                MathHelper.lerp(start.y, end.getY(), speed.getY()));
    }

    /**
     * Slowly moves a fist point towards the location of a second using a speed modifier.
     *
     * @param start The start point to use.
     * @param end   The end point to use.
     * @param speed The speed at which to move the first point towards the second.
     */
    public static void lerp(Vector2f start, Vector2f end, Vector2f speed) {
        start.set(MathHelper.lerp(start.x, end.getX(), speed.getX()),
                MathHelper.lerp(start.y, end.getY(), speed.getY()));
    }

    /**
     * Slowly moves a fist point towards the location of a second using a speed modifier.
     *
     * @param start The start point to use.
     * @param end   The end point to use.
     * @param speed The speed at which to move the first point towards the second.
     */
    public static void lerp(Vector2f start, Vector2f end, float speed) {
        float coeff = speed / GameMathHelper.getDistance(start, end);
        lerp(start, end, new Vector2f(Math.abs(coeff * (end.x - start.x)), Math.abs(coeff * (end.y - start.y))));
    }

    public static boolean collectionContainsVector2f(Collection<Vector2f> l, Vector2f v) {
        for (Vector2f e : l) {
            if (vector2fEquals(e, v))
                return true;
        }
        return false;
    }

    public static int indexOfVector2f(List<Vector2f> l, Vector2f v) {
        for (int i = 0; i < l.size(); i++) {
            if (vector2fEquals(l.get(i), v))
                return i;
        }
        return -1;
    }

    public static Vector2f randomVector(float minX, float maxX, float minY, float maxY) {
        return new Vector2f(MathHelper.randomFloat(minX, maxX), MathHelper.randomFloat(minY, maxY));
    }

}
