package com.example.visadeveloper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.visadeveloper.fragments.MerchantIdFragment;
import com.example.visadeveloper.fragments.NFCFragments;
import com.example.visadeveloper.fragments.QRCodeFragment;

/**
 * Created by rahul on 16/03/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MerchantIdFragment tab1 = new MerchantIdFragment();
                return tab1;
            case 1:
                NFCFragments tab2 = new NFCFragments();
                return tab2;
            case 2:
                QRCodeFragment tab3 = new QRCodeFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
