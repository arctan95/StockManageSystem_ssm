package com.tan.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Import {

	private Integer id;
	private String goodsId;
	private String goodsName;
	private String impoPrice;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//日期格式化注解,将前台字符串类型转化为日期类型
	private Date impoDate;
	private String impoNum;
	private String impoDesc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getImpoPrice() {
		return impoPrice;
	}
	public void setImpoPrice(String impoPrice) {
		this.impoPrice = impoPrice;
	}
	public Date getImpoDate() {
		return impoDate;
	}
	public void setImpoDate(Date impoDate) {
		this.impoDate = impoDate;
	}
	public String getImpoNum() {
		return impoNum;
	}
	public void setImpoNum(String impoNum) {
		this.impoNum = impoNum;
	}
	public String getImpoDesc() {
		return impoDesc;
	}
	public void setImpoDesc(String impoDesc) {
		this.impoDesc = impoDesc;
	}
	
	
}
