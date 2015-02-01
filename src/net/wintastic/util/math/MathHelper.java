package net.wintastic.util.math;

import java.util.Random;

public class MathHelper {

    /**
     * [FLOAT CLAMP] Asserts a provided value is contained within the boundaries.
     *
     * @param value The value to be clamped.
     * @param min   The minimum bound for the clamp operation.
     * @param max   The maximum bound for the clamp operation.
     * @return The result of the clamp operation.
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) value = min;
        else if (value > max) value = max;
        return value;
    }

    /**
     * [INT CLAMP] Asserts a provided value is contained within the boundaries.
     *
     * @param value The value to be clamped.
     * @param min   The minimum bound for the clamp operation.
     * @param max   The maximum bound for the clamp operation.
     * @return The result of the clamp operation.
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) value = min;
        else if (value > max) value = max;
        return value;
    }

    /**
     * [DOUBLE CLAMP] Asserts a provided value is contained within the boundaries.
     *
     * @param value The value to be clamped.
     * @param min   The minimum bound for the clamp operation.
     * @param max   The maximum bound for the clamp operation.
     * @return The result of the clamp operation.
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) value = min;
        else if (value > max) value = max;
        return value;
    }

    /**
     * [LINEAR INTERPOLATION] Smoothly tends the start value towards the end value using the provided amount as the speed factor.
     *
     * @param start  The value which you are interpolating towards the end value.
     * @param end    The value at which to stop interpolation on.
     * @param amount The speed at which to interpolate start towards end.
     * @return Return the next iteration of the interpolation operation.
     */
    public static float lerp(float start, float end, float amount) {
        if (start == end)
            return start;
        if (start < end) {
            start = clamp(start + amount, start, end);
        } else {
            start = clamp(start - amount, end, start);
        }
        return start;
    }

    /**
     * Randomly generates a true or false boolean value.
     *
     * @return Returns true or false at random.
     */
    public static boolean randomBoolean() {
        return Math.random() > 0.5f;
    }

    /**
     * Randomly generates an integer value between the provided bounds.
     *
     * @param min The minimum boundary in which to generate the integer.
     * @param max The maxiumum boundary in which to generate the integer.
     * @return The generated integer.
     */
    public static int randomInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("Minimum bound cannot be larger than maximum bound!");
        if (min == max) return min;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    /**
     * Randomly generates a float value between the provided bounds.
     * The maximum bound is exclusive.
     *
     * @param min The minimum bound in which to generate the float. (Inclusive)
     * @param max The maximum bound in which to generate the float. (Exclusive)
     * @return The generated float.
     */
    public static float randomFloat(float min, float max) {
        if (min > max) throw new IllegalArgumentException("Minimum bound cannot be larger than maximum bound!");
        if (min == max) return min;
        return (float) Math.random() * (max - min) + min;
    }

    /**
     * Randomly generates a double value between the provided bounds.
     * The maximum bound is exclusive.
     *
     * @param min The minimum bound in which to generate the float. (Inclusive)
     * @param max The maximum bound in which to generate the float. (Exclusive)
     * @return The generated double.
     */
    public static double randomDouble(double min, double max) {
        if (min > max) throw new IllegalArgumentException("Minimum value cannot be larger than maximum!");
        if (min == max) return min;
        return Math.random() * (max - min) + min;
    }

    /**
     * Returns a random value true with probability given by chance
     *
     * @param chance The change the return value will be true, as a percentage (ex. 50)
     * @return The result of the probability calculation
     */
    public static boolean randomChance(float chance) {
        if (chance < 0) throw new IllegalArgumentException("Change cannot be smaller than 0%");
        return Math.random() < chance / 100;
    }

    public static boolean floatEquals(float a, float b) {
        return floatEquals(a, b, 0.00001f);
    }

    public static boolean floatEquals(float a, float b, float epsilon) {
        return Math.abs(a - b) < epsilon;
    }
}
