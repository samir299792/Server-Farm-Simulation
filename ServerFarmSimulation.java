/*
file name:      ServerFarmSimulation.java
Authors:        Ike Lage
last modified:  03/07/2024
Edited by Samir Bikram Dhami on 25th March, 2024.
This file defines a class to simualate the server farm
*/

public class ServerFarmSimulation {
    public static void main(String[] args) {

        //You can explore how these change your results if you want!
        //How often a new job arrives at the server farm, on average
        int meanArrivalTime = 3 ; 
        //How long a job takes to process, on average
        int meanProcessingTime = 100 ; 

        //Debugging settings
        // int numServers = 4 ; //Numbers of servers in the farm
        // int numJobs = 10 ; //Number of jobs to process
        // boolean showViz = true ; //Set to true to see the visualization, and false to run your experiments

        //Main experiment settings
        
        int numServers = 34 ; //Numbers of servers in the farm
        int numJobs = 10000000 ; //Number of jobs to process
        boolean showViz = false ; //Set to true to see the visualization, and false to run your experiments
        String[] dispatcherTypes = {"random", "round", "shortest", "least", "dynamic"};
    
        //Formatting the table to display the results for experiment 1.
        System.out.println("Experiment 1: Average Waiting Time for 34 Servers and 10,000,000 Jobs");
        System.out.println("------------------------------------------------------------------");
        System.out.println("| Dispatcher Type       | Average Waiting Time (seconds) |");
        System.out.println("------------------------------------------------------------------");
        
        for (String dispatcherType : dispatcherTypes) {
            JobDispatcher dispatcher = createDispatcher(dispatcherType, numServers, showViz);
            // Pass meanArrivalTime and meanProcessingTime as additional arguments to runSimulation.
            runSimulation(dispatcher, numJobs, meanArrivalTime, meanProcessingTime);
            System.out.format("| %-21s | %-30.3f |\n", dispatcherType, dispatcher.getAverageWaitingTime());
        }
        
        System.out.println("------------------------------------------------------------------");

        //Formatting the table to display the results for experiment 2.
        System.out.println(
        "Experiment 2: Average Waiting Time for ShortestQueue Dispatcher with Varying Number of Servers");
        System.out.println(
                "------------------------------------------------------------------------------------------------------");
        System.out.println("| Number of Servers | Average Waiting Time (seconds) |");
        System.out.println(
                "------------------------------------------------------------------------------------------------------");

        for (int servers = 30; servers <= 40; servers++) {
            JobDispatcher dispatcher = new ShortestQueueDispatcher(servers, false);
            // Again, pass meanArrivalTime and meanProcessingTime as arguments.
            runSimulation(dispatcher, numJobs, meanArrivalTime, meanProcessingTime);
            System.out.format("| %-18s | %-10.3f |\n", servers, dispatcher.getAverageWaitingTime());
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------------");
    }
    
/**
 * static method to create the chosen dispatcher
 * @param dispatcherType of type string, representing the type of dispatcher
 * @param numServers of int time, representing the number of servers
 * @param showViz of boolean type, a flag to represent to whether show the visual or not.
 * @return
 */
private static JobDispatcher createDispatcher(String dispatcherType, int numServers, boolean showViz) {
        JobDispatcher dispatcher = switch (dispatcherType) {
            case "random" -> new RandomDispatcher(numServers, showViz);
            case "round" -> new RoundRobinDispatcher(numServers, showViz);
            case "shortest" -> new ShortestQueueDispatcher(numServers, showViz);
            case "least" -> new LeastWorkDispatcher(numServers, showViz);
            case "dynamic" -> new DynamicDispatcher(numServers, showViz); 
            default -> throw new IllegalArgumentException("Invalid dispatcher type: " + dispatcherType);
        };
        return dispatcher;
    }

/**
 * static method to run a simulation 
 * @param dispatcher of type jobdispatcher
 * @param numJobs of type int, representing the number of jobs
 * @param meanArrivalTime of type int, representing the mean arrival time
 * @param meanProcessingTime of type int, representing the mean processing time
 */
private static void runSimulation(JobDispatcher dispatcher, int numJobs, int meanArrivalTime, int meanProcessingTime) {
        JobMaker jobMaker = new JobMaker(meanArrivalTime, meanProcessingTime);
        for (int i = 0; i < numJobs; i++) {
            dispatcher.handleJob(jobMaker.getNextJob());
        }
        dispatcher.finishUp();
    }
}