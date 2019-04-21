package com.karenjar.helper.imp;

import com.karenjar.ihelper.ILogHelper;

/**
 * <pre>
 *     打印帮助抽象类
 *     a abstract class of print log
 * </pre>
 * Created by Dandy
 * Wechat: flycatdeng
 */
public abstract class KJALogHelper implements ILogHelper{
    private final String ROOT_TAG = "dandy";
    private String mRootTag = ROOT_TAG;
    private boolean mIsLogDebug = false;

    public boolean isLogDebug() {
        return mIsLogDebug;
    }

    public void setLogDebug(boolean isLogDebug) {
        mIsLogDebug = isLogDebug;
    }

    public String getRootTag() {
        return mRootTag;
    }

    public void setRootTag(String rootTag) {
        mRootTag = rootTag;
    }

    /**
     * 得到调用此方法的线程的线程名
     *
     * @return
     */
    public String getThreadName() {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(Thread.currentThread().getName());
            sb.append("-> ");
            sb.append(Thread.currentThread().getStackTrace()[3].getMethodName());
            sb.append("()");
            sb.append(" ");
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
