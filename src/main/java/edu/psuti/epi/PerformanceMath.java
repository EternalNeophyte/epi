package edu.psuti.epi;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import static java.lang.System.nanoTime;
import static java.util.Arrays.sort;
import static java.util.Arrays.stream;

public final class PerformanceMath {

    private static final int BOUND = 0x100000;
    public static final int DEFAULT_SCALE = 6;
    private static final double NANO_MULTIPLIER = 10e-6;

    private static final Random RANDOM = new Random();
    private static final Variance VARIANCE = new Variance();    //дисперсия
    private static final Mean MEAN = new Mean();                //мат. ожидание

    public static Evaluation evaluate(int size, int runs, int scale) {
        int[] numbers = RANDOM.ints(size, 0, BOUND).toArray();
        double[] timings = new double[runs];
        long sortStart;
        for(int i = 0; i < runs; i++) {
            shuffle(numbers);
            sortStart = nanoTime();
            sort(numbers);
            timings[i] = (nanoTime() - sortStart) * NANO_MULTIPLIER;
        }

        DecimalFormat df = new DecimalFormat("#." + "#".repeat(scale));
        df.setRoundingMode(RoundingMode.HALF_UP);
        String[] formattedTimings = stream(timings)
                .mapToObj(df::format)
                .toArray(String[]::new);
        String formattedMean = df.format(MEAN.evaluate(timings));
        String formattedVariance = df.format(VARIANCE.evaluate(timings));

        return new Evaluation(formattedTimings, formattedMean, formattedVariance);
    }

    private static void shuffle(int[] array) {
        int selected;
        for(int i = 0; i < array.length; i++) {
            selected = RANDOM.nextInt(i + 1);
            if(selected != i) {
                array[selected] ^= array[i];
                array[i] ^= array[selected];
                array[selected] ^= array[i];
            }
        }
    }
}
