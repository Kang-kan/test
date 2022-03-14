package com.xxgame.admin.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 分页对象
 * @author gil
 *
 * @param <T>
 */
@ApiModel(value = "分页结果Dto")
public class PageDto<T> {
	
	/**
	 * 分页内容
	 */
	@ApiModelProperty(value = "分页内容")
	private List<T> contents;

	/**
	 * 每页数量
	 */
	@ApiModelProperty(value = "每页数量")
	private int pageSize;

	/**
	 * 页码
	 */
	@ApiModelProperty(value = "页码")
	private int pageNo;

	/**
	 * 总页数
	 */
	@ApiModelProperty(value = "总页数")
	private int totalPage;

	/**
	 * 总条目数
	 */
	@ApiModelProperty(value = "总条目数")
	private long totalCount;

	/**
	 * 空列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static PageDto empty(int pageNo, int pageSize) {
		PageDto dto = new PageDto();
		dto.setPageNo(pageNo);
		dto.setPageSize(pageSize);

		return dto;
	}

	public List<T> getContents() {
		return contents;
	}

	public void setContents(List<T> contents) {
		this.contents = contents;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
