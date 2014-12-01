package com.ls.bean;

import java.io.Serializable;

import android.R.integer;

public class JsonNewsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "NewsEntity [suorceUrl=" + sourceUrl + ", title=" + title
				+ ", picOneUrl=" + picOneUrl + ", picTwoUrl=" + picTwoUrl
				+ ", picThereUrl=" + picThereUrl + ", publishTime="
				+ publishTime + ", clickTimes=" + clicks + "isFavor ="
				+ isFavor + "]";
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSuorceUrl(String suorceUrl) {
		this.sourceUrl = suorceUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicOneUrl() {
		return picOneUrl;
	}

	public void setPicOneUrl(String picOneUrl) {
		this.picOneUrl = picOneUrl;
	}

	public String getPicTwoUrl() {
		return picTwoUrl;
	}

	public void setPicTwoUrl(String picTwoUrl) {
		this.picTwoUrl = picTwoUrl;
	}

	public String getPicThereUrl() {
		return picThereUrl;
	}

	public void setPicThereUrl(String picThereUrl) {
		this.picThereUrl = picThereUrl;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getClicks() {
		return clicks;
	}

	public void setClicks(String clicks) {
		this.clicks = clicks;
	}

	public JsonNewsEntity(String title, String suorceUrl, String publishTime,
			String clicks, String picOneUrl, String picTwoUrl,
			String picThereUrl, int isFavor) {

		this.title = title;
		this.sourceUrl = suorceUrl;
		this.publishTime = publishTime;
		this.clicks = clicks;
		this.picOneUrl = picOneUrl;
		this.picTwoUrl = picTwoUrl;
		this.picThereUrl = picThereUrl;
		this.isFavor = isFavor;
	}

	public JsonNewsEntity() {

	}

	private String title;
	private String sourceUrl;
	private String publishTime;
	private String clicks;
	private String picOneUrl;
	private String picTwoUrl;
	private String picThereUrl;
	private int isFavor = 0;

	public int getIsFavor() {
		return isFavor;
	}

	public void setIsFavor(int isFavor) {
		this.isFavor = isFavor;
	}
}
