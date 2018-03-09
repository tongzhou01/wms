package com.mz.common.entity;


import java.util.ArrayList;
import java.util.List;

public class Example {
    /**
     * id
     */
    private Integer id;
    /**
     * 查询的表
     */
    private String tableName;

    /**
     * 查询的列
     */
    private String[] columns;

    /**
     * 是否排重
     */
    private boolean distinct;
    /**
     * 排序条件
     */
    private String orderBy;

    /**
     * 更新的对象
     */
    private IEntity entity;

    /**
     * 第几页
     */
    private Integer page;

    /**
     * 每页几行
     */
    private Integer rows;

    /**
     * 分组
     */
    private String groupBy;

    /**
     * 查询条件
     */
    private List<Criterion> criteria;

    /**
     * @param tableName 表名
     * @param columns   数据库字段
     * @param distinct  是否排重（暂不使用）
     * @param orderBy   排序
     * @param entity     实体类
     * @param page      第几页
     * @param rows      每页多少行
     * @param groupBy   分组
     * @return
     */
    public static Example create(String tableName, String[] columns, boolean distinct, String orderBy, IEntity entity,
                                 Integer page, Integer rows, String groupBy) {
        return new Example(tableName, columns, distinct, orderBy, entity, page, rows, groupBy);
    }

    public static Example create(Class<?> clazz) {
        String[] columns = FieldsArray.getFieldsArray(clazz);
        String tableName = Abc.abc(clazz.getSimpleName());
        return new Example(tableName, columns);
    }

    private void addCriteria(Criterion... criteria) {
        if (this.criteria == null) {
            this.criteria = new ArrayList<>();
        }
        if (criteria != null && criteria.length > 0) {
            for (Criterion criterion : criteria) {
                this.criteria.add(criterion);
            }
        }
    }

    public Example(String tableName, String[] columns, boolean distinct, String orderBy, IEntity entity, Integer page,
                   Integer rows, String groupBy) {
        super();
        this.tableName = tableName;
        this.columns = columns;
        this.distinct = distinct;
        this.orderBy = orderBy;
        this.entity = entity;
        this.page = page;
        this.rows = rows;
        this.groupBy = groupBy;
    }

    public Example(String tableName, String[] columns) {
        super();
        this.tableName = tableName;
        this.columns = columns;
    }

    /**
     * 为空
     *
     * @param column
     * @return
     */
    public Example isNull(String column) {
        this.addCriteria(Criterion.isNull(column));
        return this;
    }

    /**
     * 不为空
     *
     * @param column
     * @return
     */
    public Example notNull(String column) {
        this.addCriteria(Criterion.isNull(column));
        return this;
    }

    /**
     * <: 小于
     *
     * @param column
     * @param value
     * @return
     */
    public Example lessThen(String column, Object value) {
        this.addCriteria(Criterion.lessThan(column, value));
        return this;
    }

    /**
     * <+: 小于等于
     *
     * @param column
     * @param value
     * @return
     */
    public Example lessEqual(String column, Object value) {
        this.addCriteria(Criterion.lessEqual(column, value));
        return this;
    }

    /**
     * =: 等于
     *
     * @param column
     * @param value
     * @return
     */
    public Example equal(String column, Object value) {
        this.addCriteria(Criterion.equal(column, value));
        return this;
    }

    /**
     * !=: 不等于
     *
     * @param column
     * @param value
     * @return
     */
    public Example notEqual(String column, Object value) {
        this.addCriteria(Criterion.notEqual(column, value));
        return this;
    }

    /**
     * >=: 大于等于
     *
     * @param column
     * @param value
     * @return
     */
    public Example greatEqual(String column, Object value) {
        this.addCriteria(Criterion.greatEqual(column, value));
        return this;
    }

    /**
     * >: 大于
     *
     * @param column
     * @param value
     * @return
     */
    public Example greatThan(String column, Object value) {
        this.addCriteria(Criterion.greatThan(column, value));
        return this;
    }

    /**
     * like: 匹配
     *
     * @param column
     * @param value
     * @return
     */
    public Example like(String column, Object value) {
        this.addCriteria(Criterion.like(column, value));
        return this;
    }

    /**
     * not like: 不匹配
     *
     * @param column
     * @param value
     * @return
     */
    public Example notLike(String column, Object value) {
        this.addCriteria(Criterion.notLike(column, value));
        return this;
    }

    /**
     * between: 在两值之间
     *
     * @param column
     * @return
     */
    public Example between(String column, Object value1, Object value2) {
        this.addCriteria(Criterion.between(column, value1, value2));
        return this;
    }

    /**
     * not between: 不在两值之间
     *
     * @param column
     * @return
     */
    public Example notBetween(String column, Object value1, Object value2) {
        this.addCriteria(Criterion.notBetween(column, value1, value2));
        return this;
    }

    /**
     * in: 在可选值集合中
     *
     * @param column
     * @param value
     * @return
     */
    public Example in(String column, Object value) {
        this.addCriteria(Criterion.in(column, value));
        return this;
    }

    /**
     * not in: 不在s值集合中
     *
     * @param column
     * @param value
     * @return
     */
    public Example notIn(String column, Object value) {
        this.addCriteria(Criterion.notIn(column, value));
        return this;
    }

    public Example() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public List<Criterion> getCriterion() {
        return criteria;
    }

    public void setCriterion(List<Criterion> criteria) {
        this.criteria = criteria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
