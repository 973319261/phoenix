package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.WithdrawSettingVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 钱包提现金额适配器
 */
public class RvWithdrawSettingAdapter extends RecyclerView.Adapter<RvWithdrawSettingAdapter.ViewHolder>{
    private Activity myActivity;
    private List<WithdrawSettingVo> list;
    private OnItemClickListener onItemClickListener;
    public RvWithdrawSettingAdapter(Activity activity){
        this.myActivity=activity;
        list=new ArrayList<>();
    }

    public void setOnItemClickListener(RvWithdrawSettingAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_withdraw_setting_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final WithdrawSettingVo withdrawSettingVo=list.get(position);
        if (withdrawSettingVo!=null){
            holder.rbMoney.setText(String.format(Locale.getDefault(),"%.0f元",withdrawSettingVo.getWithdrawMoney()));//设置金额
            holder.itemView.setId(withdrawSettingVo.getWithdrawSettingId());//设置选择ID
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(withdrawSettingVo,position);
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
     * 添加数据
     * @param listAdd
     */
    public void addItem(List<WithdrawSettingVo> listAdd) {
        //如果是加载第一页，需要先清空数据列表
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbMoney;//金额
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rbMoney=itemView.findViewById(R.id.rb_withdraw_setting_money);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(WithdrawSettingVo withdrawSettingVo, int position);
    }
}
