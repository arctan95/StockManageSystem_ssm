package com.tan.entity;

public class Goods {

	private Integer id;
	private String goodsId;
	private String goodsName;
	private Integer expireTime;
	private String proId;
	private String typeId;
	private String proName;
	private String typeName;
	private String goodsDesc;
	

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


	public Integer getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}


	public String getProId() {
		return proId;
	}


	public void setProId(String proId) {
		this.proId = proId;
	}


	public String getTypeId() {
		return typeId;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public String getProName() {
		return proName;
	}


	public void setProName(String proName) {
		this.proName = proName;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getGoodsDesc() {
		return goodsDesc;
	}


	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	@Override
	public String toString() {
		return goodsName;
	}
	
}
