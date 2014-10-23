package com.bourntec.URLMonitor;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class URLMonitor extends Thread{
	private static int INITCODE=-1;
	private String URL;
	private Map<String,Integer> ResultMap;
	URLMonitor(String url,Map<String,Integer> resultMap)	{
		this.URL=url;
		this.ResultMap=resultMap;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		monitor(URL,ResultMap);
	}
	
	public int monitor(String url,Map<String,Integer> resultMap){
		int code =INITCODE;
		try{
			URL u = new URL ( url);
			HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
			huc.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.13) Gecko/2009073021 Firefox/3.0.13");
			huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
			huc.connect() ; 		
			code = huc.getResponseCode() ;			
			System.out.println(code +" "+ url);
			resultMap.put(url, code);
		}catch(MalformedURLException e){
			System.out.println(e);
		}		
		catch(Exception e){
			System.out.println(url);
			System.out.println("Error" + " "+e );
		}
		return code;
	}

}
