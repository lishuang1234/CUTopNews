package com.ls.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.ls.activity.SettingsActivity;
import com.ls.mytopnews.R;
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
	private TextView loginUserName;
	private String loginPlatName;
	private ShareSDKHelper sdkHelper;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;

	public DrawerView(Activity activity) {
		
		this.activity = activity;
		sdkHelper = new ShareSDKHelper(activity);
		loginPlatName = sdkHelper.checkInti();
		options = Options.getListOptions();
	}
	public SlidingMenu initSlidingMenu() {
		localSlidingMenu = new SlidingMenu(activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT);// �������һ��˵�
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
		// localSlidingMenu.setSecondaryMenu(R.layout.profile_drawer_right);
		// localSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
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
		if (loginPlatName != null) {
			loginQzone.setVisibility(View.GONE);
			loginWeiBo.setVisibility(View.GONE);
			loginUserPhoto.setVisibility(View.VISIBLE);
			getUserInfor();
		} else {
			loginQzone.setOnClickListener(this);
			loginWeiBo.setOnClickListener(this);
		}
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
			activity.startActivity(new Intent(activity, SettingsActivity.class));
		default:
			break;
		}
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

}
