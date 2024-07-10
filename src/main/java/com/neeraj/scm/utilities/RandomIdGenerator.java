package com.neeraj.scm.utilities;

import java.util.Random;

public class RandomIdGenerator {
    public static String randomIdGenerator() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(new Random().nextInt(10));
        }
        return String.valueOf(builder);
    }
}
