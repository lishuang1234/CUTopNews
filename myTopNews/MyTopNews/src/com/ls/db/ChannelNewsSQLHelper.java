package com.ls.db;

import com.ls.tool.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChannelNewsSQLHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "cquptnews";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String SOURCE_URL = "sourceUrl";
	public static final String PUBLISH_TIME = "publishTime";
	public static final String CLICKS = "clicks";
	public static final String PIC_ONE_URL = "picOneUrl";
	public static final String PIC_TWO_URL = "picTwoUrl";
	public static final String PIC_THERE_URL = "picThereUrl";
	public static final String IS_FAVOR = "isFavor";

	public ChannelNewsSQLHelper(Context context) {
		super(context, DB_NAME, null, 1);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (int i = 0; i < Constants.TABLE_NAME.length; i++) {
			String sql = "create table if not exists  "
					+ Constants.TABLE_NAME[i] + "(" + ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE
					+ " TEXT, " + SOURCE_URL + " TEXT," + PUBLISH_TIME
					+ " TEXT," + CLICKS + " TEXT," + PIC_ONE_URL + " TEXT,"
					+ PIC_TWO_URL + " TEXT," + PIC_THERE_URL + " TEXT,"
					+ IS_FAVOR + " INTEGER)";
			db.execSQL(sql);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}

}
