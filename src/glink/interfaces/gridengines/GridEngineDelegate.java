package glink.interfaces.gridengines;

import glink.interfaces.models.Job;

import java.util.List;

//  Interface to be implemented for all grid engine types.
public interface GridEngineDelegate {
	
	public List<Job> getScheduledJobs();
	
	public List<Job> getAllPastJobs();
	
	public List<Job> getDateRangeJobs();
	

}
