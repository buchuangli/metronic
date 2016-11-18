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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Extends the <a href="BaseBean.html">BaseBean</a> to provide an implementation
 * of the JSP <i>Bean</i> task based on the <i>Whole-Part Pattern</i>.
 * 
 * @author Chen Li
 * @since 1.1
 * 
 */
public class WholePartBean extends BaseBean {

	private String key_;
	private HashMap<String, BaseBean> parts_;
	private BaseBean part_;

	/**
	 * 
	 * Required by the JavaBeans specification. Not to be invoked directly.
	 * 
	 */
	public WholePartBean() {
	}

	/**
	 * 
	 * @throws NullPointerException
	 *             If <code>key</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             If <code>key</code> is empty.
	 * 
	 */
	protected WholePartBean(String key) {

		if (key.length() == 0) {
			throw new IllegalArgumentException();
		}
		key_ = key;

		parts_ = new HashMap<String, BaseBean>();
	}

	/**
     *
     *
     *
     */
	protected void addPart(String keyValue, BaseBean part) {

		parts_.put(keyValue, part);
	}

	/**
     *
     *
     *
     */
	protected void addParts(WholePartBean bean) {

		String key = bean.getKey();
		if (!key.equals(key_)) {
			throw new IllegalArgumentException("Specified WholePartBean key '"
					+ key + "' not equal to ");
		}
		Iterator<String> iterator = bean.getKeyValues().iterator();
		while (iterator.hasNext()) {
			String key_value = iterator.next();
			addPart(key_value, bean.getPart(key_value));
		}
	}

	/**
     *
     *
     *
     */
	protected String getKey() {

		return key_;
	}

	/**
     *
     *
     *
     */
	protected Set<String> getKeyValues() {

		return parts_.keySet();
	}

	/**
     *
     *
     *
     */
	protected BaseBean getPart(String keyValue) {

		return (BaseBean) parts_.get(keyValue);
	}

	/**
     *
     *
     *
     */
	protected void processRequest() {

		processRequest(request.getParameter(key_));
	}

	/**
     *
     *
     *
     */
	protected void processRequest(String keyValue) {

		part_ = (BaseBean) parts_.get(keyValue);
		if (part_ == null) {
			throw new PartBeanNotFoundException(
					"No Part Bean was found for key '" + key_
							+ "' with value '" + keyValue + "'.");
		}

		part_.request = request;
		part_.session = session;
		part_.response = response;
		part_.writer = writer;

		part_.processRequest();
	}

	/**
     *
     *
     *
     */
	protected void handleThrowable(Throwable t) {

		if (t instanceof PartBeanNotFoundException) {
			throw (PartBeanNotFoundException) t;
		}

		part_.handleThrowable(t);
	}
}
