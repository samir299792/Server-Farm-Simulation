/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: JobDispatcher.java
 */

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics; 
import java.awt.Color;

public abstract class JobDispatcher{
    protected ArrayList<Server> servers = new ArrayList<Server>(); //Arraylist of server objects to represent the servers
    private double time; //systemtime
    private boolean showViz; //visualization object
    private int numJobsHandled; //number of jobs handled
    private ServerFarmViz serverFarmViz; //simulation object

    /**
     * Constructs a new JobDispatcher object with the specified number of servers.
     *
     * @param k the number of servers in the system
     * @param showViz boolean value to represent the choice to visualise or not.
     */
    public JobDispatcher(int k, boolean showViz) {
        this.servers = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            this.servers.add(new Server());  
            //this.servers.add(new PreemptiveServer());
        }
        this.time = 0;
        this.numJobsHandled = 0;
        this.showViz = showViz;
        if (showViz) {
            // Initialize serverFarmViz here if showViz is true.
            this.serverFarmViz = new ServerFarmViz(this, showViz);
        }
    }

    /**
     * method to return the system time
     * @return doubel type time
     */
    public double getTime() {
        return time;
    }

    public List<Server> getServerList() {
        return servers;
    }
    public void advanceTimeTo(double time){
        if (time < this.time) return;
        for (Server server : servers) {
            server.processTo(time);
        }
        this.time = time;
    
    }

    /**
     * Processes all remaining jobs on all servers and advances the current time of
     * the system to the time at which the last job is finished.
     */
    public void finishUp() {
        // create arraylist to store the work time of each server
        ArrayList<Double> tempTime = new ArrayList<>();
        double max = -1;
        for (Server server : servers) {
            tempTime.add(server.remainingWorkInQueue()); // add work time of each server to list
        }
        for (int i = 0; i < tempTime.size(); i++) { // loop through list
            if (tempTime.get(i) > max) { // if the time is ever larger than the previous max value
                max = tempTime.get(i); // set max value as new time
            }
        }
        advanceTimeTo(this.time + max); // advance time to that and finish up
    }

    /**
     * Handles a single job by advancing the current time of the system to the
     * arrival time of the job,
     * picking a server to assign the job to using the pickServer method,
     * and adding the job to the server's queue.
     *
     * @param job the job to handle
     */
    public void handleJob(Job job) {
        advanceTimeTo(job.getArrivalTime());
    
        if (showViz) {
            serverFarmViz.repaint();
        }
    
        Server chosenServer = pickServer(job);
        chosenServer.addJob(job);
        numJobsHandled++; // Increment the number of jobs handled
    
        if (showViz) {
            serverFarmViz.repaint();
        }
    }

    /**
     * method to get the number of jobs that are handled.
     * @return the number of handled jobs
     */
    public int getNumJobsHandled() {
        return numJobsHandled;
    }

    public double getAverageWaitingTime() {
        if (numJobsHandled == 0) {
            // If no jobs have been handled, the average waiting time is undefined (or zero).
            return 0;
        }
        
        double totalWaitingTime = 0;
        
        // Sum the total waiting time of all servers.
        for (Server server : servers) {
            totalWaitingTime += server.totalWaitingTime; // Directly access the totalWaitingTime field of each server.
        }
    
        // Calculate the average waiting time.
        return totalWaitingTime / numJobsHandled;
    }
    /**
     * An abstract method that must be implemented by a subclass to pick a server to
     * assign a job to.
     *
     * @param j the job to assign to a server
     * @return the server to assign the job to
     */
    public abstract Server pickServer(Job j);

    public void draw(Graphics g){
        double sep = (ServerFarmViz.HEIGHT - 20) / (getServerList().size() + 2.0);
        g.drawString("Time: " + getTime(), (int) sep, ServerFarmViz.HEIGHT - 20);
        g.drawString("Jobs handled: " + getNumJobsHandled(), (int) sep, ServerFarmViz.HEIGHT - 10);
        for(int i = 0; i < getServerList().size(); i++){
            getServerList().get( i ).draw(g, (i % 2 == 0) ? Color.GRAY : Color.DARK_GRAY, (i + 1) * sep, getServerList().size());
        }
    }
}