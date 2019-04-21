package com.karenjar.ihelper;

/**
 * <pre>
 *     打印帮助类接口，不同的平台不同的实现方式，例如Android和Java的打印方式就会不一样
 *     The Log helper interface , different platform owns different implementations
 * </pre>
 */
public interface ILogHelper {
    /**
     * <pre>
     *     打印
     *     print
     * </pre>
     */
    void d(String tag, String content);

    /**
     * <pre>
     *     打印错误信息
     *     print the exception message
     * </pre>
     */
    void e(String tag, String content);

    /**
     * <pre>
     *     打印错误信息
     *     print the exception message
     * </pre>
     */
    void e(String tag, String content, Exception e);

    /**
     * <pre>
     *     得到调用此方法的线程的线程名
     *     get the Thread name
     * </pre>
     */
    String getThreadName();

    /**
     * <pre>
     *     是否是Debug模式，是的话将不会打印内容
     *     there will no content to print if it is a Debug mode
     * </pre>
     *
     * @return true:Debug mode
     */
    boolean isLogDebug();

    /**
     * <pre>
     *     设置是否是Debug模式
     *     set if it is a Debug mode
     * </pre>
     *
     * @param isLogDebug true:Debug mode
     */
    void setLogDebug(boolean isLogDebug);

    /**
     * <pre>
     *     得到Log的根tag
     *     get the root tag
     * </pre>
     */
    String getRootTag();

    /**
     * <pre>
     *     设置Log的根TAG
     *     set the root TAG
     * </pre>
     */
    void setRootTag(String rootTag);
}
