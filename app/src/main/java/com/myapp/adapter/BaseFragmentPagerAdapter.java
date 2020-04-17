package com.myapp.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class BaseFragmentPagerAdapter
		extends FragmentPagerAdapter {

	private Fragment[] mPages;

	private String[] mTitles;

	public BaseFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public BaseFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] mTitles) {
		super(fm);
		this.mPages= fragments;
		this.mTitles= mTitles;
		this.notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		if (position < 0 || mPages == null || mPages.length <= position) {
			return null;
		}

		return mPages[position];
	}

	@Override
	public int getCount() {
	    if (mPages == null)  return 0;
	    else  return mPages.length;
	}

	@Override
    public CharSequence getPageTitle(int position) {
        if (position < 0 || mTitles == null || mTitles.length <= position) {
            return null;
        }
		return mTitles[position];
    }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
}
