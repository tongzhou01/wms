package com.mz.common.entity;

import java.util.Collection;

public class Criterion {
    private static final int NO_VALUE = 0;
    private static final int SINGLE_VALUE = 1;
    private static final int DOUBLE_VALUE = 2;
    private static final int MULTI_VALUE = 3;

    private String condition;
    private String column;
    private String operate;
    private Object value;

    private int type;

    private Criterion(String column, String operate, Object value, int type) {
        this.column = column;
        this.operate = operate;
        this.condition = (this.column + this.operate);
        this.value = value;
        this.type = type;
    }

    private Criterion(String condition) {
        this.condition = condition;
        this.type = NO_VALUE;
    }

    /**
     * SQL片断
     *
     * @param condition
     * @return
     */
    public static Criterion condition(String condition) {
        return new Criterion("(" + condition + ")");
    }

    /**
     * 为空
     *
     * @param column
     * @return
     */
    public static Criterion isNull(String column) {
        return new Criterion(column + " is null");
    }

    /**
     * 不为空
     *
     * @param column
     * @return
     */
    public static Criterion notNull(String column) {
        return new Criterion(column + " is not null");
    }

    /**
     * <: 小于
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion lessThan(String column, Object value) {
        return new Criterion(column, " <", value, SINGLE_VALUE);
    }

    /**
     * <+: 小于等于
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion lessEqual(String column, Object value) {
        return new Criterion(column, " <=", value, SINGLE_VALUE);
    }

    /**
     * =: 等于
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion equal(String column, Object value) {

        return new Criterion(column, " =", value, SINGLE_VALUE);
    }

    /**
     * !=: 不等于
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion notEqual(String column, Object value) {
        return new Criterion(column, " <>", value, SINGLE_VALUE);
    }

    /**
     * >=: 大于等于
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion greatEqual(String column, Object value) {
        return new Criterion(column, " >=", value, SINGLE_VALUE);
    }

    /**
     * >: 大于
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion greatThan(String column, Object value) {
        return new Criterion(column, " >", value, SINGLE_VALUE);
    }

    /**
     * like: 匹配
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion like(String column, Object value) {
        return new Criterion(column, " like", value, SINGLE_VALUE);
    }

    /**
     * not like: 不匹配
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion notLike(String column, Object value) {
        return new Criterion(column, " not like", value, SINGLE_VALUE);
    }

    /**
     * between: 在两值之间
     *
     * @param column
     * @return
     */
    public static Criterion between(String column, Object value1, Object value2) {
        return new Criterion(column, " between", new Object[]{value1, value2}, DOUBLE_VALUE);
    }

    /**
     * not between: 不在两值之间
     *
     * @param column
     * @return
     */
    public static Criterion notBetween(String column, Object value1, Object value2) {
        return new Criterion(column, " not between", new Object[]{value1, value2}, DOUBLE_VALUE);
    }

    /**
     * in: 在可选值集合中
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion in(String column, Object value) {
        Object[] vs = (Object[]) value;
        if (value instanceof Collection) {
            vs = ((Collection<?>) value).toArray();
        }
        return new Criterion(column, " in", vs, MULTI_VALUE);
    }

    /**
     * not in: 不在可选值集合中
     *
     * @param column
     * @param value
     * @return
     */
    public static Criterion notIn(String column, Object value) {
        Object[] vs = null;
        if (value instanceof Collection) {
            vs = ((Collection<?>) value).toArray();
        }
        return new Criterion(column, " not in", vs, MULTI_VALUE);
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
