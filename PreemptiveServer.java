/*
 * Samir Bikram Dhami
 * Project 4: Simulating Server Farms
 * CS231 A
 * Date: 25th March, 2024
 * File name: PreemptiveServer.java
 * This file extends the server and updates its process to
 * fetch any jobs from the queue wanted by the server
 */

import java.util.Comparator;

 public class PreemptiveServer extends Server {
 
     public PreemptiveServer(){
         super(); //inheriting all the properties of the parent class
     }

    /**
     * Processes jobs in the queue up to a specified system time, following FIFO order.
     * Updated so that the server can fetch any job it wants
     * 
     * @param time The time until which jobs should be processed.
     */
     public void processTo(double time) {
         double timeLeft = Math.abs(time - this.time); //calculating the time  left
         while (timeLeft > 0 && this.jobQueue.size() > 0) { //if we still have time and jobs remain.
             Job currentJob = jobQueue.findMin(new Comparator<Job>() { //finding the job with minimu processing time
                /*
                 * returns 0 if the jobs have the same remaining processing time, returns -1 if o1 has
                 * a longer processing time than o2, and 1 otherwise.
                 */
                 @Override
                 public int compare(Job o1, Job o2) {
                 if (o1.getProcessingTimeRemaining() == o2.getProcessingTimeRemaining()) {
                     return 0;
                 }
                 else if (o1.getProcessingTimeRemaining() > o2.getProcessingTimeRemaining()) {
                     return -1;
                 } else {
                     return 1;
                 }
                 }
             });
             double timeNeeded = Math.min(currentJob.getProcessingTimeRemaining(), timeLeft);
             currentJob.process(timeNeeded, this.time);
             remainingTime = remainingTime - timeNeeded;
             if (currentJob.isFinished()) {
                 jobQueue.removeMin(new Comparator<Job>() { //remove the job with minimun processing time if current job is finished.
                    /*
                    * returns 0 if the jobs have the same remaining processing time, returns -1 if o1 has
                    * a longer processing time than o2, and 1 otherwise.
                    */
                     @Override
                     public int compare(Job o1, Job o2) {
                     if (o1.getProcessingTimeRemaining() == o2.getProcessingTimeRemaining()) {
                         return 0;
                     }
                     else if (o1.getProcessingTimeRemaining() > o2.getProcessingTimeRemaining()) {
                         return -1;
                     } else {
                         return 1;
                     }
                     }
                 });
                 numJobs++;
                 totalWaitingTime = totalWaitingTime + currentJob.timeInQueue();
             }
             timeLeft = timeLeft - timeNeeded;
             }
         this.time = time;
     }
 
 }