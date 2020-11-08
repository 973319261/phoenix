package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.BetVo;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RvBetAdapter extends RecyclerView.Adapter<RvBetAdapter.ViewHolder>{
    private Activity myActivity;
    private List<BetVo> list;
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换

    public RvBetAdapter(Activity activity){
        this.myActivity=activity;
        this.list=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_bet_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BetVo betVo=list.get(position);
        if (betVo!=null){
            String strPicture = betVo.getAvatarUrl().trim();
            if (Tools.isNotNull(strPicture)) {
                String pictureUrl = ServiceUrls.getUserMethodUrl("getUserAvatar") + "?pictureName=" + strPicture;
                //使用Glide加载图片
                Glide.with(myActivity)
                        .load(pictureUrl)
                        .apply(headerRO.error(R.drawable.ic_avatar_error))
                        .into(holder.ivAvatar);
            }
            holder.tvNickName.setText(betVo.getNickName().trim());//设置昵称
            holder.tvBetTime.setText(sf.format(betVo.getBetTime()));//设置投注时间
            holder.tvBetNum.setText(String.format(Locale.getDefault(),"%d注",betVo.getBetNum()));//设置投注数量
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
    public void addItem(List<BetVo> listAdd, int loadPage) {
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
        ImageView ivAvatar;
        TextView tvNickName;
        TextView tvBetTime;
        TextView tvBetNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar=itemView.findViewById(R.id.iv_user_avatar);
            tvNickName=itemView.findViewById(R.id.tv_user_nickName);
            tvBetTime=itemView.findViewById(R.id.tv_bet_time);
            tvBetNum=itemView.findViewById(R.id.tv_bet_num);
        }
    }
}
