package com.koi.phoenix.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.TaskVo;
import com.koi.phoenix.util.AnimationUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.Tools;
import com.robinhood.ticker.TickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 任务列表适配器
 */
public class RvWorkAdapter extends RecyclerView.Adapter<RvWorkAdapter.ViewHolder>{
    private Activity myActivity;//上下文
    private List<TaskVo> list;//数据
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private OnItemClickListener onItemClickListener;
    public RvWorkAdapter(Activity context){
        this.myActivity= context;
        this.list=new ArrayList<>();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_work_list,parent,false);
        return new RvWorkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TaskVo taskVo=list.get(position);
        TickerView[] tickerViews=new TickerView[]{holder.tvRewardState,holder.tvProgressNum};
        AnimationUtils.tickerRollView(tickerViews);//设置滚动View()数字
        if (taskVo!=null){
           String strPicture = taskVo.getTaskIcon().trim();
            if (Tools.isNotNull(strPicture)) {
                String pictureUrl = ServiceUrls.getTaskMethodUrl("getIcon") + "?pictureName=" + strPicture;
                //使用Glide加载图片
                Glide.with(myActivity)
                        .load(pictureUrl)
                        .into(holder.ivIcon);
            }
            holder.tvTaskName.setText(taskVo.getTaskName().trim());//设置任务名称
            if (taskVo.getTaskId()==3){ //在线分钟
                holder.tvRewardState.setText("已在线"+taskVo.getProgressNum()+"分钟");//设置任务说明
            }else if (taskVo.getTaskId()==2 || taskVo.getTaskId()==4){//邀请好友或者观看视频
                holder.tvBracket.setText(" (");
                holder.tvProgressNum.setText(String.format(Locale.getDefault(),"%d",taskVo.getProgressNum()));
                holder.tvTaskTotal.setText(String.format(Locale.getDefault(),"/%d)",taskVo.getTaskTotal()));
                holder.tvRewardState.setText(taskVo.getRewardState().trim());//设置任务说明
            }else {
                holder.tvRewardState.setText(taskVo.getRewardState().trim());//设置任务说明
            }
            if (taskVo.getHasDo()==0){//未完成
                holder.tvRewardNum.setText(String.format(Locale.getDefault(),"+%d",taskVo.getRewardNum()));
                holder.tvRewardNum.setVisibility(View.VISIBLE);//显示
                holder.tvRewardType.setTextColor(Color.BLACK);//黑色字体
                if (taskVo.getRewardType()==0){//判断奖励类型
                    holder.tvRewardType.setText(" 算力");
                }else {
                    holder.tvRewardType.setText(" hdc");
                }
            }else {
                holder.tvRewardType.setText("已完成");
                holder.tvRewardType.setTextColor(Color.GRAY);//灰色字体
                holder.tvRewardNum.setVisibility(View.GONE);//隐藏
            }
            if (list.size()-1==position){//最后一项
                holder.itemView.setBackground(null);//去掉底部边框
            }
            //绑定点击事件
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(taskVo,holder.tvRewardNum,holder.tvRewardType);
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
     * 对外方法，用于添加数据
     *
     * @param listAdd  要添加的数据
     */
    public void addItem(List<TaskVo> listAdd) {
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;//图标
        TextView tvTaskName;//任务名称
        TextView tvBracket;//括号
        TickerView tvProgressNum;//任务进度数
        TextView tvTaskTotal;//任务总数
        TickerView tvRewardState;//奖励说明
        TextView tvRewardNum;//奖励数量
        TextView tvRewardType;//奖励类型
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon=itemView.findViewById(R.id.iv_work_icon);
            tvTaskName=itemView.findViewById(R.id.tv_work_taskName);
            tvBracket=itemView.findViewById(R.id.tv_work_left_bracket);
            tvProgressNum=itemView.findViewById(R.id.tv_work_progressNum);
            tvTaskTotal=itemView.findViewById(R.id.tv_work_taskTotal);
            tvRewardState=itemView.findViewById(R.id.tv_work_rewardState);
            tvRewardNum=itemView.findViewById(R.id.tv_work_rewardNum);
            tvRewardType=itemView.findViewById(R.id.tv_work_rewardType);
        }
    }
    //任务列表点击接口
    public interface OnItemClickListener{
        void onItemClick(TaskVo taskVo,TextView tvRewardNum,TextView tvPower);
    }
}
