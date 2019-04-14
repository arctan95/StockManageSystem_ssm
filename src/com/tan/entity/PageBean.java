package com.tan.entity;

public class PageBean {

	private int page;
	private int size;
	private int start;
	
	public PageBean(int page, int rows) {
		super();
		this.page = page;
		this.size = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStart() {
		return (page-1)*size;
	}
	
	
}
