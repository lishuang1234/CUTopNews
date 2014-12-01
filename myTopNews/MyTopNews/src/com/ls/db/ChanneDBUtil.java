package com.ls.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChanneDBUtil {
	private static ChanneDBUtil channelDbUtil;// static 只有一份


	private Context mContext;
	private ChannelSQLHelper mSQLHelp;
	private SQLiteDatabase mSQLiteDatabase;


	private ChanneDBUtil(Context context) {// 適用於新闻频道数据库
		mContext = context;
		mSQLHelp = new ChannelSQLHelper(context);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
	}

	/**
	 * 初始化数据库操作DBUtil类
	 */
	public static ChanneDBUtil getInstance(Context context) {

			if (channelDbUtil == null) {
				channelDbUtil = new ChanneDBUtil(context);
			}
			return channelDbUtil;
	}


	/**
	 * 关闭数据库
	 */
	public void close() {
		mSQLHelp.close();
		mSQLHelp = null;
		mSQLiteDatabase.close();
		mSQLiteDatabase = null;
		channelDbUtil = null;
	}

	/**
	 * 添加数据
	 */
	public long insertData(ContentValues values) {
		return mSQLiteDatabase.insert(ChannelSQLHelper.TABLE_CHANNEL, null,
				values);
	}

	/**
	 * 更新数据
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public int updateData(ContentValues values, String whereClause,
			String[] whereArgs) {
		return mSQLiteDatabase.update(ChannelSQLHelper.TABLE_CHANNEL, values,
				whereClause, whereArgs);
	}

	/**
	 * 删除数据
	 * 
	 * @param whereClause
	 * @param whereArgs
	 */
	public int deleteData(String whereClause, String[] whereArgs) {
		return mSQLiteDatabase.delete(ChannelSQLHelper.TABLE_CHANNEL,
				whereClause, whereArgs);
	}

	/**
	 * 查询数据
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor selectData(String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor = mSQLiteDatabase.query(ChannelSQLHelper.TABLE_CHANNEL,
				columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}
}