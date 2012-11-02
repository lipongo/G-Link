package glink.util;

import glink.common.Constants;
import glink.tasks.AccountingScan;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SchedulerUtil implements ServletContextListener {

	
	 private ScheduledExecutorService scheduler;

	    @Override
	    public void contextInitialized(ServletContextEvent event) {
	    	//  Setup all scheduled tasks
	        scheduler = Executors.newSingleThreadScheduledExecutor();
	        
	        //  Task to scan the accounting file for new entries
	        scheduler.scheduleAtFixedRate(new AccountingScan(), 0,Long.parseLong(String.valueOf(CacheUtil.getUniqueInstance().getProperties().get(Constants.GridEngineTypes.SGE.Properties.GRID_SCAN_TIME))), TimeUnit.MINUTES);
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent event) {
	    	
	    	//  Clean up on server shutdown
	        scheduler.shutdownNow();
	    }
}
