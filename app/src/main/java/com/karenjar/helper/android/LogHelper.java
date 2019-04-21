package com.karenjar.helper.android;

import android.util.Log;

import com.karenjar.helper.imp.KJALogHelper;

/**
 * <pre>
 *     log help class
 *     打印帮助类
 * </pre>
 * Created by dandy on 2018/3/22.
 * Wechat: flycatdeng
 */
public class LogHelper extends KJALogHelper{

    public void d(String tag, String content) {
        Log.d(tag, content);
    }

    public void e(String tag, String content) {
        Log.e(tag, content);
    }

    public void e(String tag, String content, Exception e) {
        Log.e(tag, content + " e=" + e.getMessage());
    }

}
