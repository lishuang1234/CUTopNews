package com.ls.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.text.TextUtils;

public class NewsDetailService {
	public static String getNewsDetails(String url, String news_title,
			String news_date) {
		Document document = null;
		String data = "<body>" + "<center><h2 style='font-size:16px;' >"
				+ news_title + "<h2></center>";
		data = data + "<p align='left' style='margin-left:10px'>"
				+ "<span style='font-size:10px;'>" + news_date + "</span>"
				+ "</p>";
		data = data + "<hr size='1'/>";
		try {
			document = Jsoup.connect(url).timeout(9000).get();
			Element element = null;
			if (TextUtils.isEmpty(url)) {
				data = "";
			} else {
				element = document.getElementById("news_content");
			}
			if (element != null) {
				System.out.println("element:" + element.toString());
				data = data + element.toString();
			}
			data = data + "</body>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/** 获取新闻标题列表 */
	public static List<String> getNewsTitleListFromWeb(int id, int flag) {

		// TODO Auto-generated method stub
		String url = "http://xwzx.cqupt.edu.cn/xwzx/news_type.php?id=" + id;
		Document document = null;
		List<String> news = new ArrayList<String>();
		try {
			document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByAttributeValue("width",
					"540");
			for (Element element : elements) {
				// element.select("a[title]");
				// System.out.println("title:" + element.select("a[title]"));】、
				Elements elements2 = element.getElementsByTag("a");
				if (flag == 1) {// 标题
					System.out.println("title:" + elements2.text());
					news.add(elements2.text());
				} else if (flag == 0) {
					System.out.println("url:" + elements2.attr("abs:href"));// 获取绝对地址
					news.add(elements2.attr("abs:href"));
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news;
	}

	/** 获取新闻时间点击次数 */
	public static void test2(String url) {

		// TODO Auto-generated method stub
		// String url = "http://xwzx.cqupt.edu.cn/xwzx/news.php?id=24830";
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByAttributeValue("style",
					"line-height:30px;");
			for (Element element : elements) {
				System.out.println("title:" + element.text());

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 获取新闻当中多张的图片供列表展示缩略图 */
	public static List<String> getNewsImages(String url) {

		// TODO Auto-generated method stub
		// String url = "http://xwzx.cqupt.edu.cn/xwzx/news.php?id=24712";
		Document document = null;
		List<String> newImagesList = new ArrayList<String>();
		try {
			document = Jsoup.connect(url).timeout(9000).get();
			Element element = document.getElementById("news_content");
			Elements e = element.select("IMG");
			// for (Element element2 : e) {
			// String e2 = element2.attr("abs:src");
			// System.out.println("IMG:" + e2);
			// newImagesList.add(element2.attr("abs:src"));
			// }
			String e2 = e.attr("abs:src");
			System.out.println("IMG:" + e2);
			newImagesList.add(e2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newImagesList;
	}
}
