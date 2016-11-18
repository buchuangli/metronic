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

import java.io.*;
import javax.servlet.http.*;

/**
 * 
 * Can be used as a superclass of a <i>Bean</i> used from within a JSP page.
 * 
 * @author Chen Li
 * @since 1.1
 * 
 */
public class BaseBean {

	/**
	 * 
	 * The <code>javax.servlet.http.HttpServletRequest</code> object that
	 * contains the request the client made to the <code>BaseBean</code>.
	 * 
	 */
	protected HttpServletRequest request;

	/**
	 * 
	 * The <code>javax.servlet.http.HttpSession</code> object that represents
	 * the session associated with the <code>BaseBean</code> <a
	 * href="#request">request</a>.
	 * 
	 */
	protected HttpSession session;

	/**
	 * 
	 * The <code>javax.servlet.http.HttpServletResponse</code> object that
	 * contains the response the <code>BaseBean</code> will send to the client.
	 * 
	 */
	protected HttpServletResponse response;

	/**
	 * 
	 * The <code>java.io.PrintWriter</code> where the <code>BaseBean</code>
	 * output is printed to.
	 * 
	 */
	protected PrintWriter writer;

	/**
	 * 
	 * Performs the JSP <i>Bean</i> task using the specified HTTP request, and
	 * prints the output to the specified character stream writer. <br/>
	 * <br/>
	 * Delegates to the <a href="#processRequest()">processRequest()</a>
	 * <code>BaseBean</code> subclass implementation.
	 * 
	 * @param request
	 *            The <code>javax.servlet.http.HttpServletRequest</code> object
	 *            that contains the request the client made to the
	 *            <code>BaseBean</code>.
	 * @param response
	 *            The <code>javax.servlet.http.HttpServletResponse</code> object
	 *            that contains the response the <code>BaseBean</code> will send
	 *            to the client.
	 * @param out
	 *            The <code>java.io.Writer</code> object used to print the
	 *            <code>BaseBean</code> response.
	 * @throws NullPointerException
	 *             If <code>request</code>, or <code>response</code> or
	 *             <code>out</code> are <code>null</code>.
	 * 
	 */
	public final void processRequest(HttpServletRequest request,
			HttpServletResponse response, Writer out) {

		setRequest(request);

		setResponse(response);

		setOut(out);

		try {
			processRequest();
		} catch (Throwable e) {
			handleThrowable(e);
		}

		writer.flush();
	}

	/**
	 * 
	 * Sets the <code>javax.servlet.http.HttpServletRequest</code> object that
	 * contains the request the client made to the <code>BaseBean</code>.
	 * 
	 * @throws NullPointerException
	 *             If <code>request</code> is <code>null</code>.
	 * 
	 */
	public final void setRequest(HttpServletRequest request) {

		if (request == null) {
			throw new NullPointerException();
		}

		this.request = request;

		this.session = request.getSession();
	}

	/**
	 * 
	 * Sets the <code>javax.servlet.http.HttpServletResponse</code> object that
	 * contains the response the <code>BaseBean</code> will send to the client.
	 * 
	 * @throws NullPointerException
	 *             If <code>response</code> is <code>null</code>.
	 * 
	 */
	public final void setResponse(HttpServletResponse response) {

		if (response == null) {
			throw new NullPointerException();
		}

		this.response = response;
	}

	/**
	 * 
	 * Sets the <code>java.io.Writer</code> object used to print the
	 * <code>BaseBean</code> response.
	 * 
	 * @throws NullPointerException
	 *             If <code>out</code> is <code>null</code>.
	 * 
	 */
	public final void setOut(Writer out) {

		if (out == null) {
			throw new NullPointerException();
		}

		writer = new PrintWriter(out);
	}

	/**
	 * 
	 * Invoked by <a href=
	 * "#processRequest(javax.servlet.http.HttpServletRequest,java.io.Writer)"
	 * >processRequest
	 * (javax.servlet.http.HttpServletRequest,java.io.Writer)</a>. <br/>
	 * <br/>
	 * <code>BaseBean</code> subclasses must override this method to provide
	 * their own implementation. <br/>
	 * <br/>
	 * The <code>BaseBean</code> default implementation does nothing.
	 * 
	 */
	protected void processRequest() {
	}

	/**
	 * 
	 * Invoked by <a href=
	 * "#processRequest(javax.servlet.http.HttpServletRequest,java.io.Writer)"
	 * >processRequest(javax.servlet.http.HttpServletRequest,java.io.Writer)</a>
	 * when a <code>java.lang.Throwable</code> is caught. <br/>
	 * <br/>
	 * <code>BaseBean</code> subclasses must override this method to provide
	 * their own implementation. <br/>
	 * <br/>
	 * The <code>BaseBean</code> default implementation just prints the
	 * throwable stack trace.
	 * 
	 * @param t
	 *            The <code>java.lang.Throwable</code> that caused the
	 *            invocation of this method.
	 * 
	 */
	protected void handleThrowable(Throwable t) {

		t.printStackTrace();
	}
}
