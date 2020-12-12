package com.lqw.core.model;

import java.util.List;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class Page<T> {
	private int curr_page;

	private int page_size;

	private int total_page;

	private int total_size;

	private List<T> data_list;

	public static <T> Page<T> valueOf(int totalSize, int pageSize, int currPage, List<T> dataList) {
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Page<T> result = new com.lqw.core.model.Page<T>();
		int totalPage = totalSize / pageSize;
		if (totalSize == 0) {
			currPage = 0;
		} else if (totalSize % pageSize != 0) {
			totalPage++;
		}
		if (totalPage < currPage) {
			currPage = totalPage;
		}
		result.setTotal_size(totalSize);
		result.setPage_size(pageSize);
		result.setCurr_page(currPage);
		result.setTotal_page(totalPage);
		result.setData_list(dataList);
		return result;
	}

	public static <T> Page<T> valueOf(Page page, List<T> dataList) {
		int totalSize = page.getTotal_size();
		int pageSize = page.getPage_size();
		int currPage = page.getCurr_page();
		return valueOf(totalSize, pageSize, currPage, dataList);
	}

	public int getCurr_page() {
		return this.curr_page;
	}

	public void setCurr_page(int currPage) {
		this.curr_page = currPage;
	}

	public int getPage_size() {
		return this.page_size;
	}

	public void setPage_size(int pageSize) {
		this.page_size = pageSize;
	}

	public int getTotal_page() {
		return this.total_page;
	}

	public void setTotal_page(int totalPage) {
		this.total_page = totalPage;
	}

	public int getTotal_size() {
		return this.total_size;
	}

	public void setTotal_size(int totalSize) {
		this.total_size = totalSize;
	}

	public List<T> getData_list() {
		return this.data_list;
	}

	public void setData_list(List<T> dataList) {
		this.data_list = dataList;
	}
}
