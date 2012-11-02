package glink.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import glink.util.FieldLoaderUtil;

public class AccountingScan implements Runnable {
	
	private Logger log;

	@Override
	public void run() {
		log = Logger.getLogger(AccountingScan.class);
		long startTime = System.currentTimeMillis();
		FieldLoaderUtil fieldLoaderUtil = new FieldLoaderUtil();
		try {
			fieldLoaderUtil.readAccountingFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			long runTime = System.currentTimeMillis() - startTime;
			long seconds = runTime / 1000;
			long minutes = seconds / 60;
			long hours = minutes / 60;
			StringBuilder readableRunTime = new StringBuilder();
			if ( hours > 0) {
				readableRunTime.append(hours + " hours ");
			}
			if (hours > 0 && minutes > 0) {
				readableRunTime.append(minutes % 60 + " minutes ");
			}
			readableRunTime.append(seconds % 60 + " seconds.");
			log.info("Time to run fieldLoader was " + readableRunTime);
		}
		
		
	}

}
