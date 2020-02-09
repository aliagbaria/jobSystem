package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Jobsystem.*;




public class Tests {
	Job j ;
	Management management = new Management()  ;
	@Before
	public void setup() {
		 j = new Job(33,new Type("s3"),60000);
	
		
	}
	

	@Test
	public void testJobType() {
		assertEquals("s3",j.getTypeName());
		j.setType("Emailsender");
		assertEquals("Emailsender", j.getTypeName());
	}
	@Test
	public void testJobPeriod() {
		assertEquals(60000,j.getPeriod());
		j.setPeriod(320);
		assertEquals(320,j.getPeriod());
	}
	@Test
	public void testJobState() {
		assertEquals(Job.State.NEW,j.getState());
		j.setPeriod(0);
		j.run();
		assertEquals(Job.State.ISDONE,j.getState());
		j.revive();
		assertEquals(Job.State.RUNNING,j.getState());
		j.stop();
		assertEquals(Job.State.STOPPED,j.getState());
	}
	@Test
	public void testManagement() {
		 Job j1 = new Job(33,new Type("fetch-s3"),0);
		 Thread t1 = new Thread(j1);
		 Job j2 = new Job(33,new Type("email-client"),0);
		 Thread t2 = new Thread(j2);
		 Job j3 = new Job(33,new Type("other"),0);
		 Thread t3 = new Thread(j3);
		 assertEquals(Job.State.NEW,j1.getState());
		 assertEquals(Job.State.NEW,j2.getState());
		 assertEquals(Job.State.NEW,j3.getState());
		 management.getAutoex().submit(t1);
		 management.getAutoex().submit(t2);
		 management.getAutoex().submit(t3);
		 management.getTaskList().add(j1);
		 management.getTaskList().add(j2);
		 management.getTaskList().add(j3);
		 management.getAutoex().shutdown();
		 assertEquals(Job.State.ISDONE,j2.getState());
		 assertEquals(Job.State.ISDONE,j3.getState());
		 
		 
	}
	@Test
	public void testGetRunningTasks() {
		 Job j1 = new Job(33,new Type("fetch-s3"),0);
		 Thread t1 = new Thread(j1);
		 Job j2 = new Job(33,new Type("email-client"),0);
		 Thread t2 = new Thread(j2);
		 Job j3 = new Job(33,new Type("other"),0);
		 Thread t3 = new Thread(j3);
		 assertEquals(Job.State.NEW,j1.getState());
		 assertEquals(Job.State.NEW,j2.getState());
		 assertEquals(Job.State.NEW,j3.getState());
		 management.getAutoex().submit(t1);
		 management.getAutoex().submit(t2);
		 management.getAutoex().submit(t3);
		 management.getTaskList().add(j1);
		 management.getTaskList().add(j2);
		 management.getTaskList().add(j3);
		 management.getAutoex().shutdown();
		 assertEquals(0,management.getRunningTasks().size());	 
		 
	}
	@Test
	public void testNotifyCleanUp() {
		 Job j1 = new Job(33,new Type("fetch-s3"),0);
		 Thread t1 = new Thread(j1);
		 Job j2 = new Job(33,new Type("email-client"),0);
		 Thread t2 = new Thread(j2);
		 assertEquals(Job.State.NEW,j1.getState());
		 assertEquals(Job.State.NEW,j2.getState());
		 management.getAutoex().submit(t1);
		 management.getAutoex().submit(t2);
		 management.getTaskList().add(j1);
		 management.getTaskList().add(j2);
		 management.getAutoex().shutdown();
		 management.notifyCleanUp();
		 assertEquals(1,management.getTaskList().size());
		 
		 
		 
	}


}
