package com.bourntec.URLMonitor;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Main implements Job{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		final Map<String,Integer> resultsMap=new Hashtable<String,Integer>();
		PropertiesLoader propertiesLoader=PropertiesLoader.getInstance();		
		String[] urls = propertiesLoader.getUrls();
		for (String url : urls) {
			try{
			Thread t=new URLMonitor(url,resultsMap);
			t.start();
			t.join();
			}catch(InterruptedException e){
				e.printStackTrace();
				}
			}
		
		String messageBody="";
		if(!resultsMap.isEmpty()){
			Set<String> keys = resultsMap.keySet();
	        for(String key: keys){
	            //System.out.println("Value of "+key+" is: "+resultsMap.get(key));
	            int code=resultsMap.get(key);
	            messageBody+= key +" is <b>"+ (code==200?"available": "down");
	            messageBody+="</b><br/>";
	        }
		}
		SendMail.sendMail(propertiesLoader.getEmailConfig(), messageBody);
	}
}
