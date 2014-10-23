package com.bourntec.URLMonitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {
	private static String CONFIG_PROPERTIES="config.properties";
	Properties prop;
	private static PropertiesLoader propertiesLoader=null;
	private PropertiesLoader(){
		init();
	}
	public static PropertiesLoader getInstance(){
		if(propertiesLoader!=null){
			return propertiesLoader;
		}else{
			return new PropertiesLoader();
		}		
	}
	public Properties getProperties(){
		return prop;
	}
	private void init(){
		prop = new Properties();
		InputStream input = null;	 
		try {
			
			input = getClass().getResourceAsStream(CONFIG_PROPERTIES);	 
			//input = new FileInputStream(CONFIG_PROPERTIES);
			prop.load(input);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 
		}
	  }
	public String[] getUrls(){
		String st=prop.getProperty("URL");
		return st.split(",");
	}
	
	public Map<String,String> getEmailConfig(){
		Map<String,String> emailConfig=new HashMap();		
		emailConfig.put("mailTo",prop.getProperty("mailTo"));
		emailConfig.put("mailFrom",prop.getProperty("mailFrom"));
		emailConfig.put("host",prop.getProperty("host"));
		emailConfig.put("emailSubject",prop.getProperty("emailSubject"));
		emailConfig.put("emailUser",prop.getProperty("emailUser"));
		emailConfig.put("emailPass",prop.getProperty("emailPass"));
		emailConfig.put("emailPort",prop.getProperty("emailPort"));		
		return emailConfig;
		
	}
	
	public static void main(String a[]){
		new PropertiesLoader();
	}
}
