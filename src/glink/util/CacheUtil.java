package glink.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CacheUtil {
	
	private static CacheUtil instance = null;
	private Properties properties;
	private List<Properties> propertiesList = new ArrayList<Properties>();
	
	
	private CacheUtil() {
		refreshPropertiesList();
	}	

	
	public static synchronized CacheUtil getUniqueInstance() {
		if (instance == null) {
			instance = new CacheUtil();
		}		
		return instance;
	}
	
	public void refreshCache() {
		instance = null;
		instance = new CacheUtil();
		
	}
	
	private void refreshPropertiesList() {
		
		//  Eventually find a way to scan in all files in the classes directory.
		Properties properties = new Properties();
		String PROPERTIES_FILE = "localgridEngine.prop";
		try {
			properties.load(CacheUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
			this.properties = properties;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public List<Properties> getPropertiesList() {
		if (propertiesList == null) {
			refreshPropertiesList();
		}
		return propertiesList;
	}

	public Properties getProperties() {
		return properties;
	}


	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
