package net.uchoice.travelgift.vote.vo;

import java.util.List;

import com.github.pagehelper.Page;

public class PageVo<T> {

	private Integer pageNo;

	private Integer pageSize;

	private Integer totalPage;

	private Long totalCount;
	
	private List<T> data;
	
	public PageVo() {
	}
	
	public PageVo(Page<T> page) {
		this.pageNo = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.totalCount = page.getTotal();
		this.totalPage = page.getPages();
	}
	

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}


}
