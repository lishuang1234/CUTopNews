package com.ls.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.ls.activity.FavorActivity;
import com.ls.activity.SettingsActivity;
import com.ls.bean.WeatherEntity;
import com.ls.mytopnews.R;
import com.ls.tool.BaseTool;
import com.ls.tool.Constants;
import com.ls.tool.DateTools;
import com.ls.tool.Options;
import com.ls.tool.ShareSDKHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DrawerView implements OnClickListener {
	/**
	 * 
	 * 
	 */
	private final Activity activity;
	SlidingMenu localSlidingMenu;
	private SwitchButton night_mode_btnButton;
	private TextView night_mode_TextView;
	private ImageView loginWeiBo;
	private ImageView loginQzone;
	private ImageView loginUserPhoto;
	private View settingBtn;
	private View feedBack;
	private View loginout;
	private TextView loginUserName;
	private String loginPlatName;
	private ShareSDKHelper sdkHelper;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private WeatherEntity weatherEntity;

	private TextView todayShiDu;
	private TextView todayFengLi;
	private TextView todayWenDu;
	private TextView todayXingQi;
	private TextView todayUpdateTime;
	private TextView todayChuanYi;
	private TextView todayYunDong;
	private ImageView todayTuBiao;

	/** 未来几天 */
	private TextView[] futureDate = new TextView[4];// 日期
	private TextView[] futureWeather = new TextView[4];// 天气
	private TextView[] futureWendu = new TextView[4];// 温度
	private ImageView[] futureTubiao = new ImageView[4];// 图标

	private int[] futureDateId = new int[] {
			R.id.right_drawer_tx_future_1_date,
			R.id.right_drawer_tx_future_2_date,
			R.id.right_drawer_tx_future_3_date,
			R.id.right_drawer_tx_future_4_date };
	private int[] futureWeatherId = new int[] {
			R.id.right_drawer_tx_future_1_wether,
			R.id.right_drawer_tx_future_2_wether,
			R.id.right_drawer_tx_future_3_wether,
			R.id.right_drawer_tx_future_4_wether };
	private int[] futureWenduId = new int[] {
			R.id.right_drawer_tx_future_1_wendu,
			R.id.right_drawer_tx_future_2_wendu,
			R.id.right_drawer_tx_future_3_wendu,
			R.id.right_drawer_tx_future_4_wendu };
	private int[] futureTubioaId = new int[] {
			R.id.right_drawer_iv_future_1_tubiao,
			R.id.right_drawer_iv_future_2_tubiao,
			R.id.right_drawer_iv_future_3_tubiao,
			R.id.right_drawer_iv_future_4_tubiao

	};

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {

				System.out.println("显示天气啦！！");
				setWeather();
			}
		}
	};

	public DrawerView(Activity activity) {

		this.activity = activity;
		sdkHelper = new ShareSDKHelper(activity);
		loginPlatName = sdkHelper.checkInti();
		options = Options.getListOptions();
		new GetWeather().start();

	}

	protected void setWeather() {
		// TODO Auto-generated method stub
		String updateTime = weatherEntity.getUpdateTime().split(" ")[1];
		String qiWen;
		String fengLi;
		String shiDu;
		if (weatherEntity.getTemper() != null
				&& !weatherEntity.getTemper().equals("")) {
			qiWen = weatherEntity.getTemper().split("：")[2];
		} else {
			qiWen = "未知";
		}
		if (weatherEntity.getWind() != null
				&& !weatherEntity.getWind().equals("")) {
			fengLi = weatherEntity.getWind().split("：")[1];
		} else {
			fengLi = "未知";
		}
		if (weatherEntity.getMoisture() != null
				&& !weatherEntity.getMoisture().equals("")) {
			shiDu = weatherEntity.getMoisture().split("：")[1];
		} else {
			shiDu = "未知";
		}
		Intent intent = new Intent(Constants.UPDATE_WEATHER);
		intent.putExtra("weather", weatherEntity.getCity() + "/" + qiWen);
		activity.sendBroadcast(intent);
		String chuanYi = weatherEntity.getDressing();
		String yunDong = weatherEntity.getSports();
		System.out.println("得到的第一天字符串啊啊:"
				+ weatherEntity.getOneDay().split(" ")[1].split("，")[3]
						.substring(0, 1));
		String tuBiao = weatherEntity.getOneDay().split(" ")[1].split("，")[3]
				.substring(0, 1);
		// List<String> futureDate = new ArrayList<String>();
		// List<String> futureWeather = new ArrayList<String>();
		// List<String> futureTubiao = new ArrayList<String>();
		// List<String> futureWendu = new ArrayList<String>();
		// String future4 = weatherEntity.getFiveDay();
		// String future1 = weatherEntity.getTwoDay();
		// String future2 = weatherEntity.getThreeDay();
		// String future3 = weatherEntity.getFourDay();
		String[] futureArray = new String[] { weatherEntity.getTwoDay(),
				weatherEntity.getThreeDay(), weatherEntity.getFourDay(),
				weatherEntity.getFiveDay() };
		// futureDate.add(future1.split(" ")[0]);
		// futureDate.add(future2.split(" ")[0]);
		// futureDate.add(future3.split(" ")[0]);
		// futureDate.add(future4.split(" ")[0]);
		for (int i = 0; i < 4; i++) {
			futureDate[i].setText(futureArray[i].split(" ")[0]);
			futureWeather[i]
					.setText(futureArray[i].split(" ")[1].split("，")[0]);
			futureWendu[i].setText(futureArray[i].split(" ")[1].split("，")[1]);
			futureTubiao[i].setImageResource((BaseTool
					.getDrawerId((futureArray[i].split(" ")[1].split("，")[3]
							.substring(0, 1)))));

		}
		todayChuanYi.setText(chuanYi);
		todayFengLi.setText(fengLi);
		todayShiDu.setText(shiDu);
		todayTuBiao.setImageResource(BaseTool.getDrawerId(tuBiao));
		todayUpdateTime.setText(updateTime + "更新");
		todayWenDu.setText(qiWen);
		todayXingQi.setText(DateTools.getWeekOfDate(updateTime));
		todayYunDong.setText(yunDong);
		System.out.println("updateTime:::" + updateTime + "  qiweng:::" + qiWen
				+ "  fengli:::" + fengLi);

	}

	public SlidingMenu initSlidingMenu() {
		localSlidingMenu = new SlidingMenu(activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// �������һ��˵�
		localSlidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);// ����Ҫʹ�˵�������������Ļ�ķ�Χ
		// localSlidingMenu.setTouchModeBehind(SlidingMenu.SLIDING_CONTENT);//������������ȡ�����˵�����Ľ��㣬������ע�͵�
		localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);// ������ӰͼƬ�Ŀ��
		localSlidingMenu.setShadowDrawable(R.drawable.shadow);// ������ӰͼƬ
		localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// SlidingMenu����ʱ��ҳ����ʾ��ʣ����
		localSlidingMenu.setFadeDegree(0.35F);// SlidingMenu����ʱ�Ľ���̶�
		localSlidingMenu.attachToActivity(activity, SlidingMenu.RIGHT);// ʹSlidingMenu������Activity�ұ�
		// localSlidingMenu.setBehindWidthRes(R.dimen.left_drawer_avatar_size);//����SlidingMenu�˵��Ŀ��
		localSlidingMenu.setMenu(R.layout.left_drawer_fragment);// ����menu�Ĳ����ļ�
		// localSlidingMenu.toggle();//��̬�ж��Զ��رջ���SlidingMenu
		localSlidingMenu.setSecondaryMenu(R.layout.right_drawer_weather);
		localSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		localSlidingMenu
				.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
					public void onOpened() {

					}
				});
		localSlidingMenu.setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {
				// TODO Auto-generated method stub
			}
		});
		initView();
		return localSlidingMenu;
	}

	private void initView() {
		// TODO Auto-generated method stub
		night_mode_btnButton = (SwitchButton) localSlidingMenu
				.findViewById(R.id.night_mode_btn);
		night_mode_TextView = (TextView) localSlidingMenu
				.findViewById(R.id.night_mode_text);
		night_mode_btnButton
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							night_mode_TextView.setText(activity.getResources()
									.getString(R.string.action_night_mode));
						} else {
							night_mode_TextView.setText(activity.getResources()
									.getString(R.string.action_day_mode));
						}
					}
				});
		night_mode_btnButton.setChecked(false);
		if (night_mode_btnButton.isChecked()) {
			night_mode_TextView.setText(activity.getResources().getString(
					R.string.action_night_mode));
		} else {
			night_mode_TextView.setText(activity.getResources().getString(
					R.string.action_day_mode));
		}

		loginQzone = (ImageView) localSlidingMenu
				.findViewById(R.id.login_img_qzone);
		loginWeiBo = (ImageView) localSlidingMenu
				.findViewById(R.id.login_img_weibo);
		loginUserPhoto = (ImageView) localSlidingMenu
				.findViewById(R.id.login_img_userphoto);
		loginUserName = (TextView) localSlidingMenu
				.findViewById(R.id.login_tx_username);
		settingBtn = (View) localSlidingMenu.findViewById(R.id.setting_btn);
		settingBtn.setOnClickListener(this);
		feedBack = (View) localSlidingMenu.findViewById(R.id.feedback_btn);
		feedBack.setOnClickListener(this);
		((View) localSlidingMenu.findViewById(R.id.offline_btn))
				.setOnClickListener(this);
		((View) localSlidingMenu.findViewById(R.id.login_out))
				.setOnClickListener(this);// 退出登陆账号
		if (loginPlatName != null) {
			loginQzone.setVisibility(View.GONE);
			loginWeiBo.setVisibility(View.GONE);
			loginUserPhoto.setVisibility(View.VISIBLE);
			getUserInfor();
		} else {
			loginQzone.setOnClickListener(this);
			loginWeiBo.setOnClickListener(this);
		}
		initWeatherView();
	}

	private void initWeatherView() {
		// TODO Auto-generated method stub

		for (int i = 0; i < 4; i++) {
			futureDate[i] = (TextView) activity.findViewById(futureDateId[i]);
			futureWeather[i] = (TextView) activity
					.findViewById(futureWeatherId[i]);
			futureWendu[i] = (TextView) activity.findViewById(futureWenduId[i]);
			futureTubiao[i] = (ImageView) activity
					.findViewById(futureTubioaId[i]);
		}
		todayShiDu = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_shidu);
		todayFengLi = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_fengli);
		todayWenDu = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_wengdu);
		todayXingQi = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_xingqi);
		todayUpdateTime = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_update_time);
		todayChuanYi = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_chuanyi);
		todayYunDong = (TextView) activity
				.findViewById(R.id.right_drawer_tx_today_yundong);
		todayTuBiao = (ImageView) activity
				.findViewById(R.id.right_drawer_iv_today_tubiao);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_img_qzone:
			sdkHelper.login(QZone.NAME);
			break;
		case R.id.login_img_weibo:
			sdkHelper.login(SinaWeibo.NAME);
			break;
		case R.id.setting_btn:
			System.out.println("click!!! ");
			activity.startActivity(new Intent(activity, SettingsActivity.class));
			break;
		case R.id.offline_btn:// 收藏
			activity.startActivity(new Intent(activity, FavorActivity.class));
			break;
		case R.id.feedback_btn:// 反馈
			Toast.makeText(activity, "暂不支持哦！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.login_out:// 退出登陆账号
			sdkHelper.removeAccount();
			localSlidingMenu.showContent();
			imageLoader.clearDiskCache();
			refreshLoginview();
			break;
		default:
			break;
		}
	}

	private void refreshLoginview() {
		// TODO Auto-generated method stub
		loginQzone.setVisibility(View.VISIBLE);
		loginWeiBo.setVisibility(View.VISIBLE);
		loginUserPhoto.setVisibility(View.GONE);
		loginUserName.setText(R.string.left_drawer_no_login_tip);
		loginQzone.setOnClickListener(this);
		loginWeiBo.setOnClickListener(this);
	}

	/**
	 * 获取帐号信息
	 */
	private void getUserInfor() {
		Platform plat = sdkHelper.getInitPlat();
		String photoUrl = null;
		if (plat != null) {
			photoUrl = plat.getDb().getUserIcon();
			// System.out.println("photo_path----->" + photo_path);
			// bit = imageDownLoader.showCacheImagne(photo_path.replaceAll(
			// "[^\\w]", ""));
			// if (bit == null)
			// bit = imageDownLoader.downLoadImage(photo_path);
			// user_photo.setImageBitmap(bit);
			imageLoader.displayImage(photoUrl, loginUserPhoto, options);
			loginUserName.setText(plat.getDb().getUserName());
			// if (plat.getName().equals(QZone.NAME)) {
			// qqImageView.setVisibility(View.VISIBLE);
			// } else if (plat.getName().equals(SinaWeibo.NAME)) {
			// weiBoImageView.setVisibility(View.VISIBLE);
			// }
		}
	}

	class GetWeather extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			weatherEntity = Constants.getWeatherEntity("chongqing");
			if (weatherEntity != null)
				handler.sendEmptyMessage(1);
		}
	}

}
