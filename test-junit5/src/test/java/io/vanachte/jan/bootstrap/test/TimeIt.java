package io.vanachte.jan.bootstrap.test;

public class TimeIt {

    public static void code(Runnable runnable) {

        long start = System.nanoTime();

        try {
            runnable.run();
        } finally {
            long end = System.nanoTime();
            System.out.println("Time taken: " +  (end - start ) /1.0e9);
        }
    }
}
