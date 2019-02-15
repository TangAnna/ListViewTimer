package com.example.tang.listviewtimer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mFragmentStatePagerAdapter;
    private String[] mTitleList = {"新任务", "进行中", "已完成"};
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mFragments = new ArrayList<>();

        mFragments.add(new MyFragment());
        mFragments.add(new MyFragment());
        mFragments.add(new MyFragment());

        mTabLayout.setupWithViewPager(mViewPager);

        mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.length;
            }

            @Override
            public Fragment getItem(int index)//直接创建fragment对象并返回
            {
                return mFragments.get(index);
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return mTitleList == null ? super.getPageTitle(position) : mTitleList[position];
            }
        };
        mViewPager.setAdapter(mFragmentStatePagerAdapter);

    }

    private void initView() {
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
    }


}
