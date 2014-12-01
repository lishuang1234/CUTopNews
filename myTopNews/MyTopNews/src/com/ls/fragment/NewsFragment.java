package com.ls.fragment;

import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ls.activity.DetailsActivity;
import com.ls.adapter.NewsAdapter;
import com.ls.app.AppApplication;
import com.ls.bean.NewsEntity;
import com.ls.mytopnews.R;
import com.ls.tool.Constants;
import com.ls.view.HeadListView;
import com.ls.view.RefreshableView;
import com.ls.view.RefreshableView.PullToRefreshListener;

public class NewsFragment extends Fragment {
	private String textString;
	private int orderId;
	private List<NewsEntity> newsList;// 新闻实体类列表
	private HeadListView mListView;
	private RelativeLayout notify_view;
	private TextView notify_view_textTextureView;
	private ImageView detail_loading;
	private static final int SET_NEWSLIST = 0;
	private static final int REFRESH_NEWSLIST = 1;
	private NewsAdapter newsAdapter;
	private Activity mActivity;
	private AppApplication mApplication;
	private View listDateHeader;
	private RefreshableView refreshableView;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SET_NEWSLIST:
				detail_loading.setVisibility(View.GONE);
				if (newsList != null)
					newsAdapter = new NewsAdapter(mActivity, newsList,
							Constants.TABLE_NAME[orderId]);
				// /判断是否为城市ID，先省略
				mListView.setOnScrollListener(newsAdapter);
				mListView.setPinnedHeaderView(listDateHeader);// 注意此处参数
				mListView.setAdapter(newsAdapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						// 设置条目点击事件
						Intent intent = new Intent(mActivity,
								DetailsActivity.class);
						intent.putExtra("news", newsAdapter.getItem(position));
						startActivity(intent);
					}
				});
				break;

			case REFRESH_NEWSLIST:
				if (newsList != null) {
					newsAdapter.addNewsEntity(newsList);
					initNotify(newsList.size());
				} else
					initNotify(0);
				refreshableView.finishRefreshing();
				break;
			default:

				break;
			}
		}
	};

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	/** 刷新提示文字 */
	protected void initNotify(final int newsNum) {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (newsNum != 0) {
					notify_view_textTextureView.setText(String.format(
							getString(R.string.ss_pattern_update), newsNum));
				} else {
					notify_view_textTextureView.setText("已经是最新");
				}
				notify_view.setVisibility(View.VISIBLE);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						notify_view.setVisibility(View.GONE);
					}
				}, 2000);
			}
		}, 1000);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		textString = bundle != null ? bundle.getString("text") : "";
		orderId = bundle != null ? bundle.getInt("order_id") : 0;
		mApplication = AppApplication.getApp();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.news_fragment, null);
		refreshableView = (RefreshableView) view
				.findViewById(R.id.refreshable_view);
		mListView = (HeadListView) view.findViewById(R.id.mListView);
		listDateHeader = (View) view.findViewById(R.id.list_header);
		detail_loading = (ImageView) view.findViewById(R.id.detail_loading);
		// 提示框
		notify_view = (RelativeLayout) view.findViewById(R.id.notify_view);
		notify_view_textTextureView = (TextView) view
				.findViewById(R.id.notify_view_text);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {// 下拉刷新接口
					@Override
					public void onRefresh() {// 执行刷新操作
						new Work().execute(Constants.TABLE_NAME[orderId], "1");
					}
				}, orderId);
		new Work().execute(Constants.TABLE_NAME[orderId], "0");
		return view;
	}

	class Work extends AsyncTask<String, Integer, Integer> {
		@Override
		protected Integer doInBackground(String... params) {
			Constants constants = new Constants(
					mActivity.getApplicationContext());
			if (params[1].equals("0")) {// 获取新闻从数据库
				newsList = constants.getNewsList(params[0], Constants.GET_NEWS);
				return SET_NEWSLIST;
			} else {// 刷新新闻
				newsList = constants.getNewsList(params[0],
						Constants.UP_DATE_NEWS);
				return REFRESH_NEWSLIST;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == SET_NEWSLIST) {
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();// 性能
			} else {
				handler.obtainMessage(REFRESH_NEWSLIST).sendToTarget();// 性能
			}
		}
	}
}
