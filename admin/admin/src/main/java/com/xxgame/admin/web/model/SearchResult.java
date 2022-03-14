package com.xxgame.admin.web.model;

import java.util.List;
import java.util.Map;

/**
 * 搜索结果
 */
public class SearchResult {

    /**
     * 结果
     */
    private List<Map<String, Object>> rows;

    /**
     * 总条数
     */
    private long totalSize;

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

}
