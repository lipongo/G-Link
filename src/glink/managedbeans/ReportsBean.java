package glink.managedbeans;

import glink.common.Constants.Reports;
import glink.dao.GlinkDAO;
import glink.interfaces.models.ReportEntry;
import glink.managedbeans.ParentBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean (name="reports")
@RequestScoped
public class ReportsBean extends ParentBean {
	private List<ReportEntry> reportEntries = new ArrayList<ReportEntry>();
	private List<String> headers = new ArrayList<String>();
	private String reportName;

	public ReportsBean() throws SQLException {
		// TODO Auto-generated constructor stub
		generateProjectReport();
	}
	
	private void generateProjectReport() throws SQLException {
		//  Build headers
		headers.add(Reports.Projects.Fields.PROJECT);
		headers.add(Reports.Projects.Fields.JOB_COUNT);
	
		GlinkDAO dao = new GlinkDAO();
		HashMap<String, Integer> projects = dao.getProjectsReport();
		for (Map.Entry<String, Integer> entry : projects.entrySet()) {
			ReportEntry reportEntry = new ReportEntry();
			reportEntry.setProjectName(entry.getKey());
			reportEntry.setJobCount(entry.getValue());
			reportEntries.add(reportEntry);
		}
	}
	

	
	public List<ReportEntry> getReportEntries() {
		return reportEntries;
	}

	public void setReportEntries(List<ReportEntry> reportEntries) {
		this.reportEntries = reportEntries;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	
}
