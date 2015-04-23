package com.ls.bean;

import java.io.Serializable;

/** 
 * ITEM鐨勫搴斿彲搴忓寲闃熷垪灞炴?
 *  */
public class ChannelItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465237897027410019L;
	/** 
	 * 鏍忕洰瀵瑰簲ID
	 *  */
	public Integer id;
	/** 
	 * 鏍忕洰瀵瑰簲NAME
	 *  */
	public String name;
	/** 
	 * 鏍忕洰鍦ㄦ暣浣撲腑鐨勬帓搴忛『搴? rank
	 *  */
	public Integer orderId;
	/** 
	 * 鏍忕洰鏄惁閫変腑
	 *  */
	public Integer selected;

	public ChannelItem() {
	}

	public ChannelItem(int id, String name, int orderId,int selected) {
		this.id = Integer.valueOf(id);
		this.name = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
	}

	public int getId() {
		return this.id.intValue();
	}

	public String getName() {
		return this.name;
	}

	public int getOrderId() {
		return this.orderId.intValue();
	}

	public Integer getSelected() {
		return this.selected;
	}

	public void setId(int paramInt) {
		this.id = Integer.valueOf(paramInt);
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setOrderId(int paramInt) {
		this.orderId = Integer.valueOf(paramInt);
	}

	public void setSelected(Integer paramInteger) {
		this.selected = paramInteger;
	}

	public String toString() {
		return "ChannelItem [id=" + this.id + ", name=" + this.name
				+ ", selected=" + this.selected + "]";
	}
}