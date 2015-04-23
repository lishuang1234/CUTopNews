package com.ls.adapter;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.activity.MainActivity;
import com.ls.bean.JsonNewsEntity;
import com.ls.bean.NewsEntity;
import com.ls.db.ChannelNewsDBUtil;
import com.ls.mytopnews.R;
import com.ls.tool.Constants;

public class FavorListAdapter extends BaseAdapter implements OnClickListener {
	private List<NewsEntity> newsEntities;
	private Context context;
	private LayoutInflater inflater;
	private ImageView btn_close;
	private View btn_dislike;
	private PopupWindow popupWindow;
	private ChannelNewsDBUtil dbUtil;
	private String table;
	private NewsEntity entity;
	private UpdateState updateState = new UpdateState();

	public FavorListAdapter(List<NewsEntity> list, Context context) {
		this.context = context;
		this.newsEntities = list;
		this.inflater = LayoutInflater.from(context);
		dbUtil = ChannelNewsDBUtil.getInstance(context);
		initPopwindow();
		IntentFilter filter = new IntentFilter(Constants.UPDATE_FAVOR_ADAPTER);
		context.registerReceiver(updateState, filter);
	}

	public void destroyReceiver() {
		context.unregisterReceiver(updateState);

	}

	private void initPopwindow() {
		// TODO Auto-generated method stub
		View popView = inflater.inflate(R.layout.list_item_pop, null);
		popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		popupWindow.setAnimationStyle(R.style.PopmenuAnimation);
		btn_close = (ImageView) popView.findViewById(R.id.btn_pop_close);
		btn_dislike = popView.findViewById(R.id.ll_pop_dislike);
		((View) popView.findViewById(R.id.ll_pop_favor))
				.setVisibility(View.GONE);
		btn_close.setOnClickListener(this);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsEntities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Viewholder viewholder = null;
		View view = convertView;
		if (view == null) {
			viewholder = new Viewholder();
			view = inflater.inflate(R.layout.left_drawer_favor_list_item, null);
			viewholder.popImageView = (ImageView) view
					.findViewById(R.id.favor_item_popicon);
			viewholder.titleTextView = (TextView) view
					.findViewById(R.id.favor_item_title);
			viewholder.publishTimeTextView = (TextView) view
					.findViewById(R.id.favor_item_publistime);
			view.setTag(viewholder);
		} else {
			viewholder = (Viewholder) view.getTag();
		}
		entity = (NewsEntity) getItem(position);
		viewholder.publishTimeTextView.setText(entity.getPushTime());
		viewholder.titleTextView.setText(entity.getTitle());
		viewholder.popImageView.setOnClickListener(new PopAction(position));// 显示弹出窗口

		return view;
	}

	static class Viewholder {
		ImageView popImageView;
		TextView titleTextView;
		TextView publishTimeTextView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_pop_close:
			popupWindow.dismiss();
			break;
		// case R.id.ll_pop_dislike:
		// setDislike();
		//
		// break;
		// case R.id.favor_item_popicon:
		//
		// break;
		default:
			break;
		}

	}

	private void setDislike(int position) {
		// TODO Auto-generated method stub
		Toast.makeText(context.getApplicationContext(), "已取消收藏",
				Toast.LENGTH_SHORT).show();
		popupWindow.dismiss();
		// NewsEntity entity = (NewsEntity) getItem(position);// 注意
		NewsEntity entity = newsEntities.get(position);// 注意
		dbUtil.updateData(
				entity.getTable(),
				new JsonNewsEntity(entity.getTitle(), entity.getSource_url(),
						entity.getPushTime(), entity.getCommentNum(), entity
								.getPicOne(), entity.getPicTwo(), entity
								.getPicThr(), 0), "sourceUrl=?",
				new String[] { entity.getSource_url() });
		entity.setMark(22);
		newsEntities.remove(entity);
		notifyDataSetChanged();
		Intent intent = new Intent(Constants.UPDATE_LISTVIEW);
		System.out.println("发广播啦。。。Favorite");
		int fragmentId = Constants.getFragmentId(entity.getTable());
		if (fragmentId != -1) {
			intent.putExtra("fragment_position", fragmentId);
			context.sendBroadcast(intent);
		}
	}

	private void showPop(View v) {
		// TODO Auto-generated method stub
		int[] array = new int[2];
		v.getLocationInWindow(array);
		popupWindow.showAtLocation(v, 0, array[0], array[1]);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	class PopAction implements OnClickListener {
		private int position;

		public PopAction(int position) {
			this.position = position;

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showPop(v);
			btn_dislike.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setDislike(position);
				}
			});
		}
	}

	class UpdateState extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			setDislike(intent.getIntExtra("position", 0));// 更新收藏的数据
		}

	}
}
