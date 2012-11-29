package glink.managedbeans;

import glink.common.Constants.Reports;
import glink.dao.GlinkDAO;
import glink.interfaces.models.ReportEntry;
import glink.managedbeans.ParentBean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@ManagedBean (name="reports")
@RequestScoped
public class ReportsBean extends ParentBean {
	private List<ReportEntry> reportEntries = new ArrayList<ReportEntry>();
	private String reportData = new String();
	private List<String> headers = new ArrayList<String>();
	private List<SelectItem> reports = new ArrayList<SelectItem>();
	private String reportName = null;
	private Date startDate;
	private Date endDate;
	private long startTime;
	private long endTime;
	private HtmlPanelGroup reportDataPanel;
	private HtmlDataTable reportsTable;
	private List<List<String>> entries = new ArrayList<List<String>>();

	public ReportsBean() throws SQLException {
		List<String> reportNames = Reports.getReports();
		for (String reportName : reportNames) {
			SelectItem selectItem = new SelectItem(reportName, reportName);
			this.reports.add(selectItem);
		}
		reportDataPanel = new HtmlPanelGroup();
		reportsTable = new HtmlDataTable();
	}
	
//	public void generateReports() throws SQLException {
	public void generateReports(AjaxBehaviorEvent event) throws SQLException {

		if (reportName != null) {
			if (reportDataPanel.getChildCount() < 1) {
				reportDataPanel.getChildren().add(reportsTable);
			}
			reportsTable.getChildren().clear();
			if (startDate != null) {
				startTime = startDate.getTime();
			} else  {
				startTime = 0;
			}
			if (endDate != null) {
				endTime = endDate.getTime();
			} else {
				endTime = System.currentTimeMillis();
			}
			if (reportName.equalsIgnoreCase(Reports.JobCountByProject.NAME)) {
				generateProjectReport();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("[ ");
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					stringBuilder.append("['").append(reportEntry.getProjectName()).append("',").append(timeInSeconds).append("],");
					entries.add(Arrays.asList(new String[] {reportEntry.getProjectName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
				}
				this.reportData = (stringBuilder.substring(0, stringBuilder.length() -1))  + " ]";
			} else if (reportName.equalsIgnoreCase(Reports.ProjectsByAccountReport.NAME)) {
				generateProjectUserReport();
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);				 
					entries.add(Arrays.asList(new String[] { reportEntry.getProjectName(), reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
				}

			} else if (reportName.equalsIgnoreCase(Reports.AccountsReport.NAME)) {
				generateAccountsReport();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("[ ");
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					stringBuilder.append("['").append(reportEntry.getAccountName()).append("',").append(timeInSeconds).append("],");
					entries.add(Arrays.asList(new String[] {reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
				}
				this.reportData = (stringBuilder.substring(0, stringBuilder.length() -1))  + " ]";
			} else if(reportName.equalsIgnoreCase(Reports.QueuesReport.NAME)) {
				generateQueuesReport();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("[ ");
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					stringBuilder.append("['").append(reportEntry.getQueueName()).append("',").append(timeInSeconds).append("],");
					entries.add(Arrays.asList(new String[] {reportEntry.getQueueName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
				}
				this.reportData = (stringBuilder.substring(0, stringBuilder.length() -1))  + " ]";
			} else if (reportName.equalsIgnoreCase(Reports.QueuesByAccountReport.NAME)) {
				generateQueuesAccountsReport();
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					entries.add(Arrays.asList(new String[] {reportEntry.getQueueName(), reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
				}
			}
			

	      //  reportsTable.setValue(reportEntries);
	        reportsTable.setValueExpression("binding", createValueExpression("#{reports.reportsTable}", HtmlDataTable.class));
	        reportsTable.setValueExpression("value",createValueExpression("#{reports.entries}", List.class));
	        reportsTable.setVar("entry");
	        int i = 0;

	        // Iterate over columns.
	        for (String header : headers) {

	            // Create <h:column>.
	            HtmlColumn column = new HtmlColumn();
	            reportsTable.getChildren().add(column);


	            // Create <h:outputText value="dynamicHeaders[i]"> for <f:facet name="header"> of column.
	            HtmlOutputText headerText = new HtmlOutputText();
	            headerText.setValue(header);
	            column.setHeader(headerText);

	            // Create <h:outputText value="#{dynamicItem[" + i + "]}"> for the body of column.
	            HtmlOutputText output = new HtmlOutputText();
	            output.setValueExpression("value",createValueExpression("#{entry[" + i + "]}", String.class));
	            //output.setValue("#{entry." + header + "}");
	            column.getChildren().add(output);
	            i++;
	        }
		}
	}
	
	private void generateProjectReport() throws SQLException {
		headers = Reports.JobCountByProject.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getProjectsReport(startTime, endTime);
	}
	
	private void generateProjectUserReport() throws SQLException {
		headers = Reports.ProjectsByAccountReport.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getProjectsAccountReport(startTime, endTime);
	}
	
	private void generateAccountsReport() throws SQLException {
		headers = Reports.AccountsReport.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getJobCountByAccounts(startTime, endTime);
	}
	
	private void generateQueuesReport() throws SQLException {
		headers = Reports.QueuesReport.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getJobCountByQueues(startTime, endTime);
	}
	
	private void generateQueuesAccountsReport() throws SQLException {
		headers = Reports.QueuesByAccountReport.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getQueuesAccountsReport(startTime, endTime);		
	}
	
	
    private ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(
        		facesContext.getELContext(), valueExpression, valueType);
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

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<SelectItem> getReports() {
		return reports;
	}

	public void setReports(List<SelectItem> reports) {
		this.reports = reports;
	}

	public HtmlPanelGroup getReportDataPanel() {
		return reportDataPanel;
	}

	public void setReportDataPanel(HtmlPanelGroup reportDataPanel) {
		this.reportDataPanel = reportDataPanel;
	}

	public List<List<String>> getEntries() {
		return entries;
	}

	public void setEntries(List<List<String>> entries) {
		this.entries = entries;
	}

	public HtmlDataTable getReportsTable() {
		return reportsTable;
	}

	public void setReportsTable(HtmlDataTable reportsTable) {
		this.reportsTable = reportsTable;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getReportData() {
		return reportData;
	}

	public void setReportData(String reportData) {
		this.reportData = reportData;
	}
	
}
