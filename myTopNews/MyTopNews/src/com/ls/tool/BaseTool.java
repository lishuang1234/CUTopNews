package com.ls.tool;

import android.app.Activity;
import android.util.DisplayMetrics;

public class BaseTool {
	/**
	 * ·µ»ØÆµÄ»¿í¶È
	 * @param activity
	 * @return
	 */
	public static int getWindowWidth(Activity activity) {
		DisplayMetrics dMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		return dMetrics.widthPixels;

	}
}
