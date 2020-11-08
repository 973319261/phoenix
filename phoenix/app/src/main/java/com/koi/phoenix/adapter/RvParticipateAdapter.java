package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.IssueVo;

import java.util.ArrayList;
import java.util.List;

public class RvParticipateAdapter extends RecyclerView.Adapter<RvParticipateAdapter.ViewHolder>{
    private Activity myActivity;
    private List<IssueVo> list;
    private OnItemClickListener onItemClickListener;
    public RvParticipateAdapter(Activity activity){
        this.myActivity=activity;
        this.list=new ArrayList<>();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_participate_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final IssueVo issueVo=list.get(position);
        if (issueVo!=null){
            holder.tvIssueName.setText(issueVo.getIssueName().trim());//设置期号名称
            holder.tvPrizeName.setText(issueVo.getPrizeName().trim());//设置奖品名称
            if (issueVo.getIsWinning()==0){
                holder.tvIsWinning.setText("待开奖");//设置状态
                holder.tvIsWinning.setTextColor(myActivity.getResources().getColor(R.color.colorGray));
            }else if (issueVo.getIsWinning()==1){
                holder.tvIsWinning.setText("未中奖");//设置状态
                holder.tvIsWinning.setTextColor(myActivity.getResources().getColor(R.color.colorBlack));
            }else if (issueVo.getIsWinning()==2){
                holder.tvIsWinning.setText("已中奖");//设置状态
                holder.tvIsWinning.setTextColor(myActivity.getResources().getColor(R.color.colorPrimary));
            }
            if (onItemClickListener!=null){//设置item点击事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(issueVo);
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
     * @param loadPage 加载的页数
     */
    public void addItem(List<IssueVo> listAdd, int loadPage) {
        if (loadPage == 1) {
            //如果是加载第一页，需要先清空数据列表
            this.list.clear();
            if (listAdd != null) {
                //添加数据
                this.list.addAll(listAdd);
            }
            //通知RecyclerView进行改变--整体
            notifyDataSetChanged();
        } else {
            //不是第一页的情况
            //添加数据
            int nowCount = this.list.size();
            if (listAdd != null) {
                this.list.addAll(listAdd);
            }
            //通知RecyclerView进行改变 -- 局部刷新
            notifyItemRangeChanged(nowCount, list.size());
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIssueName;//期号名称
        TextView tvPrizeName;//奖品名称
        TextView tvIsWinning;//中奖状态
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIssueName=itemView.findViewById(R.id.tv_issue_name);
            tvPrizeName=itemView.findViewById(R.id.tv_prize_name);
            tvIsWinning=itemView.findViewById(R.id.tv_is_winning);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(IssueVo issueVo);
    }
}
