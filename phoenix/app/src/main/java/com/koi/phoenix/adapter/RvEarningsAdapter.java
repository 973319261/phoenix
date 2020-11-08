package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.IncomeRecord;
import com.koi.phoenix.bean.TeamVo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 收益列表适配器
 */
public class RvEarningsAdapter extends RecyclerView.Adapter<RvEarningsAdapter.ViewHolder> {
    private Activity myActivity;
    private List<IncomeRecord> list;//数据
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
    public RvEarningsAdapter(Activity context, List<IncomeRecord> list){
        this.myActivity= context;
        this.list=list;
    }
    //创建ViewHolder --设置子项的布局
    @NonNull
    @Override
    public RvEarningsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_earnings_list,parent,false);
        return new RvEarningsAdapter.ViewHolder(view);
    }
    //绑定每一项的数据
    @Override
    public void onBindViewHolder(@NonNull RvEarningsAdapter.ViewHolder holder, int position) {
        IncomeRecord incomeRecord=list.get(position);
        if (incomeRecord!=null){
            holder.tvDate.setText(sf.format(incomeRecord.getIncomeDay()));
            holder.tvTuDi.setText(String.format(Locale.getDefault(),"%.2f",incomeRecord.getIncomeTudi()));
            holder.tvTuSun.setText(String.format(Locale.getDefault(),"%.2f",incomeRecord.getIncomeTusun()));
            holder.tvAll.setText(String.format(Locale.getDefault(),"%.2f",incomeRecord.getIncomeAll()));
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
    public void addItem(List<IncomeRecord> listAdd, int loadPage) {
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
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;//日期
        TextView tvTuDi;//徒弟收益
        TextView tvTuSun;//徒孙收益
        TextView tvAll;//总计
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tv_earnings_date);
            tvTuDi=itemView.findViewById(R.id.tv_earnings_tudi);
            tvTuSun=itemView.findViewById(R.id.tv_earnings_tusun);
            tvAll=itemView.findViewById(R.id.tv_earnings_all);
        }
    }
}
