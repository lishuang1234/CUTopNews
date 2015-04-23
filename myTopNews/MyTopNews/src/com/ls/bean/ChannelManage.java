package com.ls.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.database.SQLException;
import android.util.Log;

import com.ls.dao.ChannelDao;
import com.ls.db.ChannelSQLHelper;

public class ChannelManage {
	public static ChannelManage channelManage;
	/**
	 * 默认的用户�?择频道列�? *
	 */
	public static List<ChannelItem> defaultUserChannels;
	// /**
	// * 默认的其他频道列�? * */
	// public static List<ChannelItem> defaultOtherChannels;
	private ChannelDao channelDao;
	/** 判断数据库中是否存在用户数据 */
	private boolean userExist = false;
	static {
		defaultUserChannels = new ArrayList<ChannelItem>();
		defaultUserChannels.add(new ChannelItem(1, "头条新闻", 0, 1));
		defaultUserChannels.add(new ChannelItem(3, "学院经纬", 1, 1));
		defaultUserChannels.add(new ChannelItem(4, "菁菁校园", 2, 1));
		defaultUserChannels.add(new ChannelItem(6, "媒体重邮", 3, 1));
		defaultUserChannels.add(new ChannelItem(7, "新闻特写", 4, 1));
		// defaultUserChannels.add(new ChannelItem(7, "教务新闻", 7, 1));
	}

	private ChannelManage(ChannelSQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		// NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
		return;
	}

	/**
	 * 初始化频道管理类
	 * 
	 * @param paramDBHelper
	 * @throws SQLException
	 */
	public static ChannelManage getManage(ChannelSQLHelper dbHelper)
			throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * 清除�?��的频�?
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}

	/**
	 * 获取其他的频�? * @return 数据库存在用户配�?? 数据库内的用户�?择频�?: 默认用户选择频道 ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = channelDao.listCache(ChannelSQLHelper.SELECTED + "= ?",
				new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer
						.valueOf(maplist.get(i).get(ChannelSQLHelper.ID)));
				navigate.setName(maplist.get(i).get(ChannelSQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(
						ChannelSQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(
						ChannelSQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}

	// /**
	// * 获取其他的频�? * @return 数据库存在用户配�?? 数据库内的其它频�?: 默认其它频道 ;
	// */
	// public List<ChannelItem> getOtherChannel() {
	// Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new
	// String[] { "0" });
	// List<ChannelItem> list = new ArrayList<ChannelItem>();
	// if (cacheList != null && !((List) cacheList).isEmpty()){
	// List<Map<String, String>> maplist = (List) cacheList;
	// int count = maplist.size();
	// for (int i = 0; i < count; i++) {
	// ChannelItem navigate= new ChannelItem();
	// navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
	// navigate.setName(maplist.get(i).get(SQLHelper.NAME));
	// navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
	// navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
	// list.add(navigate);
	// }
	// return list;
	// }
	// if(userExist){
	// return list;
	// }
	// cacheList = defaultOtherChannels;
	// return (List<ChannelItem>) cacheList;
	// }
	//
	/**
	 * 保存用户频道到数据库
	 * 
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			channelDao.addCache(channelItem);
		}
	}

	//
	// /**
	// * 保存其他频道到数据库
	// * @param otherList
	// */
	// public void saveOtherChannel(List<ChannelItem> otherList) {
	// for (int i = 0; i < otherList.size(); i++) {
	// ChannelItem channelItem = (ChannelItem) otherList.get(i);
	// channelItem.setOrderId(i);
	// channelItem.setSelected(Integer.valueOf(0));
	// channelDao.addCache(channelItem);
	// }
	// }

	/**
	 * 初始化数据库内的频道数据
	 */
	private void initDefaultChannel() {
		Log.d("deleteAll", "deleteAll");
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		// saveOtherChannel(defaultOtherChannels);
	}
}
