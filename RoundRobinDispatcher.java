/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: RoundRobinDispatcher.java
 * This file extends job dispatcher and defines the round robin dispatcher.
 */

/**
 * The RoundRobinDispatcher class distributes jobs to servers in a round-robin fashion.
 * This class extends JobDispatcher, overriding the pickServer method to cycle through
 * the servers one by one for each job assignment.
 */
public class RoundRobinDispatcher extends JobDispatcher {
    private int currentIndex = 0; // Tracks the current index of the server to which the next job will be assigned.

    /**
     * Constructs a RoundRobinDispatcher with a specified number of servers and visualization option.
     * Initializes currentIndex to 0, starting the round-robin cycle with the first server.
     * 
     * @param k the number of servers in the dispatcher
     * @param showViz a boolean flag indicating whether or not to show the visualization
     */
    public RoundRobinDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    /**
     * Picks the next server in the round-robin cycle to assign a new job.
     * This method overrides the abstract method in the parent class.
     * After selecting a server, it updates currentIndex to point to the next server in the cycle,
     * wrapping around to the first server if the end of the list is reached.
     * 
     * @param j the job to be dispatched to a server
     * @return the server selected by the round-robin algorithm
     */
    @Override
    public Server pickServer(Job j) {
        Server server = servers.get(currentIndex); // Select the current server in the cycle.
        currentIndex = (currentIndex + 1) % servers.size(); // Update currentIndex, cycling back to 0 if at the end.
        return server;
    }
}
