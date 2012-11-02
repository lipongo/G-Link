package glink.managedbeans;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import glink.dao.GlinkDAO;


@ManagedBean (name="index")
@SessionScoped
public class IndexBean extends ParentBean {

	private boolean isConnected = false;
	
	public IndexBean() throws SQLException {
		GlinkDAO dao = new GlinkDAO();
		setIsConnected(dao.getConnectionState());
	}

	public boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
}
