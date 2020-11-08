package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.BetVo;
import com.koi.phoenix.util.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑换码适配器
 */
public class RvRedeemCodeAdapter extends RecyclerView.Adapter<RvRedeemCodeAdapter.ViewHolder>{
    private Activity myActivity;
    List<String> list;
    public RvRedeemCodeAdapter(Activity activity){
        this.myActivity=activity;
        this.list=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_redeem_code_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str=list.get(position);
        if (Tools.isNotNull(str)){
            holder.tvRedeemCode.setText(str);//设置兑换码
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
    public void addItem(List<String> listAdd) {
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRedeemCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRedeemCode=itemView.findViewById(R.id.tv_redeemCode);
        }
    }
}
