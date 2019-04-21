package com.karenjar;

/**
 * <pre>
 * </pre>
 * Created by Dandy on 2018/7/10
 */
public class KJCNative{
    static {
        System.loadLibrary("KJCGLES");
    }
    public static native int test();
}
