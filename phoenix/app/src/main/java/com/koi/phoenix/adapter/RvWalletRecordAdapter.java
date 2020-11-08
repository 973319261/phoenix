package com.koi.phoenix.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.MoneyRecordVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 钱包记录适配器
 */
public class RvWalletRecordAdapter extends RecyclerView.Adapter<RvWalletRecordAdapter.ViewHolder>{
    private Activity myActivity;//上下文
    private List<MoneyRecordVo> list;//数据
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public RvWalletRecordAdapter(Activity activity){
        this.myActivity=activity;
        this.list=new ArrayList<>();

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_wallet_record_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoneyRecordVo moneyRecordVo=list.get(position);
        if (moneyRecordVo!=null){
            holder.tvName.setText(moneyRecordVo.getMoneySoureName().trim());//零钱来源名称
            holder.tvDate.setText(sf.format(moneyRecordVo.getCreateTime()));//创建时间
            holder.tvMoney.setText(String.format(Locale.getDefault(),"%.2f元",moneyRecordVo.getMoney()));//金额
            if (moneyRecordVo.getMoneyType()==1){//钱包支出（提现）类型
                holder.tvState.setVisibility(View.VISIBLE);//显示状态
                if (moneyRecordVo.getMoneyStateId()==1){//处理中
                    holder.tvState.setTextColor(Color.GRAY);//灰色字体
                    holder.tvState.setText(moneyRecordVo.getMoneyStateName().trim());//状态
                }
            }else {//钱包入账类型
                holder.tvState.setVisibility(View.GONE);//隐藏状态
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
    public void addItem(List<MoneyRecordVo> listAdd, int loadPage) {
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
        TextView tvName;//零钱来源名称
        TextView tvDate;//创建时间
        TextView tvMoney;//金额
        TextView tvState;//状态
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_wallet_record_name);
            tvDate=itemView.findViewById(R.id.tv_wallet_record_date);
            tvMoney=itemView.findViewById(R.id.tv_wallet_record_money);
            tvState=itemView.findViewById(R.id.tv_wallet_record_state);
        }
    }
}
