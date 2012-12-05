package glink.common;

import java.util.ArrayList;
import java.util.List;

//  Using the Constant Data Manager design pattern, store all immutable centrally accessible information. 
public class Constants {

	public static class Tables {

		public static class Accounting {
			public static final String NAME = "accounting";
			
			public static class Fields {
				public static final String ID = "id";
				public static final String QUEUE_NAME = "queue_name";
				public static final String HOST_NAME = "host_name";
				public static final String GROUP = "group";
				public static final String OWNER = "owner";
				public static final String JOB_NAME = "job_name";
				public static final String JOB_NUMBER = "job_number";
				public static final String ACCOUNT = "account";
				public static final String SUBMISSION_TIME = "submission_time";
				public static final String START_TIME = "start_time";
				public static final String END_TIME = "end_time";
				public static final String PROJECT = "project";
				public static final String GRANTED_PE = "granted_pe";		
			}
		}

		
		public static class Accounts {
			public static final String NAME = "accounts";
			public static class Fields {
				public static final String ID = "id";
				public static final String ACCOUNT_NAME = "account_name";	
			}
		}

		public static class PlaceHolder {
			public static final String NAME = "place_holder";
			public static class Fields {
				public static final String ID = "id";
				public static final String OFFSET = "offset";
				public static final String LAST_RECORD = "last_record";
				public static final String IS_LATEST = "is_latest";
			}
		}

		public static class ProjectAccount {
			public static final String NAME = "project_account";
			public static class Fields {
				public static final String ID = "id";
				public static final String PROJECT_ID = "project_id";
				public static final String ACCOUNT_ID = "account_id";
				public static final String JOB_COUNT = "job_count";
			}
		}
		
		public static class Projects {
			public static final String NAME = "projects";
			public static class Fields {
				public static final String ID = "id";
				public static final String PROJECT_NAME = "project_name";
			}
		}				
	}
	
	//  Class denoting all grid engine types.
	public static class GridEngineTypes {
		
		//  Class for Sun Grid Engine(prior to acquisition by Oracle).
		public static class SGE {
			
			public static final String NAME = "Sun Grid Engine";
			
			public static class Properties {
				public static final String GRID_TYPE = "gridType";
				public static final String GRID_NAME = "gridName";
				public static final String GRID_BINARY_PATH = "gridBinaryPath";
				public static final String GRID_ARCH_TYPE = "gridArchType";
				public static final String GRID_CELL = "gridCell";
				public static final String GRID_ACCOUNTING_FILE = "gridAccountingFileName";
				public static final String GRID_SCAN_TIME = "gridScanTime";
			}
			
			//  Class for Sun Grid Engine accounting file.
			public static class AccountingFile {
				
				//  Class denoting the information of the various fields in the accounting file.
				public static class Fields {
				
					// The below fields are OS dependent.  They occur in position 14-30.
					// ru_utime
					// ru_stime
					// ru_maxrss
					// ru_ixrss
					// ru_ismrss
					// ru_idrss
					// ru_isrss
					// ru_minflt
					// ru_majflt
					// ru_nswap
					// ru_inblock
					// ru_oublock
					// ru_msgsnd
					// ru_msgrcv
					// ru_nsignals
					// ru_nvcsw
					// ru_nivcsw

					
					public static class QueueName {
						public static final String NAME = "qname";
						public static final int POSITION = 0;
					}
					
					public static class HostName {
						public static final String NAME = "hostname";
						public static final int POSITION = 1;
					}
					
					public static class Group {
						public static final String NAME = "group";
						public static final int POSITION = 2;
					}
					
					public static class Owner {
						public static final String NAME = "owner";
						public static final int POSITION = 3;
					}
					
					public static class JobName {
						public static final String NAME = "job_name";
						public static final int POSITION = 4;
					}
					
					public static class JobNumber {
						public static final String NAME = "job_number";
						public static final int POSITION = 5;
					}
					
					public static class Account {
						public static final String NAME = "account";
						public static final int POSITION = 6;
					}

					public static class Priority {
						public static final String NAME = "priority";
						public static final int POSITION = 7;
					}
					
					public static class SubmissionTime {
						public static final String NAME = "submission_time";
						public static final int POSITION = 8;
					}
					public static class StartTime {
						public static final String NAME = "start_time";
						public static final int POSITION = 9;
					}
					
					public static class EndTime {
						public static final String NAME = "end_time";
						public static final int POSITION = 10;
					}
					
					public static class Failed {
						public static final String NAME = "failed";
						public static final int POSITION = 11;
					}
					
					public static class ExitStatus {
						public static final String NAME = "exit_status";
						public static final int POSITION = 12;
					}
					
					public static class RUWallClock {
						public static final String NAME = "ru_wallclock";
						public static final int POSITION = 13;
					}
					
					public static class Project {
						public static final String NAME = "project";
						public static final int POSITION = 31;
					}
					
					public static class Department {
						public static final String NAME = "department";
						public static final int POSITION = 32;
					}
					
					public static class GrantedPE {
						public static final String NAME = "granted_pe";
						public static final int POSITION = 33;
					}
					
					public static class Slots {
						public static final String NAME = "slots";
						public static final int POSITION = 34;
					}
					
					public static class TaskNumer {
						public static final String NAME = "task_number";
						public static final int POSTISION = 35;
					}
					
					public static class CPU {
						public static final String NAME = "cpu";
						public static final int POSITION = 36;
					}
					
					public static class MEM {
						public static final String NAME = "mem";
						public static final int POSITION = 37;
					}
					
					public static class IO {
						public static final String NAME = "io";
						public static final int POSITION = 38;
					}
					
					public static class Category {
						public static final String NAME = "category";
						public static final int POSITION = 39;
					}
					
					public static class IOW {
						public static final String NAME = "iow";
						public static final int POSITION = 40;
					}
					
					public static class PETaskId {
						public static final String NAME = "pe_taskid";
						public static final int POSITION = 41;
					}
					
					public static class MaxVMem {
						public static final String NAME = "maxvmem";
						public static final int POSITION = 42;
					}

					public static class Grid {
						public static final String NAME = "grid";
						public static final int POSITION = 43;
					}

					public static class GRSubmissionTime {
						public static final String NAME = "gr_submission_time";
						public static final int POSITION = 44;
					}
				}
			}
		}
		
		//  Class for Oracle Grid Engine.
		public static final String OGE = "Oracle Grid Engine";
	}
	
	//  Rewrite queries to ref Table's constants
	public static class Queries {
		//  Insert queries
		public static final String INSERT_ACCOUNTING_ENTRY = "INSERT INTO `accounting` (" + Tables.Accounting.Fields.QUEUE_NAME + ", " + Tables.Accounting.Fields.HOST_NAME + ", `" + Tables.Accounting.Fields.GROUP + "`, " + Tables.Accounting.Fields.OWNER + ", " + Tables.Accounting.Fields.JOB_NAME + ", " + Tables.Accounting.Fields.JOB_NUMBER + ", " + Tables.Accounting.Fields.ACCOUNT + ", " + Tables.Accounting.Fields.SUBMISSION_TIME + ", " + Tables.Accounting.Fields.START_TIME + ", " + Tables.Accounting.Fields.END_TIME + ", " + Tables.Accounting.Fields.PROJECT + ", " + Tables.Accounting.Fields.GRANTED_PE + ") VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
		public static final String INSERT_NEW_PROJECT = "INSERT INTO `projects` (project_name) VALUES ( ? )";
		public static final String INSERT_NEW_ACCOUNT = "INSERT INTO `accounts` (account_name) VALUES ( ? )";
		public static final String INSERT_PROJECT_ACCOUNT_COUNT_BY_IDS = "INSERT INTO `project_account` (project_id, account_id, " + Tables.ProjectAccount.Fields.JOB_COUNT + ") VALUES ( ? , ? , ? )";
		public static final String INSERT_PROJECT_ACCOUNT_COUNT_BY_NAMES = "INSERT INTO `project_account` (project_id, account_id, " + Tables.ProjectAccount.Fields.JOB_COUNT + ") VALUES ((SELECT id FROM `projects` WHERE project_name = ?), (SELECT id FROM `accounts` WHERE account_name = ?), ?)";
		public static final String INSERT_PLACE_HOLDER_NEW_LATEST_RECORD = "INSERT INTO " + Tables.PlaceHolder.NAME + " (" + Tables.PlaceHolder.Fields.OFFSET + ", " + Tables.PlaceHolder.Fields.LAST_RECORD + ", " + Tables.PlaceHolder.Fields.IS_LATEST + ") VALUES (?, ?, 1)";
		
		//  Update queries
		public static final String UPDATE_PROJECT_ACCOUNT_COUNT_BY_PROJECT_ID_AND_ACCOUNT_ID = "UPDATE `project_account` SET " + Tables.ProjectAccount.Fields.JOB_COUNT + " = ? WHERE project_id = ? AND account_id = ?";
		public static final String UPDATE_PROJECT_ACCOUNT_COUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME = "UPDATE `project_account` SET " + Tables.ProjectAccount.Fields.JOB_COUNT + " = ? WHERE project_id = (SELECT id FROM `projects` WHERE project_name = ?) AND account_id = (SELECT id FROM `accounts` WHERE account_name = ?)";
		public static final String UPDATE_PLACE_HOLDER_IS_LATEST_SET_FALSE = "UPDATE " + Tables.PlaceHolder.NAME + " SET " + Tables.PlaceHolder.Fields.IS_LATEST + " = 0 WHERE " + Tables.PlaceHolder.Fields.IS_LATEST + " = 1";
		
		//  Select queries
		public static final String SELECT_PROJECT_BY_PROJECT_NAME = "SELECT * FROM `projects` WHERE project_name = ?";
		public static final String SELECT_ACCOUNT_BY_ACCOUNT_NAME = "SELECT * FROM `accounts` WHERE account_name = ?";
		public static final String SELECT_PROJECT_ACCOUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME = "SELECT * FROM `project_account` WHERE project_id = (SELECT id FROM `projects` WHERE project_name = ?) AND account_id = (SELECT id FROM `accounts` WHERE account_name = ? )";
		public static final String SELECT_PROJECT_ACCOUNT_COUNT_BY_PROJECT_NAME_AND_ACCOUNT_NAME = "SELECT " + Tables.ProjectAccount.Fields.JOB_COUNT + " FROM `project_account` WHERE project_id = (SELECT id FROM `projects` WHERE project_name = ?) AND account_id = (SELECT id FROM `accounts` WHERE account_name = ? )";
		public static final String SELECT_PROJECT_ACCOUNT_COUNT_BY_ID = "SELECT " + Tables.ProjectAccount.Fields.JOB_COUNT + " FROM `project_account` WHERE id = ?";
		public static final String SELECT_OFFSET_PLACE_HOLDER = "SELECT " + Tables.PlaceHolder.Fields.OFFSET + " FROM place_holder WHERE " + Tables.PlaceHolder.Fields.IS_LATEST + " = 1";
		public static final String SELECT_LAST_RECORD_PLACE_HOLDER = "SELECT " + Tables.PlaceHolder.Fields.LAST_RECORD + " FROM place_holder WHERE " + Tables.PlaceHolder.Fields.IS_LATEST + " = 1";
		public static final String SELECT_PROJECT_ACCOUNT_PROJECT_NAME_COUNT = "SELECT p." + Tables.Projects.Fields.PROJECT_NAME + " ,pa." + Tables.ProjectAccount.Fields.JOB_COUNT + " FROM " + Tables.Projects.NAME + " p, " + Tables.ProjectAccount.NAME + " pa WHERE pa." + Tables.ProjectAccount.Fields.PROJECT_ID + " = p." + Tables.Projects.Fields.ID; 
		public static final String SELECT_PROJECT_REPORT_BY_TIME = "SELECT " + Tables.Accounting.Fields.PROJECT + ", " + Tables.Accounting.Fields.START_TIME + ", " + Tables.Accounting.Fields.END_TIME + " FROM " + Tables.Accounting.NAME + " WHERE " + Tables.Accounting.Fields.START_TIME + " > ? AND " + Tables.Accounting.Fields.END_TIME + " < ?";
		public static final String SELECT_PROJECT_ACCOUNT_REPORT_BY_TIME = "SELECT " + Tables.Accounting.Fields.PROJECT + ", " + Tables.Accounting.Fields.OWNER + ", " + Tables.Accounting.Fields.START_TIME + ", " + Tables.Accounting.Fields.END_TIME + " FROM " + Tables.Accounting.NAME + " WHERE " + Tables.Accounting.Fields.START_TIME + " > ? AND " + Tables.Accounting.Fields.END_TIME + " < ?";
		public static final String SELECT_ACCOUNTING_BY_TIME = "SELECT * FROM " + Tables.Accounting.NAME + " WHERE " + Tables.Accounting.Fields.START_TIME + " >  ? AND " + Tables.Accounting.Fields.END_TIME + " < ?"; 
	}
	
	//  Reports with an * in the name do not have a Pie chart to go with them.
	public static class Reports {
		
		public static class JobCountByProject {
			
			public static final String NAME = "All Projects";
			
			public static class Fields {
			
				public static final String PROJECT = "Project Name";
				public static final String TIME = "Time(hours)";
				public static final String JOB_COUNT = "Job Count";		
			}
			
			public static List<String> getFields() {
				List<String> fields = new ArrayList<String>();
				fields.add(Reports.JobCountByProject.Fields.PROJECT);
				fields.add(Reports.JobCountByProject.Fields.TIME);
				fields.add(Reports.JobCountByProject.Fields.JOB_COUNT);
				return fields;
			}
		}
		
		public static class AccountsReport{
			public static final String NAME = "All Accounts";
			
			public static class Fields {
				public static final String ACCOUNT = "Account Name";
				public static final String TIME = "Time(hours)";
				public static final String JOB_COUNT = "Job Count";						
			}
			
			public static List<String> getFields() {
				List<String> fields = new ArrayList<String>();
				fields.add(Reports.AccountsReport.Fields.ACCOUNT);
				fields.add(Reports.AccountsReport.Fields.TIME);
				fields.add(Reports.AccountsReport.Fields.JOB_COUNT);
				return fields;
			}
		}
		
		public static class QueuesReport{
			public static final String NAME = "All Queues";
			
			public static class Fields {
				public static final String QUEUE = "Queue Name";
				public static final String TIME = "Time(hours)";
				public static final String JOB_COUNT = "Job Count";						
			}
			
			public static List<String> getFields() {
				List<String> fields = new ArrayList<String>();
				fields.add(Reports.QueuesReport.Fields.QUEUE);
				fields.add(Reports.QueuesReport.Fields.TIME);
				fields.add(Reports.QueuesReport.Fields.JOB_COUNT);
				return fields;
			}
		}

		public static class QueuesByAccountReport{
			public static final String NAME = "Queues With Account Detail*";
			
			public static class Fields {
				public static final String QUEUE = "Queue Name";
				public static final String ACCOUNT = "Account";
				public static final String TIME = "Time(hours)";
				public static final String JOB_COUNT = "Job Count";						
			}
			
			public static List<String> getFields() {
				List<String> fields = new ArrayList<String>();
				fields.add(Reports.QueuesByAccountReport.Fields.QUEUE);
				fields.add(Reports.QueuesByAccountReport.Fields.ACCOUNT);
				fields.add(Reports.QueuesByAccountReport.Fields.TIME);
				fields.add(Reports.QueuesByAccountReport.Fields.JOB_COUNT);
				return fields;
			}
		}
		
		public static class ProjectsByAccountReport {
		
			public static final String NAME = "Project With Account Detail*";
			
			public static class Fields {
			
				public static final String PROJECT = "Project";
				public static final String JOB_COUNT = "Job Count";
				public static final String ACCOUNT = "Account";
				public static final String TIME = "Time(hours)";
			}
			
			public static List<String> getFields() {
				List<String> fields = new ArrayList<String>();
				fields.add(Reports.ProjectsByAccountReport.Fields.PROJECT);
				fields.add(Reports.ProjectsByAccountReport.Fields.ACCOUNT);
				fields.add(Reports.ProjectsByAccountReport.Fields.TIME);
				fields.add(Reports.ProjectsByAccountReport.Fields.JOB_COUNT);
				return fields;
			}
		}		
		
		public static List<String> getReports() {
			List<String> reports = new ArrayList<String>();
			reports.add(Reports.AccountsReport.NAME);
			reports.add(Reports.JobCountByProject.NAME);
			reports.add(Reports.ProjectsByAccountReport.NAME);
			//  Currently has a bug.  Commented out till fixed.
			reports.add(Reports.QueuesByAccountReport.NAME);
			reports.add(Reports.QueuesReport.NAME);
			return reports;
		}
	}
	
	public static class APIReportTypes {
		public static final String AllAccounts = "AllAccounts";
		public static final String AllProjects = "AllProjects";
		public static final String AllProjectsAccountDetails = "AllProjectsAccountDetails";
		public static final String AllQueues = "AllQueues";
		public static final String AllQueuesAccountDetails = "AllQueuesAccountDetails";
	}
	
}
