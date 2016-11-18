/*
 *
 * This is the code developed by Chen Li
 * Copyright (C) Open01 Inc. 2016
 *
 * Chen Li
 * chen.li AT open01.com
 *
 */
package info.chenli.web.jsp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author Chen Li
 * @since 1.0
 * 
 */
public class Listener implements ServletContextListener {

	private static ServletContext servlet_context;

	/**
	 * 
	 * Returns a string containing the value of the named context-wide
	 * initialization parameter, or <code>null</code> if the parameter does not
	 * exist.
	 * 
	 * @param name
	 *            The name of the context-wide initialization parameter whose
	 *            value is to be returned.
	 * @return The value of the context-wide initialization parameter with name
	 *         <code>name</code>.
	 * 
	 */
	public static String getInitParameter(String name) {

		return servlet_context.getInitParameter(name);
	}

	/**
	 * 
	 * Prints the specified log message.
	 * 
	 */
	public static void log(String msg) {

		servlet_context.log(Listener.class.getName() + "["
				+ Thread.currentThread().getName() + "]: " + msg);
	}

	/**
     *
     *
     *
     */
	public static ServletContext getServletContext() {

		return servlet_context;
	}

	/**
	 * 
	 * Notification that the web application initialization process is starting.
	 * 
	 */
	public void contextInitialized(ServletContextEvent event) {

		synchronized (Listener.class) {
			servlet_context = event.getServletContext();
		}
	}

	/**
	 * 
	 * Notification that the servlet context of the web application is about to
	 * be shut down.
	 * 
	 */
	public void contextDestroyed(ServletContextEvent event) {
	}
}
