package com.koi.phoenix.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

/**
 * 动画工具类
 */
public class AnimationUtils {
    /**
     * 缩放动画
     * @param view
     */
    public static void scale(View view){
        AnimatorSet animatorSetsuofang = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 1.05f,1);//后几个参数是放大的倍数
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 1.05f,1);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);//永久循环
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        animatorSetsuofang.setDuration(3000);//时间
        animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetsuofang.start();//开始
    }
    /**
     * 设置需要滚动的view(数字)
     * @param tickerViews
     */
    public static void tickerRollView(TickerView[] tickerViews){
        for (TickerView tickerView:tickerViews){
            tickerView.setCharacterLists(TickerUtils.provideNumberList());
            tickerView.setAnimationDuration(500);
            tickerView.setAnimationInterpolator(new OvershootInterpolator());
            tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);
        }

    }
}
