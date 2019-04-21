package com.karenjar;

import android.app.Application;

import com.karenjar.helper.KJLogHelper;
import com.karenjar.helper.android.LogHelper;

/**
 * <pre>
 * </pre>
 * Created by Dandy on 2018/7/10
 */
public class KarenApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        KJLogHelper.setLogHelper(new LogHelper());
    }
}
