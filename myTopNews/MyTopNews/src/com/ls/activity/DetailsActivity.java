package com.ls.activity;

import java.util.ArrayList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ls.bean.JsonNewsEntity;
import com.ls.bean.NewsEntity;
import com.ls.db.ChannelNewsDBUtil;
import com.ls.mytopnews.R;
import com.ls.service.NewsDetailService;
import com.ls.tool.Constants;
import com.ls.tool.DateTools;
import com.ls.tool.ShareSDKHelper;

@SuppressLint("JavascriptInterface")
public class DetailsActivity extends BaseActivity implements OnClickListener {
	private TextView title;
	private ProgressBar progressBar;
	private RelativeLayout customview_Layout;
	private String news_url;
	private String news_title;
	private String news_source;
	private String news_date;
	private NewsEntity newsEntity;
	// private TextView action_comment_coun;
	WebView webView;
	private ImageView action_repost;
	private ImageView action_refresh;
	private ImageView action_favor;
	private ShareSDKHelper sdkHelper;
	private SharedPreferences sPreferences;
	private String table;
	private int position;
	private int activityFlag;

	private boolean isFavor;
	private ChannelNewsDBUtil dbUtil;

	private boolean isSupportZoom;// 是否放大字体

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		setNeedBackGesture(true);
		sdkHelper = new ShareSDKHelper(DetailsActivity.this);
		sPreferences = getSharedPreferences(Constants.SETTING,
				Context.MODE_PRIVATE);
		dbUtil = ChannelNewsDBUtil.getInstance(getApplicationContext());
		getData();
		initView();
		initWebView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		isSupportZoom = sPreferences.getBoolean(Constants.SETTING_FONT_SIZE,
				false);
		title = (TextView) findViewById(R.id.title);
		progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogressbar);
		customview_Layout = (RelativeLayout) findViewById(R.id.comment_layout);
		// action_comment_coun = (TextView)
		// findViewById(R.id.action_comment_count);// 底部栏目数量
		action_repost = (ImageView) findViewById(R.id.action_view_comment);// 分想
		action_refresh = (ImageView) findViewById(R.id.action_refresh);
		action_favor = (ImageView) findViewById(R.id.action_favor);// 收藏状态
		progressBar.setVisibility(View.VISIBLE);
		title.setTextSize(13);
		title.setVisibility(View.VISIBLE);
		title.setText(news_url);
		action_repost.setOnClickListener(this);
		action_refresh.setOnClickListener(this);
		action_favor.setOnClickListener(this);
		if (isFavor) {// 是收藏
			action_favor.setImageResource(R.drawable.ic_action_favor_on_normal);
		}
		// action_comment_coun.setText(String.valueOf(newsEntity.getCommentNum()));
	}

	@SuppressLint("ResourceAsColor")
    private void initWebView() {
		webView = (WebView) findViewById(R.id.wb_details);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		if (!TextUtils.isEmpty(news_url)) {
			WebSettings webSettings = webView.getSettings();
			//webSettings.setJavaScriptEnabled(true);// 可以运行JS脚本
			// webSettings.setTextZoom(120);//Sets the text zoom of the page in
			// percent. The default is 100.
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
			// webSettings.setUseWideViewPort(true); // 打开页面时， 自适应屏幕
			webSettings.setLoadWithOverviewMode(true);// 打开页面时， 自适应屏幕
			webSettings.setSupportZoom(isSupportZoom);// 用于设置webview放大
			webSettings.setBuiltInZoomControls(isSupportZoom);
			// webView.setBackgroundColor(R.color.transparent);
			// webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
			//webSettings.setRenderPriority(RenderPriority.HIGH);
			webSettings.setBlockNetworkImage(true);

			webView.addJavascriptInterface(new JSInterface(
					getApplicationContext()), "imagelistner");// 添加JS交互接口类，并起别名imagelistner
			webView.setWebChromeClient(new MyWebChromeClient());// 此方法可以处理webview
																// 在加载时和加载完成时一些操作
			webView.setWebViewClient(new MyWebViewClient());
			new MyAsyncTask().execute(news_url, news_title, news_source + " "
					+ news_date);
		}

	}

	private void getData() {
		// TODO Auto-generated method stub
		activityFlag = getIntent().getIntExtra("flag", 0);// 判断从哪个Activity跳转的
															// 1：收藏新闻 0：新闻列表
		newsEntity = (NewsEntity) getIntent().getSerializableExtra("news");
		table = getIntent().getStringExtra("table");
		position = getIntent().getIntExtra("position", 0);
		System.out.println(table);
		news_url = newsEntity.getSource_url();
		news_title = newsEntity.getTitle();
		news_source = newsEntity.getSource();
		news_date = DateTools.getSection(String.valueOf(newsEntity
				.getPushTime()));
		isFavor = newsEntity.getMark() == Constants.mark_favor;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	class JSInterface {
		private Context context;

		public JSInterface(Context applicationContext) {
			// TODO Auto-generated constructor stub
			this.context = applicationContext;
		}

		public void openImage(String img) {// 点击图片放大效果
			String[] imgs = img.split(",");
			ArrayList<String> imgsUrl = new ArrayList<String>();
			for (String s : imgs) {
				imgsUrl.add(s);
				System.out.println("图片的URL：" + s);
			}
			// Intent intent = new Intent();
			// intent.putStringArrayListExtra("infos", imgsUrl);
			// intent.setClass(context, ImageShowActivity.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(intent);
		}
	}

	// WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
	private class MyWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if (newProgress != 100) {
				progressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	/**
	 * 如果希望点击链接由自己处理，而不是新开Android的系统browser中响应该链接。
	 * 给WebView添加一个事件监听对象（WebViewClient)
	 */
	// WebViewClient就是帮助WebView处理各种通知、请求事件的，
	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			view.getSettings().setJavaScriptEnabled(true);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			view.getSettings().setJavaScriptEnabled(true);
			view.getSettings().setBlockNetworkImage(
					sPreferences
							.getBoolean(Constants.SETTING_LOAD_IMAGE, false));// 非阻塞图片下在加速。。。
			// Html加载完成之后添加监听图片的JS函数
			addImageClickListener();
			progressBar.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);

		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			progressBar.setVisibility(View.GONE);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		/** 对网页中超链接按钮的响应。 当按下某个连接时WebViewClient会调用这个方法，并传递参数：按下的url */
		// //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}

	}

	// 注入JS函数监听
	private void addImageClickListener() {
		// TODO Auto-generated method stub
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		webView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\");"
				+ "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
				+ "imgurl+=objs[i].src+',';"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(imgurl);  "// 此处一一对应
				+ "    }  " + "}" + "})()");
	}

	class MyAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			String data = NewsDetailService.getNewsDetails(urls[0], urls[1],
					urls[2]);
			// NewsDetailService.Test3();
			return data;
		}

		@Override
		protected void onPostExecute(String data) {
			// TODO Auto-generated method stub
			super.onPostExecute(data);
			webView.loadDataWithBaseURL(news_url, data, "text/html", "utf-8",
					null);
			// webView.loadData(data, "text/html", "utf-8");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.action_view_comment:// 分享
			sdkHelper.shareToPlat(news_title, news_url);
			break;
		case R.id.action_refresh:// 刷新
			new MyAsyncTask().execute(news_url, news_title, news_source + " "
					+ news_date);
			webView.reload();
			break;
		case R.id.action_favor:// 收藏
			updateIsFavorState();
			break;
		default:
			break;
		}
	}

	//
	// /**
	// * 清除WebView缓存
	// */
	// public void clearWebViewCache() {
	//
	// // 清理Webview缓存数据库
	// try {
	// deleteDatabase("webview.db");
	// deleteDatabase("webviewCache.db");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// // WebView 缓存文件
	// File appCacheDir = new File(getFilesDir().getAbsolutePath()
	// + "cquptnews");
	// // Log.e(TAG, "appCacheDir path=" + appCacheDir.getAbsolutePath());
	//
	// File webviewCacheDir = new File(getCacheDir().getAbsolutePath()
	// + "/webviewCache");
	// // Log.e(TAG, "webviewCacheDir path=" +
	// // webviewCacheDir.getAbsolutePath());
	//
	// // 删除webview 缓存目录
	// if (webviewCacheDir.exists()) {
	// deleteFile(webviewCacheDir);
	// }
	// // 删除webview 缓存 缓存目录
	// if (appCacheDir.exists()) {
	// deleteFile(appCacheDir);
	// }
	// }
	//
	// /**
	// * 递归删除 文件/文件夹
	// *
	// * @param file
	// */
	// public void deleteFile(File file) {
	//
	// // Log.i(TAG, "delete file path=" + file.getAbsolutePath());
	//
	// if (file.exists()) {
	// if (file.isFile()) {
	// file.delete();
	// } else if (file.isDirectory()) {
	// File files[] = file.listFiles();
	// for (int i = 0; i < files.length; i++) {
	// deleteFile(files[i]);
	// }
	// }
	// file.delete();
	// } else {
	// // Log.e("tag", "delete file no exists " + file.getAbsolutePath());
	// }
	// }
	public void doBack(View v) {
		if (activityFlag == 1) {
			if (isFavor) {
			} else {
				Intent intent = new Intent();
				intent.setAction(Constants.UPDATE_FAVOR_ADAPTER);
				intent.putExtra("position", position);
				sendBroadcast(intent);
			}
		}
		this.finish();

	}

	private void updateIsFavorState() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		int i = 0;
		if (isFavor) {// 更新不在收藏
			action_favor.setImageResource(R.drawable.ic_action_favor_normal);
			isFavor = false;
			i = 0;
			intent.putExtra("isFavor", false);
			System.out.println("更新啦！！");
			newsEntity.setMark(22);
		} else {
			action_favor.setImageResource(R.drawable.ic_action_favor_on_normal);
			intent.putExtra("isFavor", true);
			i = 1;
			newsEntity.setMark(4);
			isFavor = true;
		}
		if (activityFlag == 1) {// 从收藏过来的，去更新收藏列表吧

		} else if (activityFlag == 0) {// 去更新新聞列表吧
			intent.setAction(Constants.UPDATE_ADAPTER);
			intent.putExtra("position", position);
			System.out.println("position::::" + position);
			sendBroadcast(intent);
		}

		dbUtil.updateData(
				table,
				new JsonNewsEntity(newsEntity.getTitle(), newsEntity
						.getSource_url(), newsEntity.getPushTime(), newsEntity
						.getCommentNum(), newsEntity.getPicOne(), newsEntity
						.getPicTwo(), newsEntity.getPicThr(), i),
				"sourceUrl=?", new String[] { newsEntity.getSource_url() });

	}
}
