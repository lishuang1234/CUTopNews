package com.ls.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ls.listener.BackGestureListener;

public class BaseActivity extends Activity {
	/** 手势监听 */
	GestureDetector mGestureDetector = null;
	/** 是否关闭手势监听 */
	private boolean mNeedBackGesture = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initGestureDetector();

	}

	private void initGestureDetector() {
		// TODO Auto-generated method stub
		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(getApplicationContext(),
					new BackGestureListener(this));
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/*
	 * 设置是否进行手势监听
	 */
	public void setNeedBackGesture(boolean mNeedBackGesture) {
		this.mNeedBackGesture = mNeedBackGesture;
	}

	
/**分发手勢监听*/
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mNeedBackGesture) {
			return mGestureDetector.onTouchEvent(ev)
					|| super.dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}
}
