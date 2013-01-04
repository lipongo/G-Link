package glink.servlets;

import glink.util.CacheUtil;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheServlet extends HttpServlet{

	private static final long serialVersionUID = 4399516135480455346L;

	//  Deal with converting this to work later for loading all the prop files into cache for later use VIA the CacheUtil(Singleton)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CacheUtil.getUniqueInstance().refreshCache();
		

		ServletOutputStream out = resp.getOutputStream();
		String responseText = "Cache refreshed";
		resp.setContentType("text/plain");
		out.print(responseText);
		out.close();
	}

}
