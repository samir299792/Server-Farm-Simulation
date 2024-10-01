/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: JobDispatcher.java
 * This file extends job dispatcher and defines the least work dispatcher.
 */

public class LeastWorkDispatcher extends JobDispatcher {

    /**
     * Constructs a LeastWorkDispatcher with a specified number of servers and visualization option.
     * 
     * @param k the number of servers in the dispatcher
     * @param showViz a boolean flag indicating whether or not to show the visualization
     */
    public LeastWorkDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    /**
     * Picks the server with the least remaining work in its queue to assign a new job.
     * This method overrides the abstract method in the parent class.
     * 
     * @param j the job to be dispatched to a server
     * @return the server with the least amount of remaining work
     */
    @Override
    public Server pickServer(Job j) {
        // Start with the first server for least work.
        Server leastWorkServer = servers.get(0);
        for (Server server : servers) {
            if (server.remainingWorkInQueue() < leastWorkServer.remainingWorkInQueue()) {
                leastWorkServer = server;
            }
        }
    
        return leastWorkServer;
    }
}
