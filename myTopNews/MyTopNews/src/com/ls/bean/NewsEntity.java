package com.ls.bean;

import java.io.Serializable;
import java.util.List;

public class NewsEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer getNewsCategoryId() {
		return newsCategoryId;
	}

	public void setNewsCategoryId(Integer newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}

	public String getNewsCategory() {
		return newsCategory;
	}

	public void setNewsCategory(String newsCategory) {
		this.newsCategory = newsCategory;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getCommentNum() {
		return CommentNum;
	}

	public void setCommentNum(String commentNum) {
		CommentNum = commentNum;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getNewsAbstract() {
		return newsAbstract;
	}

	public void setNewsAbstract(String newsAbstract) {
		this.newsAbstract = newsAbstract;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getPicListString() {
		return picListString;
	}

	public void setPicListString(String picListString) {
		this.picListString = picListString;
	}

	public String getPicOne() {
		return picOne;
	}

	public void setPicOne(String picOne) {
		this.picOne = picOne;
	}

	public String getPicTwo() {
		return picTwo;
	}

	public void setPicTwo(String picTwo) {
		this.picTwo = picTwo;
	}

	public String getPicThr() {
		return picThr;
	}

	public void setPicThr(String picThr) {
		this.picThr = picThr;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public Boolean getIsLarge() {
		return isLarge;
	}

	public void setIsLarge(Boolean isLarge) {
		this.isLarge = isLarge;
	}

	public Boolean getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
	}

	public Boolean getCllectStatue() {
		return cllectStatue;
	}

	public void setCllectStatue(Boolean cllectStatue) {
		this.cllectStatue = cllectStatue;
	}

	public Boolean getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(Boolean likeStatus) {
		this.likeStatus = likeStatus;
	}

	public Boolean getInterestedStatue() {
		return interestedStatue;
	}

	public void setInterestedStatue(Boolean interestedStatue) {
		this.interestedStatue = interestedStatue;
	}

	/** 新闻类别ID */
	private Integer newsCategoryId;
	/** 新闻类别 */
	private String newsCategory;
	/** 新闻状态标记 */
	private Integer mark;
	/** 新闻评论数量 */
	private String CommentNum;
	/** ID */
	private Integer ID;
	/** 新闻ID */
	private Integer newsId;
	/** 标题 */
	private String title;
	/** 新闻源 */
	private String source;
	/** 新闻来源地址URL */
	private String source_url;
	/** 发布时间 */
	private String pushTime;
	/** 总节 */
	private String summary;
	/** 摘要 */
	private String newsAbstract;
	/** 评论 */
	private String comment;
	/** 特殊标签，如推广，广告，可以为空 */
	private String local;
	/** 图列表字符串 */
	private String picListString;
	/** 图片1 */
	private String picOne;
	/** 图片2 */
	private String picTwo;
	/** 图片3 */
	private String picThr;
	/** 图片列表 **/
	private List<String> picList;
	/** 是否为大图 */
	private Boolean isLarge;
	/** 阅读状态，阅读为灰色背景 */
	private Boolean readStatus;
	/** 收藏状态 */
	private Boolean cllectStatue;
	/** 喜欢状态 */
	private Boolean likeStatus;
	/** 感兴趣状态 */
	private Boolean interestedStatue;

}
