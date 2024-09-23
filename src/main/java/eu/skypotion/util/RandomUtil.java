package eu.skypotion.util;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class RandomUtil {

    private static final Random RANDOM = ThreadLocalRandom.current();

    public int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public int nextInt(int origin, int bound) {
        return RANDOM.nextInt(origin, bound);
    }

    public double nextDouble() {
        return RANDOM.nextDouble();
    }

    public double nextDouble(double origin, double bound) {
        return RANDOM.nextDouble(origin, bound);
    }

    public boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public long nextLong() {
        return RANDOM.nextLong();
    }

    public long nextLong(long origin, long bound) {
        return RANDOM.nextLong(origin, bound);
    }

    public float nextFloat() {
        return RANDOM.nextFloat();
    }

    public float nextFloat(float origin, float bound) {
        return RANDOM.nextFloat(origin, bound);
    }

    public double nextGaussian() {
        return RANDOM.nextGaussian();
    }

    public void nextBytes(byte[] bytes) {
        RANDOM.nextBytes(bytes);
    }

    public static boolean chance(double chance) {
        double min = 0;
        double max = 100;

        double random = nextDouble(min, max);
        return random <= chance;
    }
}
