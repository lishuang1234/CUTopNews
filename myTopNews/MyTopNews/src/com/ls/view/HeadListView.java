package com.ls.view;

import com.ls.adapter.NewsAdapter;
import com.ls.bean.NewsEntity;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HeadListView extends ListView {
	private final static int MAX_ALPHA = 255;
	private HeaderAdapter mHeaderAdapter;
	private View mHeaderView;
	private boolean mHeaderViewVisible;
	private int mHeaderViewWidth;
	private int mHeaderViewHeight;

	public HeadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public HeadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HeadListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/** 设置接口回调Adapter */
	public interface HeaderAdapter {
		public static final int HEADER_GONE = 0;
		public static final int HEADER_VISIBLE = 1;
		public static final int HEADER_PUSHED_UP = 2;

		/** 传入当前屏幕第一个Item的实际位置，获得Header状态：可见，向上抬起，不可见 */
		int getHeaderState(int position);

		/** 传入HeaderView设置Header的信息 */
		void configureHeader(View header, int position, int alpha);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
	//	System.out.println("onMeasure()");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mHeaderView != null) {
			measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
			mHeaderViewHeight = mHeaderView.getHeight();// 获得该Item的宽和高
			mHeaderViewWidth = mHeaderView.getWidth();
		}
	}

	/**
	 * 设置时间Item布局
	 * 
	 * @param view
	 */
	public void setPinnedHeaderView(View view) {
		mHeaderView = view;
		if (mHeaderView != null) {
			setFadingEdgeLength(0);
			// 设置ListView上下边缘阴影宽度
		}
		requestLayout();// 开始测量
	}

	/**
	 * 绘制时间Item
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		if (mHeaderViewVisible) {
			drawChild(canvas, mHeaderView, getDrawingTime());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (mHeaderView != null) {
			mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			configureHeaderView(getFirstVisiblePosition());
		}
	}

	/** getFirstVisibleposition:得到当前显示的第一个Item的实际Position 相对于全部Item而言 */
	/***/
	public void configureHeaderView(int firstVisiblePosition) {
		// TODO Auto-generated method stub
		if (mHeaderView == null) {
			return;
		}
		int state = mHeaderAdapter.getHeaderState(firstVisiblePosition);// 根据当前显示的第一个Item的position获得当前时间view的状态
		switch (state) {
		case HeaderAdapter.HEADER_GONE:
			mHeaderViewVisible = false;
	System.out.println("HEADER_GONE！");
			break;
		case HeaderAdapter.HEADER_PUSHED_UP:
	//		System.out.println("绘制HEADER_PUSHED_UP");
			View firstView = getChildAt(0);// ListView中的能看到的第一個元素
			int bottom = firstView.getBottom();
			int headerHeight = mHeaderView.getHeight();
			System.out.println("绘制HEADER_PUSHED_UP  bottom"+bottom+" height"+headerHeight);
			int y;
			int alpha;
			if (bottom < headerHeight) {// 判断LIstView的第一个Item与Header相对位置，设置透明值
				y = (bottom - headerHeight);
				alpha = (headerHeight + y) * MAX_ALPHA / headerHeight;
			} else {
				y = 0;
				alpha = MAX_ALPHA;
			}
		
			mHeaderAdapter.configureHeader(mHeaderView, firstVisiblePosition,
					alpha);// 谁知时间显示文字
			if (mHeaderView.getTop() != y) {// 向上滑動，設置位置
				mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight
						+ y);
			}
			mHeaderViewVisible = true;
			break;
		case HeaderAdapter.HEADER_VISIBLE: {
			System.out.println("HEADER_VISIBLE！");
			mHeaderAdapter.configureHeader(mHeaderView, firstVisiblePosition,
					MAX_ALPHA);
	//		System.out.println("绘制HEADER_VISIBLE+"+mHeaderView.getTop());
			if (mHeaderView.getTop() != 0) {
				mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
			//	System.out.println("绘制HEADER_VISIBLE");
			}
			mHeaderViewVisible = true;
			break;
		}
		default:
			break;
		}

	}

	public void setAdapter(NewsAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		mHeaderAdapter = (HeaderAdapter) adapter;
	}

}
