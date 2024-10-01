/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: Server.java
 * This file defines the Server class
 */

//importing all the required packages
import java.awt.Graphics; 
import java.awt.Color; 
import java.awt.Toolkit; 
import java.awt.Font;

/**
 * The Server class represents a server in the server farm that processes jobs.
 * It manages a queue of jobs, processing them in a first-in-first-out (FIFO) manner
 */
public class Server {

    protected double time; // The current system time for this server.
    protected double totalWaitingTime; // Total waiting time of all jobs processed.
    protected double remainingTime; // The total remaining processing time needed for all jobs in the queue.
    protected int numJobs; // The number of jobs processed by this server.

    LinkedList<Job> jobQueue = new LinkedList<>(); // Queue of jobs waiting to be processed by this server.

    /**
     * Constructs a new Server with no jobs and initializes other variables to 0.
     */
    public Server(){
        this.time = 0;
        this.numJobs = 0;
        this.totalWaitingTime = 0;
        this.remainingTime = 0;
        this.jobQueue = new LinkedList<>();
    }

    /**
     * Adds a job to the server's job queue and updates the remaining processing time accordingly.
     * 
     * @param job The job to add to the queue.
     */
    public void addJob(Job job){
        this.jobQueue.offer(job); // Add the job to the queue.
        remainingTime += job.getProcessingTimeRemaining(); // Update remaining processing time.
    }

    /**
     * Processes jobs in the queue up to a specified system time, following FIFO order.
     * 
     * @param time The time until which jobs should be processed.
     */
    public void processTo(double time) {
        double timeLeft = time - this.time; // Calculate available time to process jobs.
        while (timeLeft > 0 && !jobQueue.isEmpty()) {
            Job currentJob = jobQueue.peek(); // Get the first job in the queue without removing it.
            double timeToProcess = Math.min(currentJob.getProcessingTimeRemaining(), timeLeft);
            
            currentJob.process(timeToProcess, this.time + (timeLeft - timeToProcess)); // Process the job.
            
            if (currentJob.isFinished()) {
                jobQueue.poll(); // Remove finished job from the queue.
                totalWaitingTime += currentJob.timeInQueue(); // Update total waiting time.
                numJobs++; // Increment processed job count.
            }
            
            remainingTime -= timeToProcess; // Update remaining time for jobs in the queue.
            timeLeft -= timeToProcess; // Reduce available time by the time spent on this job.
        }
        this.time = time; // Update server's system time.
    }    

    /**
     * Calculates the total remaining processing time for all jobs in the server's queue.
     * 
     * @return The total remaining processing time.
     */
    public double remainingWorkInQueue(){
        return remainingTime; // Return the pre-calculated remaining time for efficiency.
    }
    
    /**
     * Returns the number of jobs currently in the server's queue.
     * 
     * @return The size of the job queue.
     */
    public int size(){
        return this.jobQueue.size();
    }

    public double getTotalWaitingTime(){
        return totalWaitingTime;
    }

    public double getNumJobsProcessed(){
        return numJobs;
    }
    /*
     * draws the simulation
     */
    public void draw(Graphics g, Color c, double loc, int numberOfServers){
        double sep = (ServerFarmViz.HEIGHT - 20) / (numberOfServers + 2.0);
        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), (int) (72.0 * (sep * .5) / Toolkit.getDefaultToolkit().getScreenResolution())));
        g.drawString("Work: " + (remainingWorkInQueue() < 1000 ? remainingWorkInQueue() : ">1000"), 2, (int) (loc + .2 * sep));
        g.drawString("Jobs: " + (size() < 1000 ? size() : ">1000"), 5 , (int) (loc + .55 * sep));
        g.setColor(c); 
        g.fillRect((int) (3 * sep), (int) loc, (int) (.8 * remainingWorkInQueue()), (int) sep);
        g.drawOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
        if (remainingWorkInQueue() == 0) g.setColor(Color.GREEN.darker());
        else g.setColor(Color.RED.darker());
        g.fillOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
    
    }
}
