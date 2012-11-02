package glink.dao;

import glink.common.Constants;
import glink.common.Constants.GridEngineTypes;
import glink.common.Constants.Queries;
import glink.common.Constants.Tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class GlinkDAO {

	    // Constants ----------------------------------------------------------------------------------

		//  Setting up database connection variables
		private Properties properties = new Properties();
		private static final String PROPERTIES_FILE = "localdatabase.prop";
		
	    private String databaseServer;
		
		private String databaseName;
		
		private String databaseUser;
		
		private String databasePassword;

		/** The database connection. */
		private Connection databaseConnection;
		
		 private Logger log;

	    // Vars ---------------------------------------------------------------------------------------


	    // Constructors -------------------------------------------------------------------------------

	    public GlinkDAO() {
	    	try {
				properties.load(GlinkDAO.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
				setDatabaseServer(properties.getProperty("databaseServer"));
				setDatabaseName(properties.getProperty("databaseName"));
				setDatabaseUser(properties.getProperty("databaseUser"));
				setDatabasePassword(properties.getProperty("databasePassword"));
				log = Logger.getLogger(GlinkDAO.class);
				log.info("New GlinkDAO created");
				//  Should I include password in debug log level?
				log.debug("GlinkDAO using database server " + getDatabaseServer() + ", database name " + getDatabaseName() + ", database user " + getDatabaseUser());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    
	    }

	    // Actions ------------------------------------------------------------------------------------
	    
	    /**
	     * Gets the connection.
	     *
	     * @return the connection
	     * @throws SQLException the sQL exception
	     */
	    private Connection getConnection() throws SQLException  {
	    	Connection connection = DriverManager.getConnection("jdbc:mysql://" + databaseServer + ":3306/" + databaseName, databaseUser ,databasePassword);
	    	return connection;
	    }
	    
	    /**
	     * Open connection.
	     *
	     * @throws SQLException the sQL exception
	     */
	    private void openConnection() throws SQLException {
	    	if (this.databaseConnection == null || (! this.databaseConnection.isValid(0))) {
	    		this.databaseConnection = getConnection();
	    	}
	    	
	    }
	    
	    private void closeConnection() throws SQLException {
	    	this.databaseConnection.close();
	    }
	    
	    public boolean getConnectionState() throws SQLException {
	    	System.out.println(properties.toString());
	    	openConnection();
	    	return this.databaseConnection.isValid(0);
	    }
	    
	    public void close() throws SQLException {
	    	closeConnection();
	    }
	    
	    public void updateLastRecord(long offset, String lastRecord) throws SQLException {
	    	openConnection();
	    	PreparedStatement updatePlaceHolderFalseQuery = databaseConnection.prepareStatement(Queries.UPDATE_PLACE_HOLDER_IS_LATEST_SET_FALSE);
	    	updatePlaceHolderFalseQuery.execute();
	    	PreparedStatement updatePlaceHolderTrueQuery = databaseConnection.prepareStatement(Queries.INSERT_PLACE_HOLDER_NEW_LATEST_RECORD);
	    	updatePlaceHolderTrueQuery.setLong(1, offset);
	    	updatePlaceHolderTrueQuery.setString(2, lastRecord);
	    	updatePlaceHolderTrueQuery.execute();
	    }
	    
	    public boolean insertAccoutingEntry(String[] positions) throws SQLException {
			String queueName = positions[GridEngineTypes.SGE.AccountingFile.Fields.QueueName.POSITION];
			String hostName = positions[GridEngineTypes.SGE.AccountingFile.Fields.HostName.POSITION];
			String group = positions[GridEngineTypes.SGE.AccountingFile.Fields.Group.POSITION];
			String owner = positions[GridEngineTypes.SGE.AccountingFile.Fields.Owner.POSITION];
			String jobName = positions[GridEngineTypes.SGE.AccountingFile.Fields.JobName.POSITION];
			int jobNumber =  Integer.valueOf(positions[GridEngineTypes.SGE.AccountingFile.Fields.JobNumber.POSITION]);
			String account = positions[GridEngineTypes.SGE.AccountingFile.Fields.Account.POSITION];
			long submissionTime = Long.valueOf(positions[GridEngineTypes.SGE.AccountingFile.Fields.SubmissionTime.POSITION]);
			long startTime = Long.valueOf(positions[GridEngineTypes.SGE.AccountingFile.Fields.StartTime.POSITION]);
			long endTime = Long.valueOf(positions[GridEngineTypes.SGE.AccountingFile.Fields.EndTime.POSITION]);
			String project = positions[GridEngineTypes.SGE.AccountingFile.Fields.Project.POSITION];
			String grantedPE = positions[GridEngineTypes.SGE.AccountingFile.Fields.GrantedPE.POSITION];
	    	openConnection();
	    	PreparedStatement insertAccountingEntryQuery = databaseConnection.prepareStatement(Constants.Queries.INSERT_ACCOUNTING_ENTRY);
	    	insertAccountingEntryQuery.setString(1, queueName);
	    	insertAccountingEntryQuery.setString(2, hostName);
	    	insertAccountingEntryQuery.setString(3, group);
	    	insertAccountingEntryQuery.setString(4, owner);
	    	insertAccountingEntryQuery.setString(5,jobName);
	    	insertAccountingEntryQuery.setInt(6, jobNumber);
	    	insertAccountingEntryQuery.setString(7, account);
	    	insertAccountingEntryQuery.setLong(8, submissionTime);
	    	insertAccountingEntryQuery.setLong(9, startTime);
	    	insertAccountingEntryQuery.setLong(10, endTime);
	    	insertAccountingEntryQuery.setString(11, project); 
	    	insertAccountingEntryQuery.setString(12, grantedPE);
	    	boolean isSuccessful = insertAccountingEntryQuery.execute();
	    	insertAccountingEntryQuery.close();
	    	closeConnection();
	    	return isSuccessful;
	    }
	    
	    public String getLastRecord() throws SQLException {
	    	openConnection();
	    	PreparedStatement selectPlaceHolderQuery = databaseConnection.prepareStatement(Queries.SELECT_LAST_RECORD_PLACE_HOLDER);
	    	selectPlaceHolderQuery.execute();
	    	ResultSet selectPlaceHolderQueryResults = selectPlaceHolderQuery.getResultSet();
	    	selectPlaceHolderQueryResults.first();
	    	return selectPlaceHolderQueryResults.getString("last_record");
	    }
	    
	    public void updateProjectUserCount(String projectName, String accountName, int count) throws SQLException {
			openConnection();
			
			// Does account record exist
			if (! doesAccountExist(accountName)) {
				createNewAccount(accountName);
			}
			
			//  Does project record exist
			if (! doesProjectExist(projectName)) {
				createNewProject(projectName);
			}
			
			//  Does project_account record exist
			if (! doesProjectAccountExist(projectName, accountName)) {
				createProjectAccount(projectName, accountName, count);
			} else {
				updateProjectAccountCountRecord(projectName, accountName, count);
			}
		}
	    
	    public long getLastOffset() throws SQLException {
	    	openConnection();
	    	PreparedStatement selectPlaceHolderQuery = databaseConnection.prepareStatement(Queries.SELECT_OFFSET_PLACE_HOLDER);
	    	selectPlaceHolderQuery.execute();
	    	ResultSet selectPlaceHolderQueryResults = selectPlaceHolderQuery.getResultSet();
	    	if (selectPlaceHolderQueryResults.first()) {
	    		return selectPlaceHolderQueryResults.getLong(Tables.PlaceHolder.Fields.OFFSET);
	    	} else {
	    		return 0;
	    	}
	    }
	    
	    public HashMap<String,HashMap<String, Long>> getProjectUserByDates(Date startDate, Date endDate) {

	    	return null;
	    }
	    
	    private void updateProjectAccountCountRecord(String projectName, String accountName, int count) throws SQLException {
	    	PreparedStatement selectProjectAccountCountQuery = databaseConnection.prepareStatement(Queries.SELECT_PROJECT_ACCOUNT_COUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME);
	    	selectProjectAccountCountQuery.setString(1, projectName);
	    	selectProjectAccountCountQuery.setString(2, accountName);
	    	selectProjectAccountCountQuery.execute();
	    	ResultSet selectProjectAccountCountQueryResult = selectProjectAccountCountQuery.getResultSet();
	    	selectProjectAccountCountQueryResult.first();
	    	int queryCount = selectProjectAccountCountQueryResult.getInt(Tables.ProjectAccount.Fields.JOB_COUNT);
	    	PreparedStatement updateProjectAccountCountQuery = databaseConnection.prepareStatement(Queries.UPDATE_PROJECT_ACCOUNT_COUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME);
	    	updateProjectAccountCountQuery.setInt(1, (count + queryCount));
	    	updateProjectAccountCountQuery.setString(2, projectName);
	    	updateProjectAccountCountQuery.setString(3, accountName);
	    	updateProjectAccountCountQuery.execute();
	    }
	    
	    
	    private void createNewProject(String projectName) throws SQLException {
	    	PreparedStatement insertProjectQuery = databaseConnection.prepareStatement(Queries.INSERT_NEW_PROJECT);
	    	insertProjectQuery.setString(1, projectName);
	    	insertProjectQuery.execute();
	    }
	    
	    private void createProjectAccount(String projectName, String accountName, int count) throws SQLException {
	    	PreparedStatement insertProjectAccountQuery = databaseConnection.prepareStatement(Queries.INSERT_PROJECT_ACCOUNT_COUNT_BY_NAMES);
	    	insertProjectAccountQuery.setString(1, projectName);
	    	insertProjectAccountQuery.setString(2, accountName);
	    	insertProjectAccountQuery.setInt(3, count);
	    	insertProjectAccountQuery.execute();
	    }
	    
	    private void createNewAccount(String accountName) throws SQLException {
	    	PreparedStatement insertAccountQuery = databaseConnection.prepareStatement(Queries.INSERT_NEW_ACCOUNT);
	    	insertAccountQuery.setString(1, accountName);
	    	insertAccountQuery.execute();
	    }
	    
	    private boolean doesAccountExist(String accountName) throws SQLException {
	    	PreparedStatement selectAccountQuery = databaseConnection.prepareStatement(Constants.Queries.SELECT_ACCOUNT_BY_ACCOUNT_NAME);
    		selectAccountQuery.setString(1, accountName);
    		selectAccountQuery.execute();
    		ResultSet selectAccountQueryResults = selectAccountQuery.getResultSet();
    		return selectAccountQueryResults.first();
	    }
	    
	    private boolean doesProjectExist(String projectName) throws SQLException {
	    	PreparedStatement selectProjectQuery = databaseConnection.prepareStatement(Queries.SELECT_PROJECT_BY_PROJECT_NAME);
	    	selectProjectQuery.setString(1, projectName);
	    	selectProjectQuery.execute();
	    	ResultSet selectProjectQueryResults = selectProjectQuery.getResultSet();
	    	return selectProjectQueryResults.first();
	    }

	    private boolean doesProjectAccountExist(String projectName, String accountName) throws SQLException {
	    	PreparedStatement selectProjectAccountQuery = databaseConnection.prepareStatement(Queries.SELECT_PROJECT_ACCOUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME);
	    	selectProjectAccountQuery.setString(1, projectName);
	    	selectProjectAccountQuery.setString(2, accountName);
	    	selectProjectAccountQuery.execute();
	    	ResultSet selectProjectAccountQueryResultSet = selectProjectAccountQuery.getResultSet();
	    	return selectProjectAccountQueryResultSet.first();
	    }
	    
	    private int getProjectAccountCount(String accountName, String projectName) throws SQLException {
	    	PreparedStatement selectProjectAccountCountQuery = databaseConnection.prepareStatement(Queries.SELECT_PROJECT_ACCOUNT_COUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME);
	    	selectProjectAccountCountQuery.setString(1, projectName);
	    	selectProjectAccountCountQuery.setString(2, accountName);
	    	selectProjectAccountCountQuery.execute();
	    	ResultSet selectProjectAccountCountQueryResults = selectProjectAccountCountQuery.getResultSet();
	    	if (selectProjectAccountCountQueryResults.getRow() > 0) {
	    		if (selectProjectAccountCountQueryResults.next()) {
//	    			log.error("Get count for project " + projectName + "and account " + accountName + "returned more than one result");
	    			//  Insert exception throwing and should be handled up stream.
	    			//  Find more elegant method of doing this.
	    			return -1;
	    		} else {
	    			selectProjectAccountCountQueryResults.first();
	    			return selectProjectAccountCountQueryResults.getInt(Tables.ProjectAccount.Fields.JOB_COUNT);
	    		}
	    	} else {
//	    		log.error("Get count for project " + projectName + "and account " + accountName + "returned no results");
	    		//  Find more elegant method of doing this.
	    		return 0;
	    	}
	    }
	    
	    public HashMap<String, Integer> getProjectsReport() throws SQLException {
	    	openConnection();
	    	PreparedStatement selectProjectReportQuery = databaseConnection.prepareStatement(Queries.SELECT_PROJECT_ACCOUNT_PROJECT_NAME_COUNT);
	    	selectProjectReportQuery.execute();
	    	ResultSet selectProjectReportQueryResults = selectProjectReportQuery.getResultSet();
	    	HashMap<String, Integer> projects = new HashMap<String, Integer>();
	    	while (selectProjectReportQueryResults.next()) {
	    		projects.put(selectProjectReportQueryResults.getString("p." + Tables.Projects.Fields.PROJECT_NAME), selectProjectReportQueryResults.getInt(Tables.ProjectAccount.Fields.JOB_COUNT));
	    	}
	    	return projects;
	    }
	    
	    //  Begin Getters and Setters
	    public Properties getProperties() {
			return properties;
		}

		public void setProperties(Properties properties) {
			this.properties = properties;
		}

		public String getDatabaseServer() {
			return databaseServer;
		}

		public void setDatabaseServer(String databaseServer) {
			this.databaseServer = databaseServer;
		}

		public String getDatabaseName() {
			return databaseName;
		}

		public void setDatabaseName(String databaseName) {
			this.databaseName = databaseName;
		}

		public String getDatabaseUser() {
			return databaseUser;
		}

		public void setDatabaseUser(String databaseUser) {
			this.databaseUser = databaseUser;
		}

		public String getDatabasePassword() {
			return databasePassword;
		}

		public void setDatabasePassword(String databasePassword) {
			this.databasePassword = databasePassword;
		}

		public Connection getDatabaseConnection() {
			return databaseConnection;
		}

		public void setDatabaseConnection(Connection databaseConnection) {
			this.databaseConnection = databaseConnection;
		}

		//  Does this need to be Static? 
		public static String getPropertiesFile() {
			return PROPERTIES_FILE;
		}
}
