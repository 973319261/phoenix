package com.koi.phoenix.adapter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.IssueVo;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.Tools;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 期号适配器
 */
public class RvIssueAdapter extends RecyclerView.Adapter<RvIssueAdapter.ViewHolder>{
    private Activity myActivity;
    private List<IssueVo> list;
    private TickerView tvIssueTitle;//标题
    private OnItemClickListener onItemClickListener;
    public RvIssueAdapter(Activity activity, TickerView tvIssueTitle){
        this.list=new ArrayList<>();
        this.myActivity=activity;
        this.tvIssueTitle=tvIssueTitle;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_issue_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final IssueVo issueVo=list.get(position);
        if (issueVo!=null){
            int num=list.size()-getHasLotteryCount();//剩余开奖数
            if (num>0){
                tvIssueTitle.setText(String.format(Locale.getDefault(),"今天已开奖 %d 期，待开奖剩余 %d 期",getHasLotteryCount(),num));//标题
            }else {
                tvIssueTitle.setText("今天抽奖活动已结束，请明天再来吧☺");//标题
            }

            String strPicture = issueVo.getPrizeImg().trim();
            if (Tools.isNotNull(strPicture)) {
                String pictureUrl = ServiceUrls.getIssueMethodUrl("getPrizePicture") + "?pictureName=" + strPicture;
                //使用Glide加载图片
                Glide.with(myActivity)
                        .load(pictureUrl)
                        .into(holder.ivPrizeImg);
            }
            holder.pbIssue.setMax(issueVo.getAllBetNum());//可参与数量
            holder.tvIssueStateName.setText(issueVo.getIssueStateName().trim());//设置期号状态
            holder.tvPrizeName.setText(issueVo.getPrizeName().trim());//设置奖品名称
            holder.tvIssueName.setText("期号："+ issueVo.getIssueName().trim());//设置期号名称
            holder.tvYetBetNum.setText(String.format(Locale.getDefault(),"已参与 %d",issueVo.getYetBetNum()));//设置已参与数量
            if (issueVo.getIssueStateId()==1){//可参与
                holder.pbIssue.setProgress(issueVo.getYetBetNum());//已参与数量
                holder.itemView.setBackgroundColor(myActivity.getResources().getColor(R.color.colorWhite));//设置背景颜色
                holder.tvAllBetNum.setVisibility(View.VISIBLE);//显示
                holder.tvIssueStateName.setTextColor(myActivity.getResources().getColor(R.color.colorPrimary));//字体颜色
                holder.tvIssueStateName.setBackgroundResource(R.drawable.btn_border_radius_primary_tint);//字体背景颜色
                holder.tvAllBetNum.setText(String.format(Locale.getDefault(),"剩余 %d",issueVo.getAllBetNum()-issueVo.getYetBetNum()));//设置剩余数量
            }else {
                holder.pbIssue.setProgress(issueVo.getAllBetNum());//进度条
                holder.tvAllBetNum.setVisibility(View.GONE);//隐藏
                holder.itemView.setBackgroundColor(Color.parseColor("#2FCCCCCC"));//设置背景颜色
                holder.tvIssueStateName.setTextColor(Color.parseColor("#FF5722"));//字体颜色
                holder.tvIssueStateName.setBackgroundResource(R.drawable.btn_border_radius_other_tint);//字体背景颜色
            }
            //=======列表每一项点击 item点击事件
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断是否设置点击事件
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClick(issueVo);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     * 对外方法，用于分页添加数据
     *
     * @param listAdd  要添加的数据
     */
    public void addItem(List<IssueVo> listAdd) {
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPrizeImg;
        TextView tvIssueStateName;//期号状态
        TextView tvPrizeName;//奖品名称
        TextView tvIssueName;//期号名称
        ProgressBar pbIssue;//进度
        TextView tvYetBetNum;//已参与
        TextView tvAllBetNum;//剩余数
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPrizeImg=itemView.findViewById(R.id.iv_prize_img);
            tvIssueStateName=itemView.findViewById(R.id.tv_issue_state_name);
            tvPrizeName=itemView.findViewById(R.id.tv_prize_name);
            tvIssueName=itemView.findViewById(R.id.tv_issue_name);
            pbIssue=itemView.findViewById(R.id.pb_issue);
            tvYetBetNum=itemView.findViewById(R.id.tv_yet_bet_num);
            tvAllBetNum=itemView.findViewById(R.id.tv_all_bet_num);
        }
    }
    /**
     * 点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(IssueVo data);
    }

    /**
     * 获取已开奖的数量
     * @return
     */
    private int getHasLotteryCount(){
        int count=0;
        for (IssueVo issueVo:list) {
            if (issueVo.getIssueStateId()==2){//已开奖
                count++;
            }
        }
        return count;
    }
}
