package com.koi.phoenix.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.TeamVo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 团队信息列表适配器
 */
public class RvTeamAdapter extends RecyclerView.Adapter<RvTeamAdapter.ViewHolder> {
    private Activity myActivity;//上下文
    private List<TeamVo> list;//数据
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public RvTeamAdapter(Activity context, List<TeamVo> list){
        this.myActivity= context;
        this.list=list;
    }
    //创建ViewHolder --设置子项的布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_team_list,parent,false);
        return new RvTeamAdapter.ViewHolder(view);
    }
    //绑定每一项的数据
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TeamVo teamVo=list.get(position);
        if (teamVo!=null){
            holder.tvNickName.setText(teamVo.getNickName().trim());//昵称
            holder.tvCreateTime.setText(sf.format(teamVo.getCreateTime()));//创建日期
            holder.tvLevel.setText(String.format(Locale.getDefault(),"%d级",teamVo.getLevel()));//等级
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
    public void addItem(List<TeamVo> listAdd) {
        this.list.clear();
        if (listAdd!=null){
            //添加数据
            this.list.addAll(listAdd);
        }
        //通知RecyclerView进行改变--整体
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNickName;//昵称
        TextView tvCreateTime;//创建时间
        TextView tvLevel;//等级
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNickName=itemView.findViewById(R.id.tv_team_nickName);
            tvCreateTime=itemView.findViewById(R.id.tv_team_createTime);
            tvLevel=itemView.findViewById(R.id.tv_team_level);
        }
    }
}
