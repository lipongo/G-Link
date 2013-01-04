package glink.util;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import glink.common.Constants.Reports;
import glink.dao.GlinkDAO;
import glink.models.ReportEntry;

public class CSVUtil {
	
	public static String getAllAccountsCSV(long startTime, long endTime) throws SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		GlinkDAO dao = new GlinkDAO();
		List<ReportEntry> reportEntries = dao.getJobCountByAccounts(startTime, endTime);
		//  Add header to CSV
		List<String> headers = Reports.AccountsReport.getFields();
		for (int i = 0; i < headers.size(); i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(headers.get(i));
		}
		stringBuilder.append("\n");
		
		//  Decimal formatter to handle oddity of Long/Double to String
		DecimalFormat df = new DecimalFormat("#.##");
		// Add Data to CSV
		for (ReportEntry reportEntry : reportEntries) {
			stringBuilder.append(reportEntry.getAccountName()).append(",").append(df.format(reportEntry.getTimeInMilliseconds()/3600.00)).append(",").append(reportEntry.getJobCount()).append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static String getAllProjectsCSV(long startTime, long endTime) throws SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		GlinkDAO dao = new GlinkDAO();
		List<ReportEntry> reportEntries = dao.getProjectsReport(startTime, endTime);
		//  Add header to CSV
		List<String> headers = Reports.JobCountByProject.getFields();
		for (int i = 0; i < headers.size(); i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(headers.get(i));
		}
		stringBuilder.append("\n");
		
		//  Decimal formatter to handle oddity of Long/Double to String
		DecimalFormat df = new DecimalFormat("#.##");
		// Add Data to CSV
		for (ReportEntry reportEntry : reportEntries) {
			stringBuilder.append(reportEntry.getProjectName()).append(",").append(df.format(reportEntry.getTimeInMilliseconds()/3600.00)).append(",").append(reportEntry.getJobCount()).append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static String getAllProjectsAccountDetailsCSV(long startTime, long endTime) throws SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		GlinkDAO dao = new GlinkDAO();
		List<ReportEntry> reportEntries = dao.getProjectsAccountReport(startTime, endTime);
		//  Add header to CSV
		List<String> headers = Reports.ProjectsByAccountReport.getFields();
		for (int i = 0; i < headers.size(); i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(headers.get(i));
		}
		stringBuilder.append("\n");
		
		//  Decimal formatter to handle oddity of Long/Double to String
		DecimalFormat df = new DecimalFormat("#.##");
		// Add Data to CSV
		for (ReportEntry reportEntry : reportEntries) {
			stringBuilder.append(reportEntry.getProjectName()).append(",").append((reportEntry.getAccountName() == null)?"":reportEntry.getAccountName()).append(",").append(df.format(reportEntry.getTimeInMilliseconds()/3600.00)).append(",").append(reportEntry.getJobCount()).append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static String getAllQueues(long startTime, long endTime) throws SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		GlinkDAO dao = new GlinkDAO();
		List<ReportEntry> reportEntries = dao.getJobCountByQueues(startTime, endTime);
		//  Add header to CSV
		List<String> headers = Reports.AccountsReport.getFields();
		for (int i = 0; i < headers.size(); i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(headers.get(i));
		}
		stringBuilder.append("\n");
		
		//  Decimal formatter to handle oddity of Long/Double to String
		DecimalFormat df = new DecimalFormat("#.##");
		// Add Data to CSV
		for (ReportEntry reportEntry : reportEntries) {
			stringBuilder.append(reportEntry.getQueueName()).append(",").append(df.format(reportEntry.getTimeInMilliseconds()/3600.00)).append(",").append(reportEntry.getJobCount()).append("\n");
		}
		return stringBuilder.toString();
	}
	
	public static String getAllQueuesAccountDetails(long startTime, long endTime) throws SQLException {
		StringBuilder stringBuilder = new StringBuilder();
		GlinkDAO dao = new GlinkDAO();
		List<ReportEntry> reportEntries = dao.getQueuesAccountsReport(startTime, endTime);
		//  Add header to CSV
		List<String> headers = Reports.AccountsReport.getFields();
		for (int i = 0; i < headers.size(); i++) {
			if (i != 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(headers.get(i));
		}
		stringBuilder.append("\n");
		
		//  Decimal formatter to handle oddity of Long/Double to String
		DecimalFormat df = new DecimalFormat("#.##");
		// Add Data to CSV
		for (ReportEntry reportEntry : reportEntries) {
			stringBuilder.append(reportEntry.getQueueName()).append(",").append((reportEntry.getAccountName() == null)?"":reportEntry.getAccountName()).append(",").append(df.format(reportEntry.getTimeInMilliseconds()/3600.00)).append(",").append(reportEntry.getJobCount()).append("\n");
		}
		return stringBuilder.toString();
	}

}
