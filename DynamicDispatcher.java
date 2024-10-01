/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: JobDispatcher.java
 * This file extends job dispatcher and defines the dynamic dispatcher.
 */
public class DynamicDispatcher extends JobDispatcher {
    private JobDispatcher currentDispatcher;
    private final double thresholdWaitingTime; // Threshold to switch strategies
    private boolean showViz;

    public DynamicDispatcher(int k, boolean showViz) {
        super(k, showViz);
        this.showViz = showViz;
        // Initialize servers for use in dynamic dispatching.
        for (int i = 0; i < k; i++) {
            this.servers.add(new Server());  
        }
        this.thresholdWaitingTime = 10.0; // Define a suitable threshold.
        // Start with RoundRobin as the default strategy.
        this.currentDispatcher = new RoundRobinDispatcher(k, showViz);
        // Ensure RoundRobinDispatcher uses the same server instances.
        this.currentDispatcher.servers = this.servers;
    }

    @Override
    public Server pickServer(Job j) {
        // Calculate the average waiting time across all servers.
        double averageWaitingTime = calculateAverageWaitingTime();
        
        if (averageWaitingTime > thresholdWaitingTime && !(currentDispatcher instanceof LeastWorkDispatcher)) {
            // System is under heavy load, switch to LeastWorkDispatcher.
            currentDispatcher = new LeastWorkDispatcher(servers.size(), showViz);
            currentDispatcher.servers = this.servers; // Use the same servers.
        } else if (averageWaitingTime <= thresholdWaitingTime && !(currentDispatcher instanceof RoundRobinDispatcher)) {
            // Load is manageable, switch back to RoundRobinDispatcher.
            currentDispatcher = new RoundRobinDispatcher(servers.size(), showViz);
            currentDispatcher.servers = this.servers; // Use the same servers.
        }
        
        // Delegate job dispatching to the current strategy.
        return currentDispatcher.pickServer(j);
    }

    private double calculateAverageWaitingTime() {
        double totalWaitingTime = 0;
        int totalProcessedJobs = 0;
        for (Server server : servers) {
            totalWaitingTime += server.getTotalWaitingTime();
            totalProcessedJobs += server.getNumJobsProcessed();
        }
        return totalProcessedJobs == 0 ? 0 : totalWaitingTime / totalProcessedJobs;
    }

    // Override other methods as needed, ensuring they delegate to the currentDispatcher or directly manipulate shared server instances.
}
