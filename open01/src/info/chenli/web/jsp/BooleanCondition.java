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
 * "Behaviourify" a boolean condition. 
 *
 * @author Chen Li
 * @since 1.0
 *
 */
public interface BooleanCondition {

    /**
     *
     * Returns whether the <code>Condition</code> is <code>true</code>
     * or <code>false</code>.
     *
     * @return Whether the <code>Condition</code> is <code>true</code>
     * or <code>false</code>.
     *
     */
    public boolean isTrue();
}
