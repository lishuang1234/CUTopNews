package com.ls.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.net.NetworkInfo.DetailedState;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.bean.JsonNewsEntity;
import com.ls.bean.NewsEntity;
import com.ls.db.ChannelNewsDBUtil;
import com.ls.mytopnews.R;
import com.ls.service.NewsDetailService;
import com.ls.tool.Constants;
import com.ls.tool.DateTools;
import com.ls.tool.Options;
import com.ls.view.HeadListView;
import com.ls.view.HeadListView.HeaderAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsAdapter extends BaseAdapter implements HeaderAdapter,
		SectionIndexer, OnScrollListener {
	List<NewsEntity> newsList;
	Activity activity;
	LayoutInflater inflater = null;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	/** 弹出的更多选择框 */
	private PopupWindow popWindow;
	private List<Integer> mPositions;
	private List<String> mSections;
	private ImageView btn_pop_close;
	private View btn_pop_favor;
	private View btn_pop_dislike;
	private ChannelNewsDBUtil dbUtil;
	private String table;

	public NewsAdapter(Activity activity, List<NewsEntity> newsList,
			String table) {
		this.activity = activity;
		this.newsList = newsList;
		this.table = table;
		inflater = LayoutInflater.from(activity);
		options = Options.getListOptions();
		dbUtil = ChannelNewsDBUtil
				.getInstance(activity.getApplicationContext());
		initPopWindow();
		initDateHead();

	}

	private void initPopWindow() {
		// TODO Auto-generated method stub
		View popView = inflater.inflate(R.layout.list_item_pop, null);
		popWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popWindow.setBackgroundDrawable(new ColorDrawable(0));
		popWindow.setAnimationStyle(R.style.PopmenuAnimation);// 出现消失动画
		btn_pop_close = (ImageView) popView.findViewById(R.id.btn_pop_close);
		btn_pop_favor = (View) popView.findViewById(R.id.ll_pop_favor);
		btn_pop_dislike = (View) popView.findViewById(R.id.ll_pop_dislike);

	}

	private void initDateHead() {
		// TODO Auto-generated method stub
		mSections = new ArrayList<String>();
		mPositions = new ArrayList<Integer>();
		for (int i = 0; i < newsList.size(); i++) {
			if (i == 0) {
				mSections.add(newsList.get(i).getPushTime());
				mPositions.add(i);
				continue;
			}
			if (i != newsList.size()) {
				if (!newsList.get(i).getPushTime()
						.equals(newsList.get(i - 1).getPushTime())) {// 12.345.678910
					mSections.add(newsList.get(i).getPushTime());
					mPositions.add(i);// 136
				}
			}
		}

	}

	public void addNewsEntity(List<NewsEntity> newsEntities) {
		for (int i = 0; i < newsEntities.size(); i++) {
			newsList.add(i, newsEntities.get(i));
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList == null ? 0 : newsList.size();
	}

	@Override
	public NewsEntity getItem(int position) {
		// TODO Auto-generated method stub
		if (newsList != null && newsList.size() != 0) {
			return newsList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	static class ViewHolder {
		LinearLayout item_layout;
		// title
		TextView item_title;
		// 图片源
		TextView item_source;
		// 类似推广之类的标签
		TextView list_item_local;
		// 评论数量
		TextView comment_count;
		// 发布时间
		TextView publish_time;
		// 新闻摘要
		TextView item_abstract;
		// 右上方TAG标记图片
		ImageView alt_mark;
		// 右边图片
		ImageView right_image;
		// 3张图片布局
		LinearLayout item_image_layout; // 3张图片时候的布局
		ImageView item_image_0;
		ImageView item_image_1;
		ImageView item_image_2;
		// 大图的图片的话布局
		ImageView large_image;
		// pop按钮
		ImageView popicon;
		// 评论布局
		RelativeLayout comment_layout;
		TextView comment_content;
		// paddingview
		View right_padding_view;

		// 头部的日期部分
		LinearLayout layout_list_section;
		TextView section_text;
		TextView section_day;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.list_item, null);
			mHolder = new ViewHolder();
			mHolder.item_layout = (LinearLayout) view
					.findViewById(R.id.item_layout);
			mHolder.comment_layout = (RelativeLayout) view
					.findViewById(R.id.comment_layout);
			mHolder.item_title = (TextView) view.findViewById(R.id.item_title);
			mHolder.item_source = (TextView) view
					.findViewById(R.id.item_source);
			mHolder.list_item_local = (TextView) view
					.findViewById(R.id.list_item_local);
			mHolder.comment_count = (TextView) view
					.findViewById(R.id.comment_count);
			mHolder.publish_time = (TextView) view
					.findViewById(R.id.publish_time);
			mHolder.item_abstract = (TextView) view
					.findViewById(R.id.item_abstract);
			mHolder.alt_mark = (ImageView) view.findViewById(R.id.alt_mark);
			mHolder.right_image = (ImageView) view
					.findViewById(R.id.right_image);
			mHolder.item_image_layout = (LinearLayout) view
					.findViewById(R.id.item_image_layout);
			mHolder.item_image_0 = (ImageView) view
					.findViewById(R.id.item_image_0);
			mHolder.item_image_1 = (ImageView) view
					.findViewById(R.id.item_image_1);
			mHolder.item_image_2 = (ImageView) view
					.findViewById(R.id.item_image_2);
			mHolder.large_image = (ImageView) view
					.findViewById(R.id.large_image);
			mHolder.popicon = (ImageView) view.findViewById(R.id.popicon);
			mHolder.comment_content = (TextView) view
					.findViewById(R.id.comment_content);
			mHolder.right_padding_view = (View) view
					.findViewById(R.id.right_padding_view);
			// 头部的日期部分
			mHolder.layout_list_section = (LinearLayout) view
					.findViewById(R.id.layout_list_section);
			mHolder.section_text = (TextView) view
					.findViewById(R.id.section_text);
			mHolder.section_day = (TextView) view
					.findViewById(R.id.section_day);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}

		// 获取position对应的数据
		NewsEntity news = (NewsEntity) getItem(position);
		mHolder.item_title.setText(news.getTitle());
		mHolder.item_source.setText(news.getSource());
		mHolder.comment_count.setText("点击：" + news.getCommentNum());
		mHolder.publish_time.setText(news.getPushTime());
		List<String> imgUrlList = news.getPicList();
		mHolder.popicon.setVisibility(View.VISIBLE);
		mHolder.comment_count.setVisibility(View.VISIBLE);
		mHolder.right_padding_view.setVisibility(View.VISIBLE);

		if (imgUrlList != null && imgUrlList.size() != 0) {
			System.out.println("adapter picsieze: " + imgUrlList.size());
			if (imgUrlList.size() == 1) {
				mHolder.item_image_layout.setVisibility(View.GONE);
				// 是否是大图
				if (news.getIsLarge()) {
					mHolder.large_image.setVisibility(View.VISIBLE);
					mHolder.right_image.setVisibility(View.GONE);
					imageLoader.displayImage(imgUrlList.get(0),
							mHolder.large_image, options);
					mHolder.popicon.setVisibility(View.GONE);
					mHolder.comment_count.setVisibility(View.GONE);
					mHolder.right_padding_view.setVisibility(View.GONE);
				} else {
					mHolder.large_image.setVisibility(View.GONE);
					mHolder.right_image.setVisibility(View.VISIBLE);
					imageLoader.displayImage(imgUrlList.get(0),
							mHolder.right_image, options);
				}
			} else if (imgUrlList.size() == 3) {
				mHolder.large_image.setVisibility(View.GONE);
				mHolder.right_image.setVisibility(View.GONE);
				mHolder.item_image_layout.setVisibility(View.VISIBLE);
				imageLoader.displayImage(imgUrlList.get(0),
						mHolder.item_image_0, options);
				imageLoader.displayImage(imgUrlList.get(1),
						mHolder.item_image_1, options);
				imageLoader.displayImage(imgUrlList.get(2),
						mHolder.item_image_2, options);
			} else {
				mHolder.large_image.setVisibility(View.GONE);
				mHolder.right_image.setVisibility(View.GONE);
				mHolder.item_image_layout.setVisibility(View.GONE);
			}
		} else {
			mHolder.large_image.setVisibility(View.GONE);
			mHolder.right_image.setVisibility(View.GONE);
			mHolder.item_image_layout.setVisibility(View.GONE);
		}
		int markResID = getAltMArkResID(news.getMark(), news.getCllectStatue());
		if (markResID != -1) {
			mHolder.alt_mark.setVisibility(View.VISIBLE);
			mHolder.alt_mark.setImageResource(markResID);
		} else {
			mHolder.alt_mark.setVisibility(View.GONE);
		}
		// 判断该新闻概述是否为空
		if (!TextUtils.isEmpty(news.getNewsAbstract())) {
			mHolder.item_abstract.setVisibility(View.VISIBLE);
			mHolder.item_abstract.setText(news.getNewsAbstract());
		} else {
			mHolder.item_abstract.setVisibility(View.GONE);
		}
		// 判断该新闻是否是特殊标记的，推广等，为空就是新闻
		if (!TextUtils.isEmpty(news.getLocal())) {
			mHolder.list_item_local.setVisibility(View.VISIBLE);
			mHolder.list_item_local.setText(news.getLocal());
		} else {
			mHolder.list_item_local.setVisibility(View.GONE);
		}
		// 判断评论字段是否为空，不为空显示对应布局
		if (!TextUtils.isEmpty(news.getComment())) {
			// news.getLocal() != null &&
			mHolder.comment_layout.setVisibility(View.VISIBLE);
			mHolder.comment_content.setText(news.getComment());
		} else {
			mHolder.comment_layout.setVisibility(View.GONE);
		}
		// 判断该新闻是否已读
		if (!news.getReadStatus()) {
			mHolder.item_layout.setSelected(true);
		} else {
			mHolder.item_layout.setSelected(false);
		}
		// 设置+按钮点击效果
		mHolder.popicon.setOnClickListener(new PopAction(position));
		// 头部的相关东西
		int section = getSectionForPosition(position);
		if (getPositionForSection(section) == position) {// 第一个Section才显示時間日期
			mHolder.layout_list_section.setVisibility(View.VISIBLE);
			// head_title.setText(news.getDate());
			mHolder.section_text.setText(mSections.get(section));
			mHolder.section_day.setText(DateTools.getWeekOfDate(mSections
					.get(section)));
		} else {
			mHolder.layout_list_section.setVisibility(View.GONE);
		}
		return view;
	}

	/** 根据属性获取资源 */

	private int getAltMArkResID(Integer mark, Boolean isfavor) {
		// TODO Auto-generated method stub
		if (isfavor) {
			return R.drawable.ic_mark_favor;
		}
		switch (mark) {
		case Constants.mark_recom:
			return R.drawable.ic_mark_recommend;
		case Constants.mark_hot:
			return R.drawable.ic_mark_hot;
		case Constants.mark_first:
			return R.drawable.ic_mark_first;
		case Constants.mark_exclusive:
			return R.drawable.ic_mark_exclusive;
		case Constants.mark_favor:
			return R.drawable.ic_mark_favor;
		default:
			break;
		}
		return -1;
	}

	class PopAction implements OnClickListener {
		int position;

		public PopAction(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int[] arrayOfInt = new int[2];
			// 获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			showPop(v, x, y, position);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	/** 显示PopWindws */
	public void showPop(View v, int x, int y, int position) {

		// TODO Auto-generated method stub
		// final ViewHolder mHolder = new ViewHolder();
		final NewsEntity newsEntity = getItem(position);
		int markFavor = newsEntity.getMark();
		final String title = newsEntity.getTitle();
		final String suorceUrl = newsEntity.getSource_url();
		final String publishTime = newsEntity.getPushTime();
		final String picOneUrl = newsEntity.getPicOne();
		final String picTwoUrl = newsEntity.getPicTwo();
		final String picThereUrl = newsEntity.getPicThr();
		final String clicks = newsEntity.getCommentNum();
		// 设置Pop显示位置
		popWindow.showAtLocation(v, 0, x, y);
		// pop获取焦点
		popWindow.setFocusable(true);
		// pop点击外部就消失
		popWindow.setOutsideTouchable(true);
		popWindow.update();
		if (popWindow.isShowing()) {// 设置显示属性
		
		}
		btn_pop_dislike.setOnClickListener(new OnClickListener() {
			ViewHolder mHolder = new ViewHolder();

			@Override
			public void onClick(View v) {

				Toast.makeText(activity.getApplicationContext(), "已取消收藏",
						Toast.LENGTH_SHORT).show();
				popWindow.dismiss();
				dbUtil.updateData(table, new JsonNewsEntity(title, suorceUrl,
						publishTime, clicks, picOneUrl, picTwoUrl, picThereUrl,
						0), "sourceUrl=?", new String[] { suorceUrl });
				newsEntity.setMark(22);
				notifyDataSetChanged();

			}
		});
		btn_pop_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popWindow.dismiss();
			}
		});
		btn_pop_favor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Toast.makeText(activity.getApplicationContext(), "已收藏",
						Toast.LENGTH_SHORT).show();
				popWindow.dismiss();
				dbUtil.updateData(table, new JsonNewsEntity(title, suorceUrl,
						publishTime, clicks, picOneUrl, picTwoUrl, picThereUrl,
						1), "sourceUrl=?", new String[] { suorceUrl });// 更新收藏数据
				newsEntity.setMark(4);
				notifyDataSetChanged();
			}
		});
	}

	/** 所有状态改变事件源 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (view instanceof HeadListView) {
			// System.out.println("view.getFirstVisiblePosition()"
			// + view.getCount());
			((HeadListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	/***
	 * 根据可见的第一个Item判断此时時間View的状态
	 */
	@Override
	public int getHeaderState(int position) {
		// TODO Auto-generated method stub
		int realPosition = position;
		// 要判断是否是城市选项做相应處理？

		if (realPosition < 0 || realPosition >= getCount()) {
			return HEADER_GONE;
		}
		int section = getSectionForPosition(realPosition);
		int nextSectionPosition = getPositionForSection(section + 1);
		System.out.println("view.section" + section + "  nextSectionPosition:"
				+ nextSectionPosition);
		if (nextSectionPosition != -1
				&& realPosition == nextSectionPosition - 1) {// 有下一个Item对应
			return HEADER_PUSHED_UP;
		}
		return HEADER_VISIBLE;
	}

	/** 设置HeaderTitle */
	@Override
	public void configureHeader(View header, int position, int alpha) {
		// TODO Auto-generated method stub
		int realPosition = position;
		int section = getSectionForPosition(realPosition);
		String titleString = (String) getSections()[section];
		((TextView) header.findViewById(R.id.section_day)).setText(DateTools
				.getWeekOfDate(titleString));
		((TextView) header.findViewById(R.id.section_text))
				.setText(titleString);
	}

	@Override
	public Object[] getSections() {
		return mSections.toArray();

	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		// TODO Auto-generated method stub
		if (sectionIndex < 0 || sectionIndex >= mPositions.size()) {
			return -1;
		}
		return mPositions.get(sectionIndex);
	}

	// 通过该项的位置，获得所在分类组的索引号
	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		if (position < 0 || position >= getCount()) {
			return -1;
		}
		int index = Arrays.binarySearch(mPositions.toArray(), position);
		return index >= 0 ? index : -index - 2;
	}
}
