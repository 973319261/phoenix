package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.PowerRecordVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 算力记录适配器
 */
public class RvPowerAdapter extends RecyclerView.Adapter<RvPowerAdapter.ViewHolder>{
    private Activity myActivity;
    private List<PowerRecordVo> list;
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
    public RvPowerAdapter(Activity activity){
        this.myActivity=activity;
        list=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_power_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PowerRecordVo powerVo=list.get(position);
        if (powerVo!=null){
            holder.tvDate.setText(sf.format(powerVo.getCreateTime()));
            holder.tvSourceName.setText(powerVo.getSourceTypeName().trim());
            holder.tvNum.setText(String.format(Locale.getDefault(),"+%d",powerVo.getProfitNum()));
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
    public void addItem(List<PowerRecordVo> listAdd, int loadPage) {
        if (loadPage==1){
            //如果是加载第一页，需要先清空数据列表
            this.list.clear();
            if (listAdd!=null){
                //添加数据
                this.list.addAll(listAdd);
            }
            //通知RecyclerView进行改变--整体
            notifyDataSetChanged();
        }else {
            //不是第一页的情况
            //添加数据
            int nowCount=this.list.size();
            if (listAdd!=null){
                this.list.addAll(listAdd);
            }
            //通知RecyclerView进行改变 -- 局部刷新
            notifyItemRangeChanged(nowCount,list.size());
        }

    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;//时间
        TextView tvSourceName;//来源名称
        TextView tvNum;//数量
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_power_date);
            tvSourceName=itemView.findViewById(R.id.tv_power_sourceName);
            tvNum=itemView.findViewById(R.id.tv_power_num);
        }
    }
}
