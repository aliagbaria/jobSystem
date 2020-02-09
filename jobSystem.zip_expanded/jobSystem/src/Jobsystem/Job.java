package Jobsystem;

/**
 *
 */
public class Job implements Runnable, functionsRun {

	private int id;
	private Type type;
	private int period;
	private long time;
	private boolean cleanUpIsNeeded;
	private State state;

	public enum State {
		NEW, RUNNING, FAILED, STOPPED, ISDONE
	}

	/**
	 *
	 * Constructor
	 */
	public Job(int id, Type type, int period) {

		this.id = System.identityHashCode(id);
		this.type = type;
		this.period = period;
		this.time = System.currentTimeMillis();
		this.state = State.NEW;
		this.cleanUpIsNeeded = false;
	}

	/**
	 * track the infos about the running job
	 */
	private void track() {

		System.out.println("State: " + this.state);
		System.out.println("Name: " + Thread.currentThread().getName());
		System.out.println("type: " + this.type.getName());
	}

	/**
	 * stop a running job
	 */
	public void stop() {

		if (this.state.equals(State.RUNNING)) {
			this.state = State.STOPPED;
		}

	}

	/**
	 * revive a job
	 */
	public void revive() {
		this.cleanUpIsNeeded = false;
		this.state = State.RUNNING;
	}

	/**
	 * parallel job
	 */
	public void run() {

		System.out.println("Started Thead " + Thread.currentThread().getName());

		/**
		 * Jobs can run both periodically and upon request. if period is 0 then it will
		 * be one run. if period >0 it will run periodically
		 */
		//
		while (!this.state.equals(State.STOPPED)) {
			this.state = State.RUNNING;
			triggerJob();
			track();
			if (period == 0) {
				break;
			}
			try {
				Thread.sleep(period);
			} catch (Exception e) {
				this.state = State.FAILED;
			}

		}

		this.state = this.state.equals(State.STOPPED) ? State.STOPPED : State.ISDONE;
		this.cleanUpIsNeeded = true;

		System.out.println("Ended Thead " + Thread.currentThread().getName());

	}

	/**
	 * do something to run the job.
	 */
	@Override
	public void triggerJob() {

		this.type.printMessage();

	}

	/**
	 * after the notification a CleanUp is triggered
	 */
	@Override
	public void cleanUp() {

		System.out.println("started cleaning up");

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return type.getName();
	}

	public void setType(String typename) {
		this.type.setName(typename);
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isCleanUpIsNeeded() {
		return cleanUpIsNeeded;
	}

	public void setCleanUpIsNeeded(boolean cleanUpIsNeeded) {
		this.cleanUpIsNeeded = cleanUpIsNeeded;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
