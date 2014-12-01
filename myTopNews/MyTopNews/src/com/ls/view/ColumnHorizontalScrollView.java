package com.ls.view;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class ColumnHorizontalScrollView extends HorizontalScrollView {
	private View ll_content;// 整体布局文件
	private View ll_more;// 更多选项布局
	private View rl_column;// 拖动栏布局
	private ImageView leftImageView;// 左阴影显示
	private ImageView rightImageView;// 右
	private int mScreenWidth;
	private Activity mActivity;

	public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ColumnHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ColumnHorizontalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		shade_ShowOrHide();

	}

	private void shade_ShowOrHide() {
		if (!mActivity.isFinishing() && ll_content != null) {
			measure(0, 0);
			if (getMeasuredWidth() <= mScreenWidth) {// 该控件宽度小于屏幕宽度
				leftImageView.setVisibility(View.GONE);
				rightImageView.setVisibility(View.GONE);
				return;
			}
			if (getLeft() == 0) {
				leftImageView.setVisibility(View.GONE);
				rightImageView.setVisibility(View.VISIBLE);
				return;
			}
			if (getRight() == getMeasuredWidth() - mScreenWidth) {
				leftImageView.setVisibility(View.VISIBLE);
				rightImageView.setVisibility(View.GONE);

				return;
			}
			leftImageView.setVisibility(View.VISIBLE);
			rightImageView.setVisibility(View.VISIBLE);

		}
	}

	/**
	 * 传入资源
	 * 
	 * @param mActivity
	 * @param ll_content
	 * @param leftImageView
	 * @param rightImageView
	 * @param ll_more
	 * @param rl_column
	 */
	public void setParam(Activity mActivity, View ll_content,
			ImageView leftImageView, ImageView rightImageView, View ll_more,
			View rl_column) {
		this.mActivity = mActivity;
		this.ll_content = ll_content;
		this.leftImageView = leftImageView;
		this.rightImageView = rightImageView;
		this.ll_more = ll_more;
		this.rl_column = rl_column;
	}

}
