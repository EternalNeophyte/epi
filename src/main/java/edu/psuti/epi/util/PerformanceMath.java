package edu.psuti.epi.util;

import org.apache.commons.math3.stat.descriptive.AbstractUnivariateStatistic;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.util.Arrays;
import java.util.Random;

public final class PerformanceMath {

    private static final int BOUND = 0x100000;
    private static final double FACTOR = 10e-6;
    private static final Random RANDOM = new Random();
    private static final AbstractUnivariateStatistic
            //дисперсия
            VARIANCE = new Variance(),
            //мат ожидание
            MEAN = new Mean();

    public record Evaluation(long[] timings, double mean, double variance) { }

    private static void shuffle(int[] array) {
        int selected;
        for(int i = array.length - 1; i > 0; i--) {
            selected = RANDOM.nextInt(i + 1);
            if(selected != i) {
                array[selected] ^= array[i];
                array[i] ^= array[selected];
                array[selected] ^= array[i];
            }
        }
    }

    public static Evaluation evaluate(int size, int runs) {
        int[] numbers = RANDOM.ints(size, 0, BOUND).toArray();
        long[] timings = new long[runs];
        long sortStart;
        for(int i = 0; i < runs; i++) {
            shuffle(numbers);
            sortStart = System.nanoTime();
            Arrays.sort(numbers);
            timings[i] = System.nanoTime() - sortStart;
        }
        return new Evaluation(timings, reduceTo(MEAN, timings), reduceTo(VARIANCE, timings));
    }


    private static double reduceTo(AbstractUnivariateStatistic statistic, long[] timings) {
        double[] timingsInMs = Arrays.stream(timings)
                .mapToDouble(t -> t * FACTOR)
                .toArray();
        return statistic.evaluate(timingsInMs);
    }

}
