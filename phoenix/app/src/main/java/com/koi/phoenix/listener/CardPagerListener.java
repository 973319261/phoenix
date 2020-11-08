package com.koi.phoenix.listener;

import android.content.Context;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

/**
     * @tips  : viewPager滑动时的监听器
     */
    public class CardPagerListener implements ViewPager.OnPageChangeListener {
        private Context context;
        public CardPagerListener(Context context){
            this.context=context;
        }
        /*
         * （非 Javadoc）
         * 
         * @see android.support.v4.view.ViewPager.OnPageChangeListener#
         * onPageScrollStateChanged(int) 此方法是在状态改变的时候调用，其中arg0这个参数
         * 有三种状态（0，1，2）。
         * 
         * arg0 == 1的时辰默示正在滑动，
         * arg0 == 2的时辰默示滑动完毕了，
         * arg0 == 0的时辰默示什么都没做。
         * 
         * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0），演示如下：
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO 自动生成的方法存根

        }

        /*
         * （非 Javadoc）
         * 
         * @see
         * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
         * 
         * arg0 :当前页面，及你点击滑动的页面
         * arg1 :当前页面偏移的百分比
         * arg2 :当前页面偏移的像素位置 
         * 
         * (int, float, int) pagerNum:第几个界面（从0开始计数） offset:偏移量，计算页面滑动的距离
         */
        @Override
        public void onPageScrolled(int pagerNum, float arg1, int offset) {

        }

        /*
         * （非 Javadoc）
         * 
         * @see
         * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
         * (int) 判断当前是哪个view
         */
        @Override
        public void onPageSelected(int position) {
            // TODO 自动生成的方法存根
            Toast.makeText(context,String.valueOf(position),Toast.LENGTH_LONG).show();
        }

    }