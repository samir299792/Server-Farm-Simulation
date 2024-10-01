/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: RandomDispatcher.java
 */

 import java.util.Random;

 /**
  * RandomDispatcher selects a random server for dispatching jobs.
  * This class extends the JobDispatcher class and overrides the pickServer method
  * to implement random selection of servers.
  */
 public class RandomDispatcher extends JobDispatcher {
     private final Random random; // A Random object for generating random server indices.
 
     /**
      * Constructs a RandomDispatcher with a specified number of servers and visualization option.
      * Initializes a Random object for server selection.
      * 
      * @param k the number of servers in the dispatcher
      * @param showViz a boolean flag indicating whether or not to show the visualization
      */
     public RandomDispatcher(int k, boolean showViz) {
         super(k, showViz); // Call the superclass constructor to initialize the servers and other settings.
         this.random = new Random(); // Initialize a Random object for selecting servers.
     }
 
     /**
      * Picks a random server from the available servers to assign a new job.
      * This method overrides the abstract method in the parent class.
      * 
      * @param job the job to be dispatched to a server
      * @return the randomly selected server
      */
     @Override
     public Server pickServer(Job job) {
         // Select a random server index between 0 (inclusive) and the number of servers (exclusive).
         int serverIndex = random.nextInt(servers.size());
         return servers.get(serverIndex); // Return the randomly selected server.
     }
 }
 