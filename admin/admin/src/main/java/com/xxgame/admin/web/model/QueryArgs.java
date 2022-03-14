package com.xxgame.admin.web.model;

/**
 * Query参数
 */
public class QueryArgs {

    /**
     * term，match
     */
    private String type;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 搜索词
     */
    private Object value;

    /**
     * 构造方法
     * @param fieldName
     * @param value
     * @return
     */
    public static QueryArgs valueOf(String fieldName, Object value) {
    	QueryArgs queryArgs = new QueryArgs();
    	queryArgs.setType("term");
    	queryArgs.setFieldName(fieldName);
    	queryArgs.setValue(value);
    	return queryArgs;
    }
    
    /**
     * 构造方法
     * @param type
     * @param fieldName
     * @param value
     * @return
     */
    public static QueryArgs valueOf(String type, String fieldName, Object value) {
    	QueryArgs queryArgs = new QueryArgs();
    	queryArgs.setType(type);
    	queryArgs.setFieldName(fieldName);
    	queryArgs.setValue(value);
    	return queryArgs;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
