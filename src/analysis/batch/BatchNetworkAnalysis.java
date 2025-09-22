package analysis.batch;

import java.util.List;
import java.util.concurrent.*;
import analysis.abs.*;
import analysis.network.file.*;

/**
 * Runs a list of Network instances in parallel, created for analysis of large networks..
 * Each Network.runCentralityAnalysisBatchMode() is executed in its own task.
 */
public class BatchNetworkAnalysis {
    public static void runBatch(List<Network> networks, int threadPoolSize, long awaitSeconds) throws InterruptedException {
        if (networks == null || networks.isEmpty()) return;

        ExecutorService executor = Executors.newFixedThreadPool(Math.max(1, threadPoolSize));

        for (Network net : networks) {
            executor.submit(() -> {
                try {
                    System.out.println("[BATCH] Starting network: " + net.getName());
                    net.runCentralityAnalysisBatchMode();
                    System.out.println("[BATCH] Finished network: " + net.getName());
                } catch (Throwable t) {
                    System.err.println("[BATCH] Error processing " + net.getName() + ": " + t.getMessage());
                    t.printStackTrace();
                }
            });
        }

        executor.shutdown(); // no new tasks accepted

        boolean finished;
        if (awaitSeconds <= 0) {
            finished = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } else {
            finished = executor.awaitTermination(awaitSeconds, TimeUnit.SECONDS);
        }

        if (!finished) {
            System.out.println("[BATCH] Some tasks did not finish within the timeout.");
        } else {
            System.out.println("[BATCH] All tasks completed.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Example usage: small dummy networks
        List<Network> nets = List.of(
                new CaliforniaRoads()
        );

        runBatch(nets, Runtime.getRuntime().availableProcessors(), 0);
    }
}
