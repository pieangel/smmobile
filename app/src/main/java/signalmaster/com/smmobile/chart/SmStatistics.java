package signalmaster.com.smmobile.chart;

import java.util.LinkedList;

public class SmStatistics {
    public SmStatistics() {};
    /**
     * Returns the maximum value in the specified array.
     *
     * @param  a the array
     * @return the maximum value in the array {@code a[]};
     *         {@code Double.NEGATIVE_INFINITY} if no such value
     */
    public static double max(double[] a) {
        validateNotNull(a);

        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < a.length; i++) {
            if (Double.isNaN(a[i])) return Double.NaN;
            if (a[i] > max) max = a[i];
        }
        return max;
    }

    /**
     * Returns the maximum value in the specified subarray.
     *
     * @param  a the array
     * @param  lo the left endpoint of the subarray (inclusive)
     * @param  hi the right endpoint of the subarray (exclusive)
     * @return the maximum value in the subarray {@code a[lo..hi)};
     *         {@code Double.NEGATIVE_INFINITY} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double max(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        double max = Double.NEGATIVE_INFINITY;
        for (int i = lo; i < hi; i++) {
            if (Double.isNaN(a[i])) return Double.NaN;
            if (a[i] > max) max = a[i];
        }
        return max;
    }

    /**
     * Returns the maximum value in the specified array.
     *
     * @param  a the array
     * @return the maximum value in the array {@code a[]};
     *         {@code Integer.MIN_VALUE} if no such value
     */
    public static int max(int[] a) {
        validateNotNull(a);

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) max = a[i];
        }
        return max;
    }

    /**
     * Returns the minimum value in the specified array.
     *
     * @param  a the array
     * @return the minimum value in the array {@code a[]};
     *         {@code Double.POSITIVE_INFINITY} if no such value
     */
    public static double min(double[] a) {
        validateNotNull(a);

        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < a.length; i++) {
            if (Double.isNaN(a[i])) return Double.NaN;
            if (a[i] < min) min = a[i];
        }
        return min;
    }

    /**
     * Returns the minimum value in the specified subarray.
     *
     * @param  a the array
     * @param  lo the left endpoint of the subarray (inclusive)
     * @param  hi the right endpoint of the subarray (exclusive)
     * @return the maximum value in the subarray {@code a[lo..hi)};
     *         {@code Double.POSITIVE_INFINITY} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double min(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        double min = Double.POSITIVE_INFINITY;
        for (int i = lo; i < hi; i++) {
            if (Double.isNaN(a[i])) return Double.NaN;
            if (a[i] < min) min = a[i];
        }
        return min;
    }

    /**
     * Returns the minimum value in the specified array.
     *
     * @param  a the array
     * @return the minimum value in the array {@code a[]};
     *         {@code Integer.MAX_VALUE} if no such value
     */
    public static int min(int[] a) {
        validateNotNull(a);

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) min = a[i];
        }
        return min;
    }

    /**
     * Returns the average value in the specified array.
     *
     * @param  a the array
     * @return the average value in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double mean(double[] a) {
        validateNotNull(a);

        if (a.length == 0) return Double.NaN;
        double sum = sum(a);
        return sum / a.length;
    }

    /**
     * Returns the average value in the specified subarray.
     *
     * @param a the array
     * @param lo the left endpoint of the subarray (inclusive)
     * @param hi the right endpoint of the subarray (exclusive)
     * @return the average value in the subarray {@code a[lo..hi)};
     *         {@code Double.NaN} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double mean(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        int length = hi - lo;
        if (length == 0) return Double.NaN;

        double sum = sum(a, lo, hi);
        return sum / length;
    }

    /**
     * Returns the average value in the specified array.
     *
     * @param  a the array
     * @return the average value in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double mean(int[] a) {
        validateNotNull(a);

        if (a.length == 0) return Double.NaN;
        int sum = sum(a);
        return 1.0 * sum / a.length;
    }

    /**
     * Returns the sample variance in the specified array.
     *
     * @param  a the array
     * @return the sample variance in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double var(double[] a) {
        validateNotNull(a);

        if (a.length == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }

    /**
     * Returns the sample variance in the specified subarray.
     *
     * @param  a the array
     * @param lo the left endpoint of the subarray (inclusive)
     * @param hi the right endpoint of the subarray (exclusive)
     * @return the sample variance in the subarray {@code a[lo..hi)};
     *         {@code Double.NaN} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double var(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        int length = hi - lo;
        if (length == 0) return Double.NaN;

        double avg = mean(a, lo, hi);
        double sum = 0.0;
        for (int i = lo; i < hi; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (length - 1);
    }

    /**
     * Returns the sample variance in the specified array.
     *
     * @param  a the array
     * @return the sample variance in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double var(int[] a) {
        validateNotNull(a);
        if (a.length == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }

    /**
     * Returns the population variance in the specified array.
     *
     * @param  a the array
     * @return the population variance in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double varp(double[] a) {
        validateNotNull(a);
        if (a.length == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / a.length;
    }

    /**
     * Returns the population variance in the specified subarray.
     *
     * @param  a the array
     * @param lo the left endpoint of the subarray (inclusive)
     * @param hi the right endpoint of the subarray (exclusive)
     * @return the population variance in the subarray {@code a[lo..hi)};
     *         {@code Double.NaN} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double varp(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        int length = hi - lo;
        if (length == 0) return Double.NaN;

        double avg = mean(a, lo, hi);
        double sum = 0.0;
        for (int i = lo; i < hi; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / length;
    }

    /**
     * Returns the sample standard deviation in the specified array.
     *
     * @param  a the array
     * @return the sample standard deviation in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double stddev(double[] a) {
        validateNotNull(a);
        return Math.sqrt(var(a));
    }

    /**
     * Returns the sample standard deviation in the specified array.
     *
     * @param  a the array
     * @return the sample standard deviation in the array {@code a[]};
     *         {@code Double.NaN} if no such value
     */
    public static double stddev(int[] a) {
        validateNotNull(a);
        return Math.sqrt(var(a));
    }

    /**
     * Returns the sample standard deviation in the specified subarray.
     *
     * @param  a the array
     * @param lo the left endpoint of the subarray (inclusive)
     * @param hi the right endpoint of the subarray (exclusive)
     * @return the sample standard deviation in the subarray {@code a[lo..hi)};
     *         {@code Double.NaN} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double stddev(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        return Math.sqrt(var(a, lo, hi));
    }


    /**
     * Returns the population standard deviation in the specified array.
     *
     * @param  a the array
     * @return the population standard deviation in the array;
     *         {@code Double.NaN} if no such value
     */
    public static double stddevp(double[] a) {
        validateNotNull(a);
        return Math.sqrt(varp(a));
    }

    /**
     * Returns the population standard deviation in the specified subarray.
     *
     * @param  a the array
     * @param lo the left endpoint of the subarray (inclusive)
     * @param hi the right endpoint of the subarray (exclusive)
     * @return the population standard deviation in the subarray {@code a[lo..hi)};
     *         {@code Double.NaN} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static double stddevp(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        return Math.sqrt(varp(a, lo, hi));
    }

    /**
     * Returns the sum of all values in the specified array.
     *
     * @param  a the array
     * @return the sum of all values in the array {@code a[]};
     *         {@code 0.0} if no such value
     */
    private static double sum(double[] a) {
        validateNotNull(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }

    /**
     * Returns the sum of all values in the specified subarray.
     *
     * @param  a the array
     * @param lo the left endpoint of the subarray (inclusive)
     * @param hi the right endpoint of the subarray (exclusive)
     * @return the sum of all values in the subarray {@code a[lo..hi)};
     *         {@code 0.0} if no such value
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    private static double sum(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        double sum = 0.0;
        for (int i = lo; i < hi; i++) {
            sum += a[i];
        }
        return sum;
    }

    /**
     * Returns the sum of all values in the specified array.
     *
     * @param  a the array
     * @return the sum of all values in the array {@code a[]};
     *         {@code 0.0} if no such value
     */
    private static int sum(int[] a) {
        validateNotNull(a);
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }


    // function that returns correlation coefficient.
    static float correlationCoefficient(double X[],
                                        double Y[], int n)
    {

        double sum_X = 0, sum_Y = 0, sum_XY = 0;
        double squareSum_X = 0, squareSum_Y = 0;

        for (int i = 0; i < n; i++)
        {
            // sum of elements of array X.
            sum_X = sum_X + X[i];

            // sum of elements of array Y.
            sum_Y = sum_Y + Y[i];

            // sum of X[i] * Y[i].
            sum_XY = sum_XY + X[i] * Y[i];

            // sum of square of array elements.
            squareSum_X = squareSum_X + X[i] * X[i];
            squareSum_Y = squareSum_Y + Y[i] * Y[i];
        }

        // use formula for calculating correlation
        // coefficient.
        float corr = (float)(n * sum_XY - sum_X * sum_Y)/
                (float)(Math.sqrt((n * squareSum_X -
                        sum_X * sum_X) * (n * squareSum_Y -
                        sum_Y * sum_Y)));

        return corr;
    }

    // function that returns correlation coefficient.
    public static float correlationCoefficient(LinkedList<Double> X,
                                        LinkedList<Double> Y, int n)
    {

        double sum_X = 0, sum_Y = 0, sum_XY = 0;
        double squareSum_X = 0, squareSum_Y = 0;

        for (int i = 0; i < n; i++)
        {
            // sum of elements of array X.
            sum_X = sum_X + X.get(i);

            // sum of elements of array Y.
            sum_Y = sum_Y + Y.get(i);

            // sum of X[i] * Y[i].
            sum_XY = sum_XY + X.get(i) * Y.get(i);

            // sum of square of array elements.
            squareSum_X = squareSum_X + X.get(i) * X.get(i);
            squareSum_Y = squareSum_Y + Y.get(i) * Y.get(i);
        }

        // use formula for calculating correlation
        // coefficient.
        float corr = (float)(n * sum_XY - sum_X * sum_Y)/
                (float)(Math.sqrt((n * squareSum_X -
                        sum_X * sum_X) * (n * squareSum_Y -
                        sum_Y * sum_Y)));

        return corr;
    }


    // throw an IllegalArgumentException if x is null
    // (x is either of type double[] or int[])
    private static void validateNotNull(Object x) {
        if (x == null)
            throw new IllegalArgumentException("argument is null");
    }

    // throw an exception unless 0 <= lo <= hi <= length
    private static void validateSubarrayIndices(int lo, int hi, int length) {
        if (lo < 0 || hi > length || lo > hi)
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", " + hi + ")");
    }


}
