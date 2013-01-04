package glink.managedbeans;

import glink.common.Constants.APIReportTypes;
import glink.common.Constants.Reports;
import glink.dao.GlinkDAO;
import glink.managedbeans.ParentBean;
import glink.models.ReportEntry;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

@ManagedBean (name="reports")
@RequestScoped
public class ReportsBean extends ParentBean {
	private List<ReportEntry> reportEntries = new ArrayList<ReportEntry>();
	private HashMap<String, Integer> labelPercentage = new HashMap<String, Integer>();
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
	private TreeMap<Integer, String> decimalToReportLabelMap = new TreeMap<Integer, String>();
	private String downloadableCSV = "";
	private String isDownloadable = "false";

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
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("api/index.xhtml?reportType=");
			if (reportName.equalsIgnoreCase(Reports.JobCountByProject.NAME)) {
				urlBuilder.append(APIReportTypes.AllProjects);
				generateProjectReport();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("[ ");
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInHours = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					stringBuilder.append("['").append(reportEntry.getProjectName()).append(" - ").append(labelPercentage.get(reportEntry.getProjectName())).append("%',").append(timeInHours).append("],");
					entries.add(Arrays.asList(new String[] {reportEntry.getProjectName(), timeInHours, String.valueOf(reportEntry.getJobCount()) }));
				}
				this.reportData = (stringBuilder.substring(0, stringBuilder.length() -1))  + " ]";
			} else if (reportName.equalsIgnoreCase(Reports.ProjectsByAccountReport.NAME)) {
				urlBuilder.append(APIReportTypes.AllProjectsAccountDetails);
				generateProjectUserReport();
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					if (reportEntry.getAccountName() !=  null) {
						entries.add(Arrays.asList(new String[] {  "  --  " + reportEntry.getProjectName(), reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
						
					} else {
						entries.add(Arrays.asList(new String[] {  reportEntry.getProjectName(), reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));						
					}
				}

			} else if (reportName.equalsIgnoreCase(Reports.AccountsReport.NAME)) {
				urlBuilder.append(APIReportTypes.AllAccounts);
				generateAccountsReport();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("[ ");
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInHours = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					stringBuilder.append("['").append(reportEntry.getAccountName()).append(" - ").append(labelPercentage.get(reportEntry.getAccountName())).append("%',").append(timeInHours).append("],");
					entries.add(Arrays.asList(new String[] {reportEntry.getAccountName(), timeInHours, String.valueOf(reportEntry.getJobCount()) }));
				}
				this.reportData = (stringBuilder.substring(0, stringBuilder.length() -1))  + " ]";
			} else if(reportName.equalsIgnoreCase(Reports.QueuesReport.NAME)) {
				urlBuilder.append(APIReportTypes.AllQueues);
				generateQueuesReport();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("[ ");
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInHours = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					stringBuilder.append("['").append(reportEntry.getQueueName()).append(" - ").append(labelPercentage.get(reportEntry.getQueueName())).append("%',").append(timeInHours).append("],");
					entries.add(Arrays.asList(new String[] {reportEntry.getQueueName(), timeInHours, String.valueOf(reportEntry.getJobCount()) }));
				}
				this.reportData = (stringBuilder.substring(0, stringBuilder.length() -1))  + " ]";
			} else if (reportName.equalsIgnoreCase(Reports.QueuesByAccountReport.NAME)) {
				urlBuilder.append(APIReportTypes.AllQueuesAccountDetails);
				generateQueuesAccountsReport();
				for (ReportEntry reportEntry : reportEntries) {
					DecimalFormat df = new DecimalFormat("#.##");
					String timeInSeconds = df.format(reportEntry.getTimeInMilliseconds()/3600.00);
					if (reportEntry.getAccountName() != null) {
						entries.add(Arrays.asList(new String[] { "  --  " + reportEntry.getQueueName(), reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));
					} else {
						entries.add(Arrays.asList(new String[] {reportEntry.getQueueName(), reportEntry.getAccountName(), timeInSeconds, String.valueOf(reportEntry.getJobCount()) }));						
					}
				}
			}
			validateEntries();
			// api/index.xhtml?reportType=AllProjectsAccountDetails&startDate=2009-12-01&endDate=2012-10-01
			if (startDate != null) {
				urlBuilder.append("&startDate=").append(startDate.getYear() + 1900).append("-").append((startDate.getMonth() + 1 < 10)? "0" + (startDate.getMonth() + 1) : startDate.getMonth() + 1 ).append("-").append((startDate.getDate() < 10)? "0" + startDate.getDate() : startDate.getDate() );
			}
			if (endDate != null) {
				urlBuilder.append("&endDate=").append(startDate.getYear() + 1900).append("-").append((endDate.getMonth() + 1 < 10)? "0" + (endDate.getMonth() + 1) : endDate.getMonth() + 1).append("-").append((endDate.getDate() < 10)? "0" + endDate.getDate() : endDate.getDate() );
			}
			downloadableCSV = urlBuilder.toString();
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

	private void generateLabels(String report) {
		double totalTime = reportEntries.remove(reportEntries.size() - 1).getTimeInMilliseconds() /3600.00;
		int total = 0;
		double onePercent = totalTime/100.00;
		DecimalFormat df = new DecimalFormat("#.###");
		DecimalFormat noDecimal = new DecimalFormat("#");
		for (ReportEntry reportEntry : reportEntries) {
			int decimals = Integer.valueOf(df.format((reportEntry.getTimeInMilliseconds() /3600.00)).split("\\.")[1]);
			if (report.equalsIgnoreCase(Reports.JobCountByProject.NAME)) {
				decimalToReportLabelMap.put(decimals, reportEntry.getProjectName());
			} else if (report.equalsIgnoreCase(Reports.AccountsReport.NAME)) {
				decimalToReportLabelMap.put(decimals, reportEntry.getAccountName());
			} else if (report.equalsIgnoreCase(Reports.QueuesReport.NAME)) {
				decimalToReportLabelMap.put(decimals, reportEntry.getQueueName());
			} else {
				//  Throw exception
			}

			double percentWithDecimal = (reportEntry.getTimeInMilliseconds()/3600.00) / onePercent;
			int percent;
			if (percentWithDecimal < 1) {
				percent = 0;
		//	} else if (percent > 0.5 && percent < 1) {
			//	labelPercentage.put(reportEntry.getProjectName(), 1);
			} else {
				percent = Integer.valueOf(noDecimal.format((reportEntry.getTimeInMilliseconds()/3600.00) / onePercent));
			}
			total += percent;
			if (report.equalsIgnoreCase(Reports.JobCountByProject.NAME)) {
				labelPercentage.put(reportEntry.getProjectName(), percent);
			} else if (report.equalsIgnoreCase(Reports.AccountsReport.NAME)) {
				labelPercentage.put(reportEntry.getAccountName(), percent);			
			} else if (report.equalsIgnoreCase(Reports.QueuesReport.NAME)) {
				labelPercentage.put(reportEntry.getQueueName(), percent);	
			} else {
				//  Throw exception
			}
		}
		if (decimalToReportLabelMap.keySet().size() > 0) {
			Object[] decimals = decimalToReportLabelMap.keySet().toArray();
			int loopControl = 100 - total;
			if (loopControl < 0) {
				//  Do stuff here and make sure to check for it already being zero, if zero go up one more.  Possible endless loop if all are percentage zero.  Figure out how to handle that
			} else {
				for (int i = 1; i <= loopControl; i++) {
					String key = decimalToReportLabelMap.get(decimals[decimals.length - i]);
					int percentage = labelPercentage.get(key);
					percentage++;
					labelPercentage.put(key, percentage);
				}
			}
		}

	}
	
	private void generateProjectReport() throws SQLException {
		headers = Reports.JobCountByProject.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getProjectsReport(startTime, endTime);
		if (reportEntries.size() > 0) {
			generateLabels(Reports.JobCountByProject.NAME);		
		}
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
		if (reportEntries.size() > 0) {
			generateLabels(Reports.AccountsReport.NAME);
		}
	}
	
	private void generateQueuesReport() throws SQLException {
		headers = Reports.QueuesReport.getFields();
		GlinkDAO dao = new GlinkDAO();
		this.reportEntries = dao.getJobCountByQueues(startTime, endTime);
		if (reportEntries.size() > 0) {
			generateLabels(Reports.QueuesReport.NAME);
		}
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
    
    private void validateEntries() {
    	if (entries.size() < 1) {
    		List<String> blankEntry = new ArrayList<String>();
    		for (int i = 0; i < headers.size(); i++) {
    			blankEntry.add(new String("No Results"));
    		}
    		entries.add(blankEntry);
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

	public String getDownloadableCSV() {
		return downloadableCSV;
	}

	public void setDownloadableCSV(String downloadableCSV) {
		this.downloadableCSV = downloadableCSV;
	}

	public String getIsDownloadable() {
		return isDownloadable;
	}

	public void setIsDownloadable(String isDownloadable) {
		this.isDownloadable = isDownloadable;
	}
	
}
