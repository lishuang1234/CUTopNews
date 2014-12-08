package com.ls.tool;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;

import com.ls.bean.JsonNewsEntity;
import com.ls.bean.NewsEntity;
import com.ls.bean.WeatherEntity;
import com.ls.db.ChannelNewsDBUtil;

public class Constants {
	public static final int mark_recom = 0;
	public static final int mark_hot = 1;
	public static final int mark_first = 2;
	public static final int mark_exclusive = 3;
	public static final int mark_favor = 4;
	public static final int DB_CHANNEL = 0;
	public static final int DB_CHANNEL_NEWS = 1;
	public static final int UP_DATE_NEWS = 1;
	public static final int GET_NEWS = 0;
	public static final String SETTING = "setting";
	public static final String SETTING_FONT_SIZE = "textFontSize";
	public static final String SETTING_LOAD_IMAGE = "loadImage";
	public static final String SETTING_NOTIFY = "notify";
	public static final String SETTING_SHARE_WHEN_FAVOR = "shareWhenFavor";
	public static final String SETTING_CLEAR = "clear";

	private List<String> newsImagesList;
	private Context context;
	private ChannelNewsDBUtil dbUtil;
	public static final String[] TABLE_NAME = { "topnews", "academynews",
			"classnews", "medianews", "specialnews" };
	public static final int[] NEWS_URL_ID = { 1, 3, 4, 6, 7 };
	public static final String[] REFRESH_NEWS = { "topnewsRe", "academynewsRe",
			"classnewsRe", "medianewsRe", "specialnewsRe", "newsReAll" };
	private static final String URL_PATH = "http://113.250.159.145/cquptnews/servlet/NewsServlet?action_flag=";

	public Constants(Context context) {
		this.context = context;
		dbUtil = ChannelNewsDBUtil.getInstance(context);

	}

	/** 从网络中获取新聞 */
	private List<JsonNewsEntity> getNewsEntityList(String table) {

		String jsonString = HttpUtils.getJsonContent(URL_PATH + table);
		System.out.println("jsonString" + jsonString);
		// /确实为空判断
		List<JsonNewsEntity> jsonNewsEntities = JsonTool.getNewsEntityList(
				jsonString, JsonNewsEntity.class);
		if (jsonNewsEntities != null & jsonNewsEntities.size() != 0) {
			for (int i = 0; i < jsonNewsEntities.size(); i++) {// 数据库添加
				System.out.println("添加数据库" + jsonNewsEntities.get(i));
				dbUtil.insertData(table, jsonNewsEntities.get(i));
			}
		}
		// new AddDataToDB(jsonNewsEntities, table).start();
		return jsonNewsEntities;
	}

	/** 网络获取最新新闻 */
	/** 传入当前数据库中最新新闻的URL */
	private List<JsonNewsEntity> upDateNewsEntityList(String table, String url) {
		String jsonString = HttpUtils.getJsonContent(URL_PATH + table + "&url="
				+ url);
		System.out.println(jsonString);
		List<JsonNewsEntity> jsonNewsEntities = JsonTool.getNewsEntityList(
				jsonString, JsonNewsEntity.class);
		if (jsonNewsEntities != null & jsonNewsEntities.size() != 0) {
			for (int i = 0; i < jsonNewsEntities.size(); i++) {// 数据库添加
				System.out.println("添加数据库" + jsonNewsEntities.get(i));
				dbUtil.insertData(table, jsonNewsEntities.get(i));
			}
		}
		// new AddDataToDB(jsonNewsEntities, table).start();
		return jsonNewsEntities;
	}

	/**
	 * 获取新闻列表
	 */
	public ArrayList<NewsEntity> getNewsList(String table, int flag) {
		List<JsonNewsEntity> jsonNewsEntities = new ArrayList<JsonNewsEntity>();
		if (flag == GET_NEWS) {// 获得数据从数据库中获得、如果数据库为空，则从网上下载

			jsonNewsEntities = dbUtil.selectData(table, null, null, null, null,
					null, "sourceUrl desc");// 数据库获取
			if (jsonNewsEntities.size() == 0) {// 网络中获取
				System.out.println("数据库为空！");
				jsonNewsEntities = getNewsEntityList(table);
			}
		} else if (flag == UP_DATE_NEWS) {// 请求网络下载最新新闻
			// 获取数据库中最新新闻的URL
			jsonNewsEntities = dbUtil.selectData(table, null, null, null, null,
					null, "sourceUrl desc");
			if (jsonNewsEntities != null & jsonNewsEntities.size() != 0)
				jsonNewsEntities = upDateNewsEntityList(table, jsonNewsEntities
						.get(0).getSourceUrl());// 传递最新URL
		} else {
			return null;
		}
		dbUtil.close();
		ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();
		for (int i = 0; i < jsonNewsEntities.size(); i++) {
			newsImagesList = new ArrayList<String>();
			NewsEntity newsEntity = new NewsEntity();
			newsEntity.setID(i);
			newsEntity.setNewsId(i);
			newsEntity.setCllectStatue(false);
			newsEntity.setCommentNum(jsonNewsEntities.get(i).getClicks());// //需要修改
			newsEntity.setInterestedStatue(true);
			newsEntity.setLikeStatus(true);
			newsEntity.setReadStatus(false);
			newsEntity.setNewsCategory("推荐");
			newsEntity.setNewsCategoryId(1);
			newsEntity.setSource_url(jsonNewsEntities.get(i).getSourceUrl());// 设置新闻网址
			newsEntity.setTitle(jsonNewsEntities.get(i).getTitle());// 设置新闻标题
			System.out.println("picThere:"
					+ jsonNewsEntities.get(i).getPicThereUrl());
			if (jsonNewsEntities.get(i).getPicOneUrl() != null
					&& !jsonNewsEntities.get(i).getPicOneUrl().equals("null")) {
				System.out.println("title :"
						+ jsonNewsEntities.get(i).getTitle());
				newsEntity.setPicOne(jsonNewsEntities.get(i).getPicOneUrl());
				newsImagesList.add(jsonNewsEntities.get(i).getPicOneUrl());
			}
			if (jsonNewsEntities.get(i).getPicTwoUrl() != null
					&& !jsonNewsEntities.get(i).getPicTwoUrl().equals("null")) {
				newsEntity.setPicTwo(jsonNewsEntities.get(i).getPicTwoUrl());
				newsImagesList.add(jsonNewsEntities.get(i).getPicTwoUrl());
			}
			if (jsonNewsEntities.get(i).getPicThereUrl() != null
					&& !jsonNewsEntities.get(i).getPicThereUrl().equals("null")) {// //空指针错误
				newsEntity.setPicThr(jsonNewsEntities.get(i).getPicThereUrl());
				newsImagesList.add(jsonNewsEntities.get(i).getPicThereUrl());
				// System.out.println("picThere:"
				// + jsonNewsEntities.get(i).getPicThereUrl());
			}
			System.out.println("imagessize:" + newsImagesList.size());
			newsEntity.setPicList(newsImagesList);
			newsEntity.setPushTime(jsonNewsEntities.get(i).getPublishTime());
			newsEntity.setSource("重邮新闻网");
			newsEntity.setSummary("");

			if (i == 0) {
				newsEntity.setMark(2);// /设置左上角标记,最新
			} else if (i > 3
					& Integer.valueOf(jsonNewsEntities.get(i).getClicks()) > 2000) {// 点击数大于1000热门
				newsEntity.setMark(1);// /设置左上角标记熱門
			} else if (i == 1) {
				newsEntity.setMark(0);// /设置左上角标记,最新
			} else if (i == 2) {
				newsEntity.setMark(3);// /设置左上角标记,最新
			} else {
				if (jsonNewsEntities.get(i).getIsFavor() == 1)// 判断是否收藏
					newsEntity.setMark(4);
				else
					newsEntity.setMark(22);
			}
			newsEntity.setIsLarge(false);
			newsList.add(newsEntity);
		}

		return newsList;
	}

	/** 从数据库查询收藏的新闻 */
	public List<NewsEntity> getFavorNews() {
		List<NewsEntity> newsEntities = new ArrayList<NewsEntity>();
		for (int i = 0; i < TABLE_NAME.length; i++) {

			List<JsonNewsEntity> jsonNewsEntities = dbUtil.selectData(
					TABLE_NAME[i], null, "isFavor=?", new String[] { "1" },
					null, null, "sourceUrl desc");// 数据库获取
			if (jsonNewsEntities.size() > 0) {
				for (int j = 0; j < jsonNewsEntities.size(); j++) {
					System.out.println("收藏的新闻是：：" + jsonNewsEntities.get(j)
							+ "  " + j);
					JsonNewsEntity jsonNewsEntity = jsonNewsEntities.get(j);
					NewsEntity newsEntity = new NewsEntity();
					newsEntity.setTitle(jsonNewsEntity.getTitle());
					newsEntity.setPushTime(jsonNewsEntity.getPublishTime());
					newsEntity.setSource_url(jsonNewsEntity.getSourceUrl());
					newsEntity.setCommentNum(jsonNewsEntity.getClicks());
					newsEntity.setPicOne(jsonNewsEntity.getPicOneUrl());
					newsEntity.setPicTwo(jsonNewsEntity.getPicTwoUrl());
					newsEntity.setPicThr(jsonNewsEntity.getPicThereUrl());
					newsEntity.setTable(TABLE_NAME[i]);// 设置表名
					newsEntities.add(newsEntity);
				}
			}
		}
		System.out.println("收藏查询完毕");
		return newsEntities;
	}

	//
	// class AddDataToDB extends Thread {
	// private List<JsonNewsEntity> jsonNewsEntities;
	// private String table;
	//
	// public AddDataToDB(List<JsonNewsEntity> jsonNewsEntities, String table) {
	// this.jsonNewsEntities = jsonNewsEntities;
	// this.table = table;
	// }
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// for (int i = 0; i < jsonNewsEntities.size(); i++) {// 数据库添加
	// System.out.println("添加数据库" + jsonNewsEntities.get(i));
	// dbUtil.insertData(table, jsonNewsEntities.get(i));
	// }
	// super.run();
	// }
	//
	// }
	/** 获取天气信息javabean */
	public static WeatherEntity getWeatherEntity(String city) {
		String weatherJson = HttpUtils.getJsonContent(URL_PATH + "weatherRe"
				+ "&city=" + city);
		System.out.println("weatherJson:" + weatherJson);
		WeatherEntity wEntity = JsonTool.getPerson(weatherJson,
				WeatherEntity.class);
		return wEntity;
	}

}
