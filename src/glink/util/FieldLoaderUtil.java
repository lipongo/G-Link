package glink.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import glink.common.Constants;
import glink.common.Constants.GridEngineTypes;
import glink.common.Constants.GridEngineTypes.SGE;
import glink.dao.GlinkDAO;

public class FieldLoaderUtil {

	private String accountingFile;
	private Logger log;
	
	public FieldLoaderUtil() {
		accountingFile = CacheUtil.getUniqueInstance().getProperties().getProperty(Constants.GridEngineTypes.SGE.Properties.GRID_ACCOUNTING_FILE);
		log = Logger.getLogger(FieldLoaderUtil.class); 
	}
	
	public void readAccountingFile() throws SQLException, IOException {
		File file = new File(accountingFile);
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
		GlinkDAO dao = new GlinkDAO();
		HashMap<String, HashMap<String, Integer>> projectUserMap = new HashMap<String, HashMap<String, Integer>>();
		int i = 0; // Total lines processed counter
		long offset = dao.getLastOffset();
		long lastOffset = offset;
		
		if ( offset != 0) {
			log.debug("Last accounting file offset greater than zero");
			log.debug("File length is " + randomAccessFile.length());
			randomAccessFile.seek(offset);
			log.debug("Offset now set to " + randomAccessFile.getFilePointer());
		}
		
		log.debug("Offset set to " + randomAccessFile.getFilePointer());
		String lastLine = null;
		String line = randomAccessFile.readLine();
		
		while (line != null) {
			// Add in line to record offset and then line to figure out how to get scan right.
			if (i == 0 && offset != 0) {
				
				if (! line.equalsIgnoreCase(dao.getLastRecord())) {
					// Insert throwing an exception 
					log.error("Last record not equal.");
					log.debug("Current line from accounting file: " + line);
					log.debug("Last Record from place_holder database: " + dao.getLastRecord());
					break;
				} else {
					log.info("Last record equal current record");
					log.debug("Current line from accounting file: " + line);
					log.debug("Last Record from place_holder database: " + dao.getLastRecord());
					lastLine = line;
					line = randomAccessFile.readLine();
					if (line != null) {
						offset = randomAccessFile.getFilePointer();
					} else {
						break;
					}
				}
				
			} else {
				String[] positions = line.split(":");
				dao.insertAccoutingEntry(positions);
				if (projectUserMap.containsKey(positions[SGE.AccountingFile.Fields.Project.POSITION])) {
					HashMap<String, Integer> project = projectUserMap.get(positions[SGE.AccountingFile.Fields.Project.POSITION]);
					
					if (project.get(positions[SGE.AccountingFile.Fields.Owner.POSITION]) == null) {
						project.put(positions[SGE.AccountingFile.Fields.Owner.POSITION], 1);
					} else {
						int userCount = project.get(positions[SGE.AccountingFile.Fields.Owner.POSITION]);
						userCount++;
						project.put(positions[SGE.AccountingFile.Fields.Owner.POSITION], userCount);
					}
					
					projectUserMap.put(positions[SGE.AccountingFile.Fields.Project.POSITION], project);
				} else {
					HashMap<String, Integer> project = new HashMap<String, Integer>();
					project.put(positions[SGE.AccountingFile.Fields.Owner.POSITION], 1);
					projectUserMap.put(positions[SGE.AccountingFile.Fields.Project.POSITION], project);
				}
				// Should I not be doing a get every time through he loop and set a variable outside the loop to use?
			}
			
			i++;
			System.out.println(offset + " - " + line);
			lastLine = line;
			line = randomAccessFile.readLine();
			if (line != null) {
				lastOffset = offset;
				offset = randomAccessFile.getFilePointer();
			}
		}
		offset = randomAccessFile.getFilePointer();

		if ( i > 0) {
			//  This if statement is an attempt to handle the single record update bug.  Need to test
			if (i == 1) {
				dao.updateLastRecord(offset, lastLine);
			}
			// Update place_holder
			dao.updateLastRecord(lastOffset, lastLine);
		}
		
		log.info("Processed " + i + " new records");
		for (Map.Entry<String, HashMap<String, Integer>> project : projectUserMap.entrySet()) {
		
			for (Map.Entry<String, Integer> user : project.getValue().entrySet()) {
				dao.updateProjectUserCount(project.getKey(), user.getKey(), user.getValue());
			}
		}
		//outputResults(projectUserMap, dao);

		dao.close();
		randomAccessFile.close();
	}

	private void outputResults(HashMap<String, HashMap<String, Integer>> projectUserMap, GlinkDAO dao) throws SQLException {
		for (Map.Entry<String, HashMap<String, Integer>> project : projectUserMap.entrySet()) {
			System.out.println("Project: " + project.getKey());
			for (Map.Entry<String, Integer> user : project.getValue().entrySet()) {
				dao.updateProjectUserCount(project.getKey(), user.getKey(), user.getValue());
				System.out.println(user.getKey() + " - " + user.getValue());
			}
		}
	}
	public String getAccountingFile() {
		return accountingFile;
	}

	public void setAccountingFile(String accountingFile) {
		this.accountingFile = accountingFile;
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		FieldLoaderUtil loader = new FieldLoaderUtil();
		loader.readAccountingFile();
	}
}
