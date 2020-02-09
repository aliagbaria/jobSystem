package Jobsystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The execution of the jobs is managed here
 */
public class Management {

	private ExecutorService autoex;
	private List<Job> taskList = new ArrayList<>();
	private int nThreads;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

	/**
	 * Constructor
	 */
	public Management() {
		this.nThreads = Runtime.getRuntime().availableProcessors();

		init();
	}

	/**
	 * init the Pool-executer with the number of available resources
	 */
	private void init() {
		autoex = Executors.newFixedThreadPool(this.nThreads);
	}

	/**
	 *
	 * @return return all Jobs with State Running
	 */
	public List<Job> getRunningTasks() {
		return this.taskList.stream().filter(e -> e.getState().equals(Job.State.RUNNING)).collect(Collectors.toList());
	}

	/**
	 * fetch current system stats e.g. all running jobs, size of queue, etc
	 */
	public void fetchSystemStats() {

		while (!this.getAutoex().isTerminated()) {
			System.out.println("Report ------------------------");
			Date date = new Date(System.currentTimeMillis());
			System.out.println("Time: " + formatter.format(date));
			System.out.println("The List inludes " + this.taskList.size() + " Jobs");
			for (int i = 0; i < this.taskList.size(); i++) {
				Job currentElem = this.taskList.get(i);
				System.out.println("ThreadId: " + currentElem.getId());
				System.out.println("ThreadState: " + currentElem.getState());
			}
			System.out.println("The List inludes " + this.getRunningTasks().size() + " running Jobs");
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * mechanism that notifies jobs they need to abort and clean up.
	 */
	public void notifyCleanUp() {
		List<Job> deletedJobs = new ArrayList<>();
		for (int i = 0; i < this.taskList.size(); i++) {
			Job currentElem = this.taskList.get(i);
			if (currentElem.isCleanUpIsNeeded()) {
				this.taskList.remove(currentElem);
				currentElem.cleanUp();
				deletedJobs.add(currentElem);
			}
		}
		System.out.println("The List of removed Elements contains " + deletedJobs.size() + " Jobs");
	}

	public ExecutorService getAutoex() {
		return autoex;
	}

	public void setAutoex(ExecutorService autoex) {
		this.autoex = autoex;
	}

	public List<Job> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Job> taskList) {
		this.taskList = taskList;
	}

	public int getnThreads() {
		return nThreads;
	}

	public void setnThreads(int nThreads) {
		this.nThreads = nThreads;
	}

}
