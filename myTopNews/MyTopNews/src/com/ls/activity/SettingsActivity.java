package com.ls.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ls.mytopnews.R;
import com.ls.tool.Constants;

public class SettingsActivity extends BaseActivity implements OnClickListener {
	private TextView title;
	private CheckBox fontSizeBox;
	private CheckBox loadImageMadeBox;// 省流量
	private CheckBox notify;// 新闻推送
	private CheckBox clear;// 自动清理缓存
	private CheckBox shareWhenFavorBox;// 收藏时转发
	private SharedPreferences sPreferences;
	private SharedPreferences.Editor editor;
	private View back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		initView();
		initData();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title);
		back = findViewById(R.id.back);
		fontSizeBox = (CheckBox) findViewById(R.id.checkbox_font_size);
		loadImageMadeBox = (CheckBox) findViewById(R.id.checkbox_load_image_mode);
		notify = (CheckBox) findViewById(R.id.checkbox_notify);
		clear = (CheckBox) findViewById(R.id.checkbox_clear);
		shareWhenFavorBox = (CheckBox) findViewById(R.id.checkbox_share_when_favor);

		((LinearLayout) findViewById(R.id.setting_font_size))
				.setOnClickListener(this);// 设置字体
		((LinearLayout) findViewById(R.id.clear)).setOnClickListener(this);// 清空缓存
		((LinearLayout) findViewById(R.id.update)).setOnClickListener(this);// 版本更新
		((LinearLayout) findViewById(R.id.setting_load_image))
				.setOnClickListener(this);// 省流量
		((LinearLayout) findViewById(R.id.notify)).setOnClickListener(this);// 推送
		((LinearLayout) findViewById(R.id.share_when_favor))// 收藏转发
				.setOnClickListener(this);
		back.setOnClickListener(this);

	}

	private void initData() {
		title.setText("设置");
		sPreferences = getSharedPreferences(Constants.SETTING,
				Context.MODE_PRIVATE);
		editor = sPreferences.edit();
		// /设置CheckBox初始状态
		fontSizeBox.setChecked(sPreferences.getBoolean(
				Constants.SETTING_FONT_SIZE, false));
		loadImageMadeBox.setChecked(sPreferences.getBoolean(
				Constants.SETTING_LOAD_IMAGE, false));
		notify.setChecked(sPreferences.getBoolean(Constants.SETTING_NOTIFY,
				false));
		shareWhenFavorBox.setChecked(sPreferences.getBoolean(
				Constants.SETTING_SHARE_WHEN_FAVOR, false));
		clear.setChecked(sPreferences
				.getBoolean(Constants.SETTING_CLEAR, false));
	}

	// @Override
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	// // overridePendingTransition(R.anim.slide_in_left,
	// R.anim.slide_out_right);
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_font_size:// 字体 已设置
			setFontSize();
			break;
		case R.id.setting_load_image:// 已设置
			setLoadImage();
			break;
		case R.id.clear:// 清空缓存
			setClear();
			break;
		case R.id.notify:
			setNotify();
			break;
		case R.id.share_when_favor:
			setShareWhenFavor();
			break;
		case R.id.update:

			break;
		case R.id.back:
			this.finish();
			break;

		default:

			break;
		}
		editor.commit();
	}

	private void setClear() {
		// TODO Auto-generated method stub
		clear.setChecked(!clear.isChecked());
		editor.putBoolean(Constants.SETTING_CLEAR, clear.isChecked());
	}

	private void setShareWhenFavor() {
		// TODO Auto-generated method stub
		shareWhenFavorBox.setChecked(!shareWhenFavorBox.isChecked());
		editor.putBoolean(Constants.SETTING_SHARE_WHEN_FAVOR,
				shareWhenFavorBox.isChecked());
	}

	private void setNotify() {
		// TODO Auto-generated method stub
		notify.setChecked(!notify.isChecked());
		editor.putBoolean(Constants.SETTING_NOTIFY, notify.isChecked());
	}

	private void setLoadImage() {
		// TODO Auto-generated method stub
		loadImageMadeBox.setChecked(!loadImageMadeBox.isChecked());
		editor.putBoolean(Constants.SETTING_LOAD_IMAGE,
				loadImageMadeBox.isChecked());
	}

	private void setFontSize() {
		fontSizeBox.setChecked(!fontSizeBox.isChecked());
		editor.putBoolean(Constants.SETTING_FONT_SIZE, fontSizeBox.isChecked());
		// System.out.println("fontSizeBox.isChecked(): "
		// + fontSizeBox.isChecked());
	}
}
