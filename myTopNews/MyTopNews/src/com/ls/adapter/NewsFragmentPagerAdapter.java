package com.ls.adapter;

import java.util.List;

import com.ls.fragment.NewsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<NewsFragment> fragments;
	private FragmentManager fManager;

	public NewsFragmentPagerAdapter(FragmentManager fm,
			List<NewsFragment> fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
		this.fManager = fm;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
