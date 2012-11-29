package glink.interfaces.models;

public class ReportEntry {
	private String projectName;
	private String accountName;
	private String queueName;
	private Long timeInMilliseconds;
	private int jobCount = 0;
	
	public ReportEntry() {
		
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getJobCount() {
		return jobCount;
	}

	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}

	public Long getTimeInMilliseconds() {
		return timeInMilliseconds;
	}

	public void setTimeInMilliseconds(Long timeInSeconds) {
		this.timeInMilliseconds = timeInSeconds;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	

}
