package glink.common;

//  Using the Constant Data Manager design pattern, store all immutable centrally accessible information. 
public class Constants {

	//  Class denoting all grid engine types.
	public static class GridEngineTypes {
		
		//  Class for Sun Grid Engine(prior to acquisition by Oracle).
		public static class SGE {
			
			public static final String TYPE = "Sun Grid Engine";
			
			//  Class for Sun Grid Engine accounting file.
			public static class AccountingFile {
				
				//  Class denoting the information of the various fields in the accounting file.
				public static class Fields {
					
					public static class JobId {
					
						public static final String NAME = "Job ID";
						public static final int POSITION = 0;
					}
				}
			}
		}
		
		//  Class for Oracle Grid Engine.
		public static final String OGE = "Oracle Grid Engine";
	}
	
}
