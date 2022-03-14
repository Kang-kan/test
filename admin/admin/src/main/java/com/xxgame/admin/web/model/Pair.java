/**
 * 
 */
package com.xxgame.admin.web.model;

/**
 * 值对
 */
public class Pair {

	/**
	 * 键名
	 */
	private String key;
	
	/**
	 * 键对应的值
	 */
	private Object value;

	/**
	 * 工厂方法
	 * @param key
	 * @param value
	 * @return
	 */
	public static Pair valueOf(String key, Object value) {
		Pair pair = new Pair();
		pair.setKey(key);
		pair.setValue(value);
		return pair;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString(){
		return new StringBuilder().append("key : ").append(key).append(", value : ").append(value).toString();
	}
	
}
