package com.bourntec.URLMonitor;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzJob {
	public static void main( String[] args ) throws Exception
    {
       	JobDetail job = new JobDetail();
    	job.setName("UrlMonitor");
    	job.setJobClass(Main.class);
    	PropertiesLoader propertiesLoader=PropertiesLoader.getInstance();
    	String triggerTimimng=propertiesLoader.getProperties().getProperty("trigger");
    	CronTrigger trigger = new CronTrigger();
    	trigger.setName("UrlMonitorTrigger");
    	trigger.setCronExpression(triggerTimimng);    	
    	
 
    	//schedule it
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);
 
    }
}
