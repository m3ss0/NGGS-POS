package com.messo.pos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

public class UtilsCommon {
	
	private static Properties prop;
    
    static{
        try {
            prop = new Properties();
            prop.load(UtilsCommon.class.getResourceAsStream("/other/Resources.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	//Works in jar
//			prop.load(new FileReader(new File(UtilsCommon.class.getResource("/other/Resources.properties").toString())));/
            e.printStackTrace();
        }
    }
    
    public static boolean logFileOn = Boolean.valueOf(UtilsCommon.getPropertyValue("log.file"));
	
	public static String getPropertyValue(String key){
			return prop.getProperty(key);
	}
	
	public static String formatCifre(Double num){
    	return new DecimalFormat("0.00").format(num);
    }

}
