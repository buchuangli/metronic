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

/**
 *
 * Extends the <a href="BaseBean.html">BaseBean</a> to provide a
 * conditional execution of the JSP <i>Bean</i> task.
 *
 * @author Chen Li
 * @since 1.1
 *
 */
public class ConditionBean
    extends BaseBean {

    private BooleanCondition condition_;

    /**
     *
     * Required by the JavaBeans specification.
     * <br/><br/>
     * Not to be invoked directly.
     *
     */
    public ConditionBean() {}

    /**
     *
     *
     *
     */
    protected ConditionBean(BooleanCondition condition) {

	setCondition(condition);
    }

    /**
     *
     *
     *
     */
    protected void setCondition(BooleanCondition condition) {

	if (condition == null) {
	    throw new NullPointerException();
	}
	condition_ = condition;
    }

    /**
     *
     * If the condition associated with the <code>ConditionBean</code>
     * is true, invokes <a href="#conditionIsTrue()">conditionIsTrue()</a>,
     * otherwise invokes <a href="#conditionIsFalse()">conditionIsFalse()</a>.
     *
     */
    protected void processRequest() {

	if (condition_.isTrue()) {
	    conditionIsTrue();
	} else {
	    conditionIsFalse();
	}
    }

    /**
     *
     * Invoked by <a href="#processRequest()">processRequest()</a> when the
     * condition associated with the <code>ConditionBean</code> is true.
     * <br/><br/>
     * <code>ConditionBean</code> subclasses must override this method
     * to provide their own implementation.
     * <br/><br/>
     * The <code>ConditionBean</code> default implementation does
     * nothing.
     *
     */
    protected void conditionIsTrue() {}

    /**
     *
     * Invoked by <a href="#processRequest()">processRequest()</a> when the
     * condition associated with the <code>ConditionBean</code> is false.
     * <br/><br/>
     * <code>ConditionBean</code> subclasses must override this method
     * to provide their own implementation.
     * <br/><br/>
     * The <code>ConditionBean</code> default implementation does
     * nothing.
     *
     */
    protected void conditionIsFalse() {}
}
