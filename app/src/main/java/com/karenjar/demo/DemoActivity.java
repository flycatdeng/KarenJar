package com.karenjar.demo;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends Activity{
    private LocalActivityManager mLocalActivityManager;
    private Context mContext;
    private ViewPager mViewPager;
    private TextView mAndroidTextView;
    private TextView mOpenGLTextView;
    private TextView mVulkanTextView;
    private TextView mLargeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_demo);
        mLocalActivityManager = new LocalActivityManager(this, true);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        initViews();
    }

    private View getAtyDecorView(String id, Intent intent){
        return mLocalActivityManager.startActivity(id, intent).getDecorView();
    }

    private void initViews(){
        mViewPager = (ViewPager) findViewById(R.id.aty_main_vp_content);
        mAndroidTextView = (TextView) findViewById(R.id.aty_main_tab_item_android);
        mOpenGLTextView = (TextView) findViewById(R.id.aty_main_tab_item_opengl);
        mVulkanTextView = (TextView) findViewById(R.id.aty_main_tab_item_vulkan);
        mLargeTextView = (TextView) findViewById(R.id.aty_main_tab_item_large);
        mAndroidTextView.setOnClickListener(new OnTabClickListener());
        mOpenGLTextView.setOnClickListener(new OnTabClickListener());
        mVulkanTextView.setOnClickListener(new OnTabClickListener());
        mLargeTextView.setOnClickListener(new OnTabClickListener());
        ArrayList<View> list = new ArrayList<View>();
        Intent intent = new Intent(mContext, AndroidActivity.class);
        list.add(getAtyDecorView("AndroidActivity", intent));
        Intent intent2 = new Intent(mContext, OpenGLActivity.class);
        list.add(getAtyDecorView("OpenGLActivity", intent2));
        Intent intent3 = new Intent(mContext, VulkanActivity.class);
        list.add(getAtyDecorView("VulkanActivity", intent3));
        Intent intent4 = new Intent(mContext, LargeActivity.class);
        list.add(getAtyDecorView("LargeActivity", intent4));

        mViewPager.setAdapter(new MyPagerAdapter(list));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    //底部Tab点击时，切换选择颜色和显示的页面
    class OnTabClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.aty_main_tab_item_android:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.aty_main_tab_item_opengl:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.aty_main_tab_item_vulkan:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.aty_main_tab_item_large:
                    mViewPager.setCurrentItem(3);
                    break;
            }
            setTabTextsColor(v.getId());
        }
    }

    //设置为都未选中的颜色
    private void setTabTextsDefualtColor(){
        mAndroidTextView.setTextColor(getResources().getColor(R.color.normalText));
        mOpenGLTextView.setTextColor(getResources().getColor(R.color.normalText));
        mVulkanTextView.setTextColor(getResources().getColor(R.color.normalText));
        mLargeTextView.setTextColor(getResources().getColor(R.color.normalText));
    }

    /**
     * 依据底部Tab TextView的ID来设置颜色
     * @param selectedId
     */
    private void setTabTextsColor(int selectedId){
        setTabTextsDefualtColor();
        switch(selectedId){
            case R.id.aty_main_tab_item_android:
                mAndroidTextView.setTextColor(getResources().getColor(R.color.normalLightText));
                break;
            case R.id.aty_main_tab_item_opengl:
                mOpenGLTextView.setTextColor(getResources().getColor(R.color.normalLightText));
                break;
            case R.id.aty_main_tab_item_vulkan:
                mVulkanTextView.setTextColor(getResources().getColor(R.color.normalLightText));
                break;
            case R.id.aty_main_tab_item_large:
                mLargeTextView.setTextColor(getResources().getColor(R.color.normalLightText));
                break;
        }
    }

    /**
     * ViewPager页面变换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        //滑动ViewPager到第position个页面
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            switch(position){
                case 0:
                    setTabTextsColor(R.id.aty_main_tab_item_android);
                    break;
                case 1:
                    setTabTextsColor(R.id.aty_main_tab_item_opengl);
                    break;
                case 2:
                    setTabTextsColor(R.id.aty_main_tab_item_vulkan);
                    break;
                case 3:
                    setTabTextsColor(R.id.aty_main_tab_item_large);
                    break;
            }
        }

        @Override
        public void onPageSelected(int position){

        }

        @Override
        public void onPageScrollStateChanged(int state){

        }
    }

    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends PagerAdapter{
        List<View> list = new ArrayList<View>();

        public MyPagerAdapter(ArrayList<View> list){
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            ViewPager pViewPager = ((ViewPager) container);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1){
            return arg0 == arg1;
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1){
            ViewPager pViewPager = ((ViewPager) arg0);
            pViewPager.addView(list.get(arg1));
            return list.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1){

        }

        @Override
        public Parcelable saveState(){
            return null;
        }

        @Override
        public void startUpdate(View arg0){
        }
    }
}