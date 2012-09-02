package glink.models;

import java.util.HashMap;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import Exceptions.GlinkException;

import glink.common.Constants;
import glink.interfaces.models.Job;

public class SGEJob implements Job {
	
	public static String jobType = Constants.GridEngineTypes.SGE.NAME; 
	private int jobId;
	private HashMap<String, String> attributes = null;
	private Log4JLogger log;
	
	public SGEJob(int JobId) {
		super();
		log = new Log4JLogger();
		log.info("New SGEJob created with jobId " + jobId);
	}
	
	public String getAttribute(String attributeName) throws GlinkException {
		log.info("Looking for " + attributeName + " in SGEJob " + jobId);
		if (attributes != null) {
			if (attributes.containsKey(attributeName)) {
				log.info("Attribute " + attributeName + " found in SGEJob " + jobId);
				return attributes.get(attributeName);
			} else {
				log.info("Attribute " + attributeName + " not found in SGEJob " + jobId);
				throw new GlinkException("Attribute " + attributeName + " not found in SGEJob", new Exception());	
			}
		} else {
			log.info("Attribute name is null when seaching for it in SGEJob " + jobId);
			throw new GlinkException("Attrubute name is null when searching for it in SGEJob " + jobId, new Exception());
		}
	}
	
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

}
