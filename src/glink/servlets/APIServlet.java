package glink.servlets;

import glink.common.Constants;
import glink.common.Constants.APIReportTypes;
import glink.util.CSVUtil;
import glink.util.CacheUtil;
import glink.util.FieldLoaderUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.rewrite.RewriteAppender;

public class APIServlet extends HttpServlet{

	private static final long serialVersionUID = 4399516135480455346L;
	private Logger log;
	private String reportType;
	private String startDate = null;
	private String endDate = null;
	private String responseText;
	
	private long startTime;
	private long endTime;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log = Logger.getLogger(APIServlet.class); 
		log.info("APIServlet request received");
		if (req.getParameter("reportType") == null) {
			log.error("No parameter provided for reportType");
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			if (req.getParameter("startDate") == null) {
				startTime = 0;
				log.debug("The parameter for startDate provide is null");
			} else {
				startDate = req.getParameter("startDate");
				log.debug("The parameter for startDate provide is " + startDate);
				try {
					startTime = Timestamp.valueOf(startDate + " 00:00:00").getTime();
				} catch (IllegalArgumentException e) {
					startTime = -1;
					log.error("Illegal argument provided for startDate parameter");
				}
			}
			
			if (req.getParameter("endDate") == null) {
				//  Set startDate to some arbitrary date for null check to throw error on bad
				endTime = System.currentTimeMillis();
				log.debug("The parameter for endDate provide is null");
			} else {
				endDate = req.getParameter("endDate");
				log.debug("The parameter for endDate provide is " + endDate);
				try {
					endTime = Timestamp.valueOf(endDate + " 00:00:00").getTime();
				} catch (IllegalArgumentException e) {
					endTime = -1;
					log.error("Illegal argument provided for endDate parameter");
				}
			}
			
			if (endTime < 0 || startTime < 0) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else if (endTime < startTime) {
				log.error("The endTime is before the startTime");
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else {
				//  Build if else to deal with report types
				try {
				if (req.getParameter("reportType").equals(APIReportTypes.AllAccounts)) {
					responseText = CSVUtil.getAllAccountsCSV(startTime, endTime);
				}	else if (req.getParameter("reportType").equalsIgnoreCase(APIReportTypes.AllProjects)) {
					responseText = CSVUtil.getAllProjectsCSV(startTime, endTime);
				} else if (req.getParameter("reportType").equalsIgnoreCase(APIReportTypes.AllProjectsAccountDetails)) {
					responseText = CSVUtil.getAllProjectsAccountDetailsCSV(startTime, endTime);
				} else if (req.getParameter("reportType").equalsIgnoreCase(APIReportTypes.AllQueues)) {
					responseText = CSVUtil.getAllQueues(startTime, endTime);
				} else if (req.getParameter("reportType").equalsIgnoreCase(APIReportTypes.AllQueuesAccountDetails)) {
					responseText = CSVUtil.getAllQueuesAccountDetails(startTime, endTime);
				} else  {
					log.error("Invalid report of type " + req.getParameter("reportType") + " requested");
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
				} catch (SQLException e) {
					log.error("A SQL error has occured, " + e.getMessage());
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			}
			
		}
		ServletOutputStream out = resp.getOutputStream();
    	resp.setContentType("text/plain");
    	out.print(responseText);
    	out.close();
	}

}
