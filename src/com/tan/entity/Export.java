package com.tan.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Export {

	private Integer id;
	private String goodsId;
	private Goods goods;
	private String expoPrice;
	private String stockId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date expoDate;
	private Integer expoNum;
	private String expoDesc;
	
	
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
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getExpoPrice() {
		return expoPrice;
	}
	public void setExpoPrice(String expoPrice) {
		this.expoPrice = expoPrice;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public Date getExpoDate() {
		return expoDate;
	}
	public void setExpoDate(Date expoDate) {
		this.expoDate = expoDate;
	}
	public Integer getExpoNum() {
		return expoNum;
	}
	public void setExpoNum(Integer expoNum) {
		this.expoNum = expoNum;
	}
	public String getExpoDesc() {
		return expoDesc;
	}
	public void setExpoDesc(String expoDesc) {
		this.expoDesc = expoDesc;
	}
	
	
}
