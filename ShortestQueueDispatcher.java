/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: ShortestQueueDispatcher.java
 * This file extends job dispatcher and defines the shortest queue dispatcher.
 */

public class ShortestQueueDispatcher extends JobDispatcher {
    
    /**
     * Constructs a ShortestQueueDispatcher with a specified number of servers and a visualization option.
     * This constructor initializes the dispatcher with the superclass constructor, sets up the required
     * number of servers and determines whether or not to display the visualization.
     * 
     * @param k the number of servers in the dispatcher
     * @param showViz a boolean flag indicating whether or not to show the visualization
     */
    public ShortestQueueDispatcher(int k, boolean showViz) {
        super(k, showViz); 
    }

    /**
     * Overrides the abstract pickServer method from JobDispatcher and iterates through all servers managed by the dispatcher
     * and selects the one with the fewest jobs waiting in its queue.
     * 
     * @param j the job to be dispatched to a server
     * @return the server with the shortest queue at the time of job arrival
     */
    @Override
    public Server pickServer(Job j) {
        Server shortestQueueServer = servers.get(0); // Start with the first server as a baseline for comparison.
        for (Server server : servers) {
            // Compare the sizes of the current shortest queue server with each server in the list.
            if (server.size() < shortestQueueServer.size()) {
                shortestQueueServer = server; // Update the shortest queue server if a smaller queue is found.
            }
        }
        return shortestQueueServer; 
    }
}

