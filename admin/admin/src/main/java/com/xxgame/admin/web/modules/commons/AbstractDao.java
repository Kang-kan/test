package com.xxgame.admin.web.modules.commons;

import com.xxgame.admin.web.model.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * AbstractDao
 */
public abstract class AbstractDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 分页查找
     * @param sql
     * @param countSql
     * @param pageNo
     * @param pageSize
     * @param args
     * @return
     */
    public PageDto<Map<String, Object>> queryPage(String sql, String countSql, int pageNo, int pageSize, Object... args) {
        PageDto<Map<String, Object>> pageDto = new PageDto<>();

        Integer totalSize = jdbcTemplate.queryForObject(countSql, Integer.class, args);
        if (totalSize == null || totalSize <= 0) {
            pageDto.setPageNo(pageNo);
            pageDto.setPageSize(pageSize);
            pageDto.setTotalPage(0);
            pageDto.setTotalCount(0);
            return pageDto;
        }

        int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
        int offset = pageNo * pageSize;
        int limit =  pageSize;

        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);
        List<Map<String, Object>> rowMaps = jdbcTemplate.queryForList(sb.toString(), args);

        pageDto.setPageNo(pageNo);
        pageDto.setPageSize(pageSize);
        pageDto.setTotalPage(totalPage);
        pageDto.setTotalCount(totalSize);
        pageDto.setContents(rowMaps);

        return pageDto;
    }

    /**
     * 分页查找
     * @param sql
     * @param countSql
     * @param pageNo
     * @param pageSize
     * @param paramMap
     * @return
     */
    public PageDto<Map<String, Object>> queryPage(String sql, String countSql, int pageNo, int pageSize, Map<String, Object> paramMap) {
        PageDto<Map<String, Object>> pageDto = new PageDto<>();

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);

        Integer totalSize = jdbc.queryForObject(countSql, paramMap, Integer.class);
        if (totalSize == null || totalSize <= 0) {
            pageDto.setPageNo(pageNo);
            pageDto.setPageSize(pageSize);
            pageDto.setTotalPage(0);
            pageDto.setTotalCount(0);
            return pageDto;
        }

        int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
        int offset = pageNo * pageSize;
        int limit =  pageSize;

        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);
        List<Map<String, Object>> rowMaps = jdbc.queryForList(sb.toString(), paramMap);

        pageDto.setPageNo(pageNo);
        pageDto.setPageSize(pageSize);
        pageDto.setTotalPage(totalPage);
        pageDto.setTotalCount(totalSize);
        pageDto.setContents(rowMaps);

        return pageDto;
    }

}
