/*
 * Open01 Inc.
 *
 * This is the code of Open01 Log Analysis System
 * Copyright (C) Open01 Inc. 2016
 * 
 * All rights reserved.
 * 
 */
package com.open01.logs.web;

import java.util.logging.Logger;

/**
 * Superclass of all the Open01 log analysis web application part beans.
 *
 * Conditional bean, where the condition is always true by default.
 *
 * Provides a templated output framework, to be actually specialized/implemented
 * by the subclasses, in addition to various printing services.
 *
 * @author Chen Li
 * @version 20160808
 */
public class DefaultPlainBean extends DefaultBean {

	private Logger logger = Logger.getLogger(DefaultPlainBean.class.getName());

	@Override
	protected void conditionIsTrue() {
		logger.info("DefaultPlainBean : conditionIsTrue");

		if (doConditionIsTrue()) {

			writeConditionIsTrueOutput();

		}
	}

	@Override
	protected void conditionIsFalse() {
		logger.info("DefaultPlainBean : conditionIsFalse");

		if (doConditionIsFalse()) {

			writeConditionIsFalseOutput();

			printWarningMessages();

			printPageBottom();
		}
	}

	protected boolean doConditionIsFalse() {
		return true;
	}

}
