package com.tan.entity;

public class Stock {

	private Integer id;
	private String goodsId;
	private String importId;
	private String stockNum;
	private Integer limitNum;
	private String impoPrice;
	private String expoPrice;
	private String stockDesc;
	private Goods goods;
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
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
	public String getImportId() {
		return importId;
	}
	public void setImportId(String importId) {
		this.importId = importId;
	}
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public Integer getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}
	public String getImpoPrice() {
		return impoPrice;
	}
	public void setImpoPrice(String impoPrice) {
		this.impoPrice = impoPrice;
	}
	public String getExpoPrice() {
		return expoPrice;
	}
	public void setExpoPrice(String expoPrice) {
		this.expoPrice = expoPrice;
	}
	public String getStockDesc() {
		return stockDesc;
	}
	public void setStockDesc(String stockDesc) {
		this.stockDesc = stockDesc;
	}
	
	
}
