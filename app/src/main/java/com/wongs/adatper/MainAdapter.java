package com.wongs.adatper;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ryze.liu on 7/14/2016.
 */
public class MainAdapter extends BaseAdapter {

    private List<Fragment> fragments;
    private Context mContext;
    public MainAdapter(List<Fragment> list,Context context){

        fragments =list;
        mContext = context;

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
