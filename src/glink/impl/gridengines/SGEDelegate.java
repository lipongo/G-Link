package glink.impl.gridengines;

import java.io.StringReader;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import glink.interfaces.gridengines.GridEngineDelegate;
import glink.interfaces.models.Job;

public class SGEDelegate implements GridEngineDelegate {

	@Override
	public List<Job> getScheduledJobs() {
		
		// Figure out how to parse the XML returned from a qstat call.
//    	DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        Document document = parser.parse(new InputSource(new StringReader(xml)));
//	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//	    String xsd = null;

		return null;
	}

	@Override
	public List<Job> getAllPastJobs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Job> getDateRangeJobs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
