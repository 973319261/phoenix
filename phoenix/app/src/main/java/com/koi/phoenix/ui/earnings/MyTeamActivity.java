package com.koi.phoenix.ui.earnings;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.koi.phoenix.R;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

/**
 * 我的团队
 */
public class MyTeamActivity extends AppCompatActivity {
    Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private String[] title={"徒弟","徒孙","待激活"};//标题
    private Integer[] state={0,1,2};//状态
    private TabLayout tabLayout;//标签
    private ViewPager2 viewPager;//内容

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myActivity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_myteam);
        tabLayout = findViewById(R.id.tl_myteam_title);
        viewPager = findViewById(R.id.vp_myteam_container);
        MyFragmentStateAdapter myFragmentStateAdapter=new MyFragmentStateAdapter((FragmentActivity) myActivity);
        viewPager.setAdapter(myFragmentStateAdapter);//设置适配器
        //tabLayout与viewPager的关联
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(title[position]);
            }
        }).attach();
        int currentItem=getIntent().getIntExtra("currentItem",0);//当前页
        viewPager.setCurrentItem(currentItem);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("邀请记录", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {//返回
                finish();//关闭当前页面
            }
            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
    }

    class MyFragmentStateAdapter extends FragmentStateAdapter{
        //存放Fragment
        Fragment[]fragments;

        public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity){
            super(fragmentActivity);
            fragments=new Fragment[title.length];
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (fragments[position]==null){
                Bundle bundle=new Bundle();
                bundle.putInt("state",state[position]);
                TeamListFragment teamListFragment=new TeamListFragment();//创建Fragment
                teamListFragment.setArguments(bundle);//设置参数
                fragments[position]=teamListFragment;
            }
            return fragments[position];
        }

        @Override
        public int getItemCount() {
            return fragments.length;
        }
    }
}
