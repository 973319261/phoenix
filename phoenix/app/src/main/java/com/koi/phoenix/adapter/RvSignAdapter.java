package com.koi.phoenix.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.SignVo;
import com.koi.phoenix.util.AnimationUtils;
import com.koi.phoenix.widget.MultiLineRadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 签到列表适配器
 */
public class RvSignAdapter extends RecyclerView.Adapter<RvSignAdapter.ViewHolder> {
    private Activity myActivity;//上下文
    private List<SignVo> list;//数据
    private SimpleDateFormat sf=new SimpleDateFormat("MM/dd");
    private Date nowDate=new Date();
    private OnItemClickListener onItemClickListener;
    public RvSignAdapter(Activity activity){
        this.myActivity=activity;
        this.list=new ArrayList<>();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_sign_list,parent,false);
        return new RvSignAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final SignVo signVo=list.get(position);
        if (signVo!=null){
            holder.tvDay.setText(sf.format(signVo.getSignDay()));
            holder.tvRewardNum.setText(String.format(Locale.getDefault(),"+%d能量",signVo.getRewardNum()));
            if (signVo.getSignDay().before(nowDate)){//今天日期之前
                if (sf.format(signVo.getSignDay()).equals(sf.format(nowDate))){//今天
                    holder.tvDay.setText("今天");
                }
                holder.itemView.setBackgroundResource(R.drawable.btn_border_fill);//设置深色背景
                holder.tvRewardNum.setTextColor(Color.WHITE);//白色字体
                if (signVo.getIsSign()==0 ){//未签到
                    AnimationUtils.scale(holder.itemView);//设置缩放动画
                    //设置item点击事件
                    if (onItemClickListener!=null){
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.click(signVo,position);
                            }
                        });
                    }
                }else {//已签到
                    holder.ivSignState.setBackgroundResource(R.drawable.ic_sign);
                }
            }
        }
    }
    /**
     * 对外方法，用于添加数据
     *
     * @param listAdd  要添加的数据
     */
    public void addItem(List<SignVo> listAdd) {
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }

    /**
     * 更新item
     * @param signVo
     * @param position
     */
    public void updateItem(SignVo signVo,int position){
        this.list.set(position,signVo);
        notifyItemChanged(position);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDay;//签到日期
        TextView tvRewardNum;//奖励说明
        ImageView ivSignState;//状态
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay=itemView.findViewById(R.id.tv_sign_day);
            tvRewardNum=itemView.findViewById(R.id.tv_sign_rewardNum);
            ivSignState=itemView.findViewById(R.id.iv_sign_state);
        }
    }
    public interface OnItemClickListener{
        void click(SignVo signVo,int position);
    }
}
