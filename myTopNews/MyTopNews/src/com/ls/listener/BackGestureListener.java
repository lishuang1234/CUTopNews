package com.ls.listener;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.ls.activity.BaseActivity;
/**监听手势，Activity返回*/
public class BackGestureListener implements OnGestureListener {
	private BaseActivity baseActivity;

	public BackGestureListener(BaseActivity baseActivity) {
		// TODO Auto-generated constructor stub
		this.baseActivity = baseActivity;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		if ((e2.getX() - e1.getX()) > 100
				&& Math.abs(e1.getY() - e2.getY()) < 60) {
			baseActivity.onBackPressed();
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

}
