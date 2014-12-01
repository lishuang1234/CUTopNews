package com.ls.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.string;

import com.ls.adapter.NewsAdapter;

public class DateTools {
	public static String getTime() {
		String re_time = null;
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy.MM.dd");
		Date d = new Date(currentTime);
		re_time = sFormat.format(d);
		return re_time;
	}

	public static String getSection(String cc_time) {

		return cc_time;
	}

/**返回星期*/
	public static String getWeekOfDate(String date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    	Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = dateFormat.parse(date);
			calendar.setTime(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
}
