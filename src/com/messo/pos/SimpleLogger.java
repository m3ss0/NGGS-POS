package com.messo.pos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SimpleLogger {
	
	public static SimpleLogger getLogger = new SimpleLogger();
	
	private final Logger logger = Logger.getLogger("MyLog");
	private final String path = System.getProperty("user.home") + System.getProperty("file.separator") + UtilsCommon.getPropertyValue("log.path");
	
	private SimpleLogger(){

		// This block configure the logger with handler and formatter  
		try {
			FileHandler fh = new FileHandler(path, true);
			logger.addHandler(fh);
			fh.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return logTime.format(cal.getTime())
							+ " || " + record.getLevel() + " "
							/*
							+ record.getSourceClassName().substring(
									record.getSourceClassName().lastIndexOf(".")+1,
									record.getSourceClassName().length())
							+ "."
							+ record.getSourceMethodName()
							+ "() : "
							*/
							+ record.getMessage() + "\n";
				}
			});

			logger.setUseParentHandlers(false);;

		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}

    public void log(String severity, String message){

    		// the following statement is used to log any messages
    		if (!severity.equals("ERROR"))
    			logger.info(message);  
    		else logger.severe(message);
    		
    }
}
