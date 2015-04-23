package com.ls.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ls.adapter.NewsFragmentPagerAdapter;
import com.ls.app.AppApplication;
import com.ls.bean.ChannelItem;
import com.ls.bean.ChannelManage;
import com.ls.fragment.NewsFragment;
import com.ls.mytopnews.R;
import com.ls.tool.BaseTool;
import com.ls.tool.Constants;
import com.ls.view.ColumnHorizontalScrollView;
import com.ls.view.DrawerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener,
        OnPageChangeListener {
    private int mScreenWidth;
    private int mItemWidth;
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private LinearLayout mRadioGroup_contentLayout;
    private LinearLayout ll_more_columnsLayout;
    private RelativeLayout rl_columnLayout;
    private ImageView button_more_columnsView;
    private ViewPager mViewPager;
    private ImageView shade_leftImageView;
    private ImageView shade_rightImageView;
    private ImageView top_headImageView;
    private TextView top_weather;
    private ImageView top_refreshImageView;
    private int columnSelectIndex;
    private List<NewsFragment> fragments;
    private SlidingMenu mSlidingMenu;
    // private WeatherEntity wEntity;
    // private ShareSDKHelper helper;
    private NewsFragmentPagerAdapter newsFragmentPagerAdapter;
    private List<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    // protected ImageLoader imageLoader = ImageLoader.getInstance();
    // DisplayImageOptions options;
    private UpdateListView updateListView = new UpdateListView();

    // private Handler handler = new Handler() {
    //
    // @Override
    // public void handleMessage(Message msg) {
    // // TODO Auto-generated method stub
    // super.handleMessage(msg);
    // if (msg.what == 1) {
    // top_weather.setText(wEntity.getCity() + "/"
    // + wEntity.getTemper().split("：")[2]);
    // }
    // }
    //
    // };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mScreenWidth = BaseTool.getWindowWidth(this);
        mItemWidth = mScreenWidth / 7;
        // helper = new ShareSDKHelper(this);
        // options = Options.getListOptions();
        // new GetWeather().start();
        initView();
        initSlidingMenu();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.UPDATE_LISTVIEW);
        filter.addAction(Constants.UPDATE_WEATHER);
        registerReceiver(updateListView, filter);

    }

    private void initSlidingMenu() {
        // TODO Auto-generated method stub
        mSlidingMenu = new DrawerView(MainActivity.this).initSlidingMenu();

    }

    /**
     * 初始化ViewPager的填充Fragment
     */
    private void initFragments() {
        // TODO Auto-generated method stub
        fragments = new ArrayList<NewsFragment>();
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            Bundle dataBundle = new Bundle();
            dataBundle.putString("text", userChannelList.get(i).getName());
            dataBundle.putInt("order_id", i);
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(dataBundle);

            fragments.add(newsFragment);

        }
        newsFragmentPagerAdapter = new NewsFragmentPagerAdapter(
                getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(newsFragmentPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(updateListView);
        super.onDestroy();
    }

    private void initTabColumns() {
        // TODO Auto-generated method stub
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            TextView itemTextView = new TextView(this);
            itemTextView.setTextAppearance(getApplicationContext(),
                    R.style.top_categry_scroll_view_text);
            itemTextView.setBackgroundResource(R.drawable.radio_button_bg);// ���ñ�����select��press״̬
            itemTextView.setGravity(Gravity.CENTER);
            itemTextView.setPadding(6, 5, 6, 5);
            itemTextView.setId(i);
            itemTextView.setText(userChannelList.get(i).getName());
            itemTextView.setTextColor(getResources().getColorStateList(
                    R.drawable.top_category_scroll_text_color));// 注意此处获取ColorStateList
            if (columnSelectIndex == i) {
                itemTextView.setSelected(true);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.topMargin = 7;
            params.bottomMargin = 7;
            mRadioGroup_contentLayout.addView(itemTextView, i, params);
            itemTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    // 控制ViewPager
                    mViewPager.setCurrentItem(v.getId(), true);
                    columnSelectIndex = v.getId();
                    initTab();
                }
            });
        }
    }

    private void initView() {
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHori);
        mRadioGroup_contentLayout = (LinearLayout) findViewById(R.id.mRadioGroup_Content);
        ll_more_columnsLayout = (LinearLayout) findViewById(R.id.ll_more_columns);
        rl_columnLayout = (RelativeLayout) findViewById(R.id.rl_column);
        button_more_columnsView = (ImageView) findViewById(R.id.button_more_columns);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        shade_leftImageView = (ImageView) findViewById(R.id.shade_left);
        shade_rightImageView = (ImageView) findViewById(R.id.shade_right);
        top_headImageView = (ImageView) findViewById(R.id.top_head);
        top_weather = (TextView) findViewById(R.id.top_weather);
        top_refreshImageView = (ImageView) findViewById(R.id.top_refersh);
        top_headImageView.setOnClickListener(this);
        top_weather.setOnClickListener(this);
        button_more_columnsView.setOnClickListener(this);
        // top_refreshImageView.setOnClickListener(this);
        mColumnHorizontalScrollView.setParam(this, mRadioGroup_contentLayout,
                shade_leftImageView, shade_leftImageView,
                ll_more_columnsLayout, rl_columnLayout);
        // if (helper.checkInti() != null) {// 已授权
        // imageLoader.displayImage(
        // helper.getInitPlat().getDb().getUserIcon(),
        // top_headImageView, options);
        // }
        setChangeView();
    }

    /**
     * 初始化频道项目数据，初始化频道Tab，初始化新闻现实Fragment
     */
    private void setChangeView() {
        // TODO Auto-generated method stub
        initColumnData();
        initTabColumns();
        initFragments();
    }

    private void initColumnData() {
        // TODO Auto-generated method stub
        userChannelList = (ChannelManage.getManage(AppApplication.getApp()
                .getSQLHelper())).getUserChannel();// ��ȡƵ���б�
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.top_weather:// ����more��ť
                if (mSlidingMenu.isSecondaryMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {
                    mSlidingMenu.showSecondaryMenu();
                }

                break;
            case R.id.top_head:// ����head
                if (mSlidingMenu.isMenuShowing()) {
                    mSlidingMenu.showContent();
                } else {
                    mSlidingMenu.showMenu();
                }

                break;
            case R.id.button_more_columns:// ����໬��Ӱ�ť

                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub

        columnSelectIndex = arg0;
        initTab();
        mViewPager.setCurrentItem(arg0, true);
    }

    /**
     * 设置TextView选中状态
     */
    private void initTab() {
        // TODO Auto-generated method stub
        for (int i = 0; i < mRadioGroup_contentLayout.getChildCount(); i++) {
            if (columnSelectIndex == i) {
                mRadioGroup_contentLayout.getChildAt(i).setSelected(true);

            } else
                mRadioGroup_contentLayout.getChildAt(i).setSelected(false);
        }
    }

    class UpdateListView extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(Constants.UPDATE_LISTVIEW)) {// 、更新
                NewsFragment mFragment = fragments.get(intent.getIntExtra(
                        "fragment_position", 0));
                // ((NewsFragment) newsFragmentPagerAdapter.getItem(intent
                // .getIntExtra("fragment_position", 0))).updateState();
                mFragment.updateState();
                System.out.println("MianActivity:::::更新啦 fragment position:: "
                        + intent.getIntExtra("fragment_position", 0));
            } else if (intent.getAction().equals(Constants.UPDATE_WEATHER)) {
                top_weather.setText(intent.getStringExtra("weather"));
            }
        }

    }
}
