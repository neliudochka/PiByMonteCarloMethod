import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.Timer;

public class ParallelMonteCarloPi {
    private static class PiRunnable implements Runnable {
        @Getter
        private double pi;
        @Setter @Getter
        private int iterations = 1_000_0000;

        private double calculateByMonteCarlo(int n) {
            double s1 = 0;
            double s2 = 0;
            for (int i=0; i<n; i++) {
                if(new Point().distance() <= 1)
                    s1++;
                else
                    s2++;
            }
            return 4 * s1/(s1 + s2);
        }

        @Override
        public void run() {
            pi = calculateByMonteCarlo(iterations);
        }
    }

    public void findPi(int n) {
        Long timeStart = new Date().getTime();
        PiRunnable[] runnables = new PiRunnable[n];
        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            PiRunnable myRunnable = new PiRunnable();
            runnables[i] = myRunnable;
            Thread thread = new Thread(myRunnable);
            threads[i] = thread;

            thread.start();
        }

        Arrays.stream(threads).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Long timeEnd = new Date().getTime();

        double pi = Arrays.stream(runnables)
                .mapToDouble(PiRunnable::getPi)
                .average().orElse(-1);

        System.out.println("PI: " + pi);
        System.out.println("TREADS: " + n);
        System.out.println("ITERATIONS: " + runnables[0].getIterations());
        System.out.println("TIME: " + (timeEnd-timeStart) +"ms");
    };

}
