package com.ls.tool;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ls.bean.JsonNewsEntity;
import com.ls.bean.NewsEntity;
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
	private List<String> newsImagesList;
	private Context context;
	private ChannelNewsDBUtil dbUtil;
	public static final String[] TABLE_NAME = { "topnews", "academynews",
			"classnews", "medianews", "specialnews" };
	public static final int[] NEWS_URL_ID = { 1, 3, 4, 6, 7 };
	public static final String[] REFRESH_NEWS = { "topnewsRe", "academynewsRe",
			"classnewsRe", "medianewsRe", "specialnewsRe", "newsReAll" };
	private static final String URL_PATH = "http://113.251.223.168/cquptnews/servlet/NewsServlet?news_flag=";

	public Constants(Context context) {
		this.context = context;
		dbUtil = ChannelNewsDBUtil.getInstance(context);
	}

	/** 从网络中获取新聞 */
	public List<JsonNewsEntity> getNewsEntityList(String table) {
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
	public List<JsonNewsEntity> upDateNewsEntityList(String table, String url) {
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
					null, null);// 数据库获取
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

	class AddDataToDB extends Thread {
		private List<JsonNewsEntity> jsonNewsEntities;
		private String table;

		public AddDataToDB(List<JsonNewsEntity> jsonNewsEntities, String table) {
			this.jsonNewsEntities = jsonNewsEntities;
			this.table = table;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < jsonNewsEntities.size(); i++) {// 数据库添加
				System.out.println("添加数据库" + jsonNewsEntities.get(i));
				dbUtil.insertData(table, jsonNewsEntities.get(i));
			}
			super.run();
		}

	}
	// /**
	// * 获取新闻列表
	// */
	// public static ArrayList<NewsEntity> getNewsList() {
	//
	// ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();
	// for (int i = 0; i < 10; i++) {
	// NewsEntity newsEntity = new NewsEntity();
	// newsEntity.setID(i);
	// newsEntity.setNewsId(i);
	// newsEntity.setCllectStatue(false);
	// newsEntity.setCommentNum(i + 10);
	// newsEntity.setInterestedStatue(true);
	// newsEntity.setLikeStatus(true);
	// newsEntity.setReadStatus(false);
	// newsEntity.setNewsCategory("推荐");
	// newsEntity.setNewsCategoryId(1);
	// newsEntity
	// .setSource_url("http://news.sina.com.cn/c/2014-05-05/134230063386.shtml");
	// newsEntity.setTitle("可以用谷歌眼睛做的十件事情");
	// List<String> url_listList = new ArrayList<String>();
	// if (i % 2 == 1) {
	// String url1 =
	// "http://infopic.gtimg.com/qq_news/digi/pics/102/102066/102066094_400_640.jpg";
	// String url2 =
	// "http://infopic.gtimg.com/qq_news/digi/pics/102/102066/102066096_400_640.jpg";
	// String url3 =
	// "http://infopic.gtimg.com/qq_news/digi/pics/102/102066/102066099_400_640.jpg";
	// newsEntity.setPicOne(url1);
	// newsEntity.setPicTwo(url2);
	// newsEntity.setPicThr(url3);
	// newsEntity
	// .setSource_url("http://xwzx.cqupt.edu.cn/xwzx/news.php?id=24716");//
	// 重邮新闻网
	// url_listList.add(url1);
	// url_listList.add(url2);
	// url_listList.add(url3);
	// } else {
	// newsEntity.setTitle("AA用车：智能短租");
	// String url =
	// "http://r3.sinaimg.cn/2/2014/0417/a7/6/92478595/580x1000x75x0.jpg";
	// newsEntity.setPicOne(url);
	// url_listList.add(url);
	// }
	// newsEntity.setPicList(url_listList);
	// newsEntity.setPushTime(Long.valueOf(i));
	// newsEntity.setSource("手机腾讯网");
	// newsEntity
	// .setSummary("腾讯数码讯：（编译：Gin）谷歌眼镜可能是目前最酷的可穿戴数码设备，你可以戴着它去任何地方");
	// newsEntity.setMark(i);
	// if (i == 4) {
	// newsEntity.setTitle("部落强势回归");
	// newsEntity.setLocal("推广");
	// newsEntity.setIsLarge(true);
	// String url =
	// "http://imgt2.bdstatic.com/it/u=3269155243,2604389213&fm=21&gp=0.jpg";
	// newsEntity.setSource_url(url);
	// newsEntity.setPicOne(url);
	// url_listList.clear();
	// url_listList.add(url);
	// } else {
	// newsEntity.setIsLarge(false);
	//
	// }
	// if (i == 2) {
	// newsEntity.setComment("评论部分说的很好！");
	// }
	// if (i <= 2) {
	// // newsEntity.setPushTime(Long.valueOf(DateTools.getTime()));//
	// // /可能会抛异常
	// newsEntity.setPushTime(100L);// /可能会抛异常
	// } else if (i > 2 && i < 5) {
	// // newsEntity.setPushTime(Long.valueOf(DateTools.getTime()) -
	// // 86400);
	// newsEntity.setPushTime(10001L);
	// } else {
	// // newsEntity
	// // .setPushTime(Long.valueOf(DateTools.getTime()) - 86400 * 2);
	// newsEntity.setPushTime(1000002L);
	// }
	// newsList.add(newsEntity);
	// }
	//
	// return newsList;
	// }
}
