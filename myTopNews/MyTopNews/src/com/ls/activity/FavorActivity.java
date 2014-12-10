package com.ls.activity;

import java.util.List;

import com.ls.adapter.FavorListAdapter;
import com.ls.bean.NewsEntity;
import com.ls.mytopnews.R;
import com.ls.tool.Constants;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FavorActivity extends Activity {
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		adapter.destroyReceiver();
		super.onDestroy();
	}

	private ListView favorList;
	private FavorListAdapter adapter;
	private ProgressBar progressBar;
	private List<NewsEntity> newsEntities;
	private TextView back;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				favorList.setAdapter(adapter);
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.left_drawer_favor);
		initView();
		new GetIsFavor().execute("");
	}

	private void initView() {
		// TODO Auto-generated method stub
		favorList = (ListView) findViewById(R.id.left_drawer_favor_lv_favor);
		progressBar = (ProgressBar) findViewById(R.id.favor_progressbar);
		back = (TextView) findViewById(R.id.back);
		((TextView) findViewById(R.id.title)).setText("已收藏新闻");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(FavorActivity.this,
				// MainActivity.class));
				FavorActivity.this.finish();
			}
		});
		favorList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(FavorActivity.this,
						DetailsActivity.class);
				intent.putExtra("flag", 1);//
				intent.putExtra("news", (NewsEntity) adapter.getItem(position));
				intent.putExtra("table",
						((NewsEntity) adapter.getItem(position)).getTable());
				intent.putExtra("position", position);
				startActivity(intent);

			}
		});
	}

	class GetIsFavor extends AsyncTask<String, String, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			Constants constants = new Constants(getApplicationContext());
			newsEntities = constants.getFavorNews();
			adapter = new FavorListAdapter(newsEntities,
					getApplicationContext());
			System.out.println("adapter");

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			handler.sendEmptyMessage(0);
			progressBar.setVisibility(View.GONE);
		}
	}

}
