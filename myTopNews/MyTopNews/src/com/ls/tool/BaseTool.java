package com.ls.tool;

import com.ls.mytopnews.R;

import android.R.integer;
import android.app.Activity;
import android.util.DisplayMetrics;

public class BaseTool {
	/**
	 * ����ƵĻ���
	 * 
	 * @param activity
	 * @return
	 */
	public static int getWindowWidth(Activity activity) {
		DisplayMetrics dMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		return dMetrics.widthPixels;

	}

	public static int getDrawerId(String string) {
		int id = Integer.valueOf(string);
		int returnId = R.drawable.a_0;
		switch (id) {
		case 0:
			returnId = R.drawable.a_0;
			break;
		case 1:
			returnId = R.drawable.a_1;
			break;
		case 2:
			returnId = R.drawable.a_2;
			break;
		case 3:
			returnId = R.drawable.a_3;
			break;
		case 4:
			returnId = R.drawable.a_4;
			break;
		case 5:
			returnId = R.drawable.a_5;
			break;
		case 6:
			returnId = R.drawable.a_6;
			break;
		case 7:
			returnId = R.drawable.a_7;
			break;
		case 8:
			returnId = R.drawable.a_8;
			break;
		case 9:
			returnId = R.drawable.a_9;
			break;
		case 10:
			returnId = R.drawable.a_10;
			break;
		case 11:
			returnId = R.drawable.a_11;
			break;
		case 12:
			returnId = R.drawable.a_12;
			break;
		case 13:
			returnId = R.drawable.a_13;
			break;
		case 14:
			returnId = R.drawable.a_14;
			break;
		case 15:
			returnId = R.drawable.a_15;
			break;
		case 16:
			returnId = R.drawable.a_16;
			break;
		case 17:
			returnId = R.drawable.a_17;
			break;

		case 18:
			returnId = R.drawable.a_18;
			break;
		case 19:
			returnId = R.drawable.a_19;
			break;
		case 20:
			returnId = R.drawable.a_20;
			break;
		case 21:
			returnId = R.drawable.a_21;
			break;
		case 22:
			returnId = R.drawable.a_22;
			break;
		case 23:
			returnId = R.drawable.a_23;
			break;
		case 24:
			returnId = R.drawable.a_24;
			break;
		case 25:
			returnId = R.drawable.a_25;
			break;
		case 26:
			returnId = R.drawable.a_26;
			break;
		case 27:
			returnId = R.drawable.a_27;
			break;
		case 28:
			returnId = R.drawable.a_28;
			break;
		case 29:
			returnId = R.drawable.a_29;
			break;
		case 30:
			returnId = R.drawable.a_30;
			break;
		case 31:
			returnId = R.drawable.a_31;
			break;
		default:
			break;
		}
		return returnId;

	}
}
