package Jobsystem;

import javax.swing.*;

/**
 *
 */
public class Main {


    private static Management management = new Management();
    private static Type type;
    private static Job job;

    public static void main(String[] args){

        int jobstoBeRun = getJobsNumber();

        // for loop create jobs and collect jobInfo from user-input
        for (int i = 1; i <= jobstoBeRun; i++) {

			type = new Type((getTypeName(i)));
            int period = getPeriod(i);
            job = new Job(i, type, period);

            Thread thread = new Thread(job);
            	// if block to prevent redundant runs of jobs 
            if (!management.getTaskList().contains(job)) {
                runThread(thread);
            }
        }

        management.getAutoex().shutdown();
        management.getTaskList().get(0).stop();

        // make sure all jobs are finished and notify the user
        while (!management.getAutoex().isTerminated()) {
            management.fetchSystemStats();
        }
        printFinishMessage();
        management.notifyCleanUp();

    }

    /** run thread in the pool and add it to the taskList
     *
     */
    private static void runThread(Thread thread){
        management.getAutoex().submit(thread);
        management.getTaskList().add(job); // threads are stored here
        System.out.println("State before Running: " + job.getState());

    }

    /**
     *
     * @return the number of the created Jobs
     */
    private static int getJobsNumber(){
         return Integer.parseInt(JOptionPane.showInputDialog("How many jobs do you want to create?"));
    }

    /**
     *
     * @param i ist the index of the job at TaskList
     * @return the the entered typename by user
     */
    private static String getTypeName(int i){

        return JOptionPane.showInputDialog("please define the type of the " + i + ".job");
    }

    /**
     *
     * @param i ist the index of the job at TaskList
     * @return  the entered period by user
     */
    private static int getPeriod(int i){
        return Integer.parseInt(JOptionPane.showInputDialog("How often should the " + i + ".job run in minutes?")) * 60000;
    }

    /**
     *  show the message after finishing the Pool-Job
     */
    private static void printFinishMessage(){
        System.out.println("All requests completed successfully");
    }
}
