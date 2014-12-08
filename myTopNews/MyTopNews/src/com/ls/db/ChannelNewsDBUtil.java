package com.ls.db;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ls.bean.JsonNewsEntity;

public class ChannelNewsDBUtil {
	private static ChannelNewsDBUtil channelNewsDbUtil;// static 只有一份

	private Context mContext;
	private ChannelNewsSQLHelper mSQLHelp;
	private SQLiteDatabase mSQLiteDatabase;

	private ChannelNewsDBUtil(Context context) {// 適用於新闻频道数据库
		mContext = context;

	}

	/**
	 * 初始化数据库操作DBUtil类
	 */
	public static ChannelNewsDBUtil getInstance(Context context) {

		if (channelNewsDbUtil == null) {
			channelNewsDbUtil = new ChannelNewsDBUtil(context);
		}
		return channelNewsDbUtil;
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		mSQLHelp.close();
		mSQLHelp = null;
		mSQLiteDatabase.close();
		mSQLiteDatabase = null;
		channelNewsDbUtil = null;
	}

	/**
	 * 添加数据
	 */
	public boolean insertData(String table, JsonNewsEntity jEntity) {
		boolean flag = false;
		mSQLHelp = new ChannelNewsSQLHelper(mContext);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
		long id = -1;
		ContentValues values = new ContentValues();
		values.put("title", jEntity.getTitle());
		values.put("sourceUrl", jEntity.getSourceUrl());
		values.put("publishTime", jEntity.getPublishTime());
		values.put("clicks", jEntity.getClicks());
		values.put("picOneUrl", jEntity.getPicOneUrl());
		values.put("picTwoUrl", jEntity.getPicTwoUrl());
		values.put("picThereUrl", jEntity.getPicThereUrl());
		values.put("isFavor", 0);
		id = mSQLiteDatabase.insert(table, null, values);
		flag = (id != -1 ? true : false);
		return flag;
	}

	/**
	 * 更新数据
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public int updateData(String table, JsonNewsEntity jEntity,
			String whereClause, String[] whereArgs) {
		mSQLHelp = new ChannelNewsSQLHelper(mContext);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", jEntity.getTitle());
		values.put("sourceUrl", jEntity.getSourceUrl());
		values.put("publishTime", jEntity.getPublishTime());
		values.put("clicks", jEntity.getClicks());
		values.put("picOneUrl", jEntity.getPicOneUrl());
		values.put("picTwoUrl", jEntity.getPicTwoUrl());
		values.put("picThereUrl", jEntity.getPicThereUrl());
		values.put("isFavor", jEntity.getIsFavor());
		int flag = mSQLiteDatabase
				.update(table, values, whereClause, whereArgs);

		return flag;
	}

	/**
	 * 删除数据
	 * 
	 * @param whereClause
	 * @param whereArgs
	 */
	public int deleteData(String table, String whereClause, String[] whereArgs) {
		mSQLHelp = new ChannelNewsSQLHelper(mContext);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
		int flag = mSQLiteDatabase.delete(table, whereClause, whereArgs);

		return flag;
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
	public List<JsonNewsEntity> selectData(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		mSQLHelp = new ChannelNewsSQLHelper(mContext);
		List<JsonNewsEntity> jsonNewsEntities = new ArrayList<JsonNewsEntity>();
		mSQLiteDatabase = mSQLHelp.getReadableDatabase();
		Cursor cursor = mSQLiteDatabase.query(table, columns, selection,
				selectionArgs, groupBy, having, orderBy);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String sourceUrl = cursor.getString(cursor
					.getColumnIndex("sourceUrl"));
			String publishTime = cursor.getString(cursor
					.getColumnIndex("publishTime"));
			String clicks = cursor.getString(cursor.getColumnIndex("clicks"));
			String picOneUrl = cursor.getString(cursor
					.getColumnIndex("picOneUrl"));
			String picTwoUrl = cursor.getString(cursor
					.getColumnIndex("picTwoUrl"));
			String picThereUrl = cursor.getString(cursor
					.getColumnIndex("picThereUrl"));
			int isFavor = cursor.getInt(cursor.getColumnIndex("isFavor"));

			jsonNewsEntities.add(new JsonNewsEntity(title, sourceUrl,
					publishTime, clicks, picOneUrl, picTwoUrl, picThereUrl,
					isFavor));
		}
		cursor.close();
		return jsonNewsEntities;
	}

	public int queryCount(String table) {
		mSQLHelp = new ChannelNewsSQLHelper(mContext);
		mSQLiteDatabase = mSQLHelp.getReadableDatabase();

		// 查询记录条数
		Cursor c = mSQLiteDatabase
				.query(false, table, new String[] { "COUNT(*)" }, null, null,
						null, null, null, null);

		c.moveToNext();
		int count = c.getInt(0);
		System.out.println(count);
		c.close();
		mSQLiteDatabase.close();
		return count;
	}

//	public void deleteCache(String table, String order, int start) {
//		mSQLHelp = new ChannelNewsSQLHelper(mContext);
//		mSQLiteDatabase = mSQLHelp.getReadableDatabase();
//		String sql = "delete   from " + table + " order by " + order
//				+ " limit " + start + " offset " + 50;// 跳过50条删除后面的条数
//		String string = "delete from " + table
//				+ "   where  (select sourceUrl from  " + table
//				+ "  order by sourceUrl desc limit " + 83 + "  offset 50 )";
//		mSQLiteDatabase.execSQL(string);
//	}
}
