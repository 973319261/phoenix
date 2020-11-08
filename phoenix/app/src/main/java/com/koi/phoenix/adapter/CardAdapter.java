package com.koi.phoenix.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;


import java.util.List;

/**
 * 邀请卡片适配器
 */
public class CardAdapter extends PagerAdapter {
    private List<Bitmap> list;
    private Context context;
    private LayoutInflater inflater;
 
    public CardAdapter(Context context, List<Bitmap> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        iv.setImageBitmap(list.get(position));
        container.addView(iv);
        return iv;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}