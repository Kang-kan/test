package com.xxgame.admin.web.model;

import java.util.List;
import java.util.Map;

/**
 * 搜索请求参数
 */
public class SearchArgs {

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 从第几条开始取
     */
    private int from;

    /**
     * 取多少条
     */
    private int fetchSize;

    /**
     * must
     */
    private List<QueryArgs> must;

    /**
     * order [{ fieldName : Order }]
     */
    private List<Pair> order;

    /**
     * 搜索关键字
     */
    private String keyWord;

    /**
     * 范围过滤
     */
    private Map<String, List<Object>> range;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public List<QueryArgs> getMust() {
		return must;
	}

	public void setMust(List<QueryArgs> must) {
		this.must = must;
	}

	public List<Pair> getOrder() {
        return order;
    }

    public void setOrder(List<Pair> order) {
        this.order = order;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Map<String, List<Object>> getRange() {
        return range;
    }

    public void setRange(Map<String, List<Object>> range) {
        this.range = range;
    }
}
