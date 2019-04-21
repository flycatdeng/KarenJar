package com.karenjar.helper;

import com.karenjar.helper.imp.DefaultFileHelper;
import com.karenjar.ihelper.IFileHelper;

import java.io.InputStream;

/**
 * <pre>
 * </pre>
 * Created by Dandy
 * Wechat: flycatdeng
 */
public class KJFileHelper{
    private static IFileHelper sFileHelper = new DefaultFileHelper();

    public static void setFileHelper(IFileHelper helper) {
        sFileHelper = helper;
    }

    /**
     * 得到文件流
     *
     * @param path 文件路劲
     * @return 文件输入流
     */
    public static InputStream getInputStreamFromPath(String path) {
        return sFileHelper.getInputStreamFromPath(path);
    }

    /**
     * 判断某个路径的文件是否存在
     *
     * @param path 文件路劲
     * @return
     */
    public static boolean isFileExit(String path) {
        return sFileHelper.isFileExit(path);
    }

    /**
     * 返回该文件的大小，例如1.3GB
     *
     * @param fileLength 文件长度，可以由File.getLength（）得到
     * @return 回该文件的大小，例如1.3GB
     */
    public static String formatFileLength(long fileLength) {
        return sFileHelper.formatFileLength(fileLength);
    }

    /**
     * 复制文件
     *
     * @param ins              该文件的输入流
     * @param destFileFullPath 要存放的文件的路径
     * @return 是否复制成功
     */
    public static boolean copyFile(InputStream ins, String destFileFullPath) {
        return sFileHelper.copyFile(ins, destFileFullPath);
    }

    /**
     * 复制文件夹
     *
     * @param srcFolderFullPath  源文件夹
     * @param destFolderFullPath 目标文件夹
     * @return 是否复制成功
     */
    public static boolean copyFolder(String srcFolderFullPath, String destFolderFullPath) {
        return sFileHelper.copyFolder(srcFolderFullPath, destFolderFullPath);
    }

    /**
     * 从文件获取文件内容
     *
     * @param filePath 文件
     * @return 文件内容
     */
    public static String getFileContent(String filePath) {
        return sFileHelper.getFileContent(filePath);
    }

    /**
     * 从输入流获取文件的内容
     *
     * @param ins 文件输入流
     * @return 文件内容
     */
    public static String getFileContent(InputStream ins) {
        return sFileHelper.getFileContent(ins);
    }

    /**
     * 复制内容到某个文件
     *
     * @param content          要复制的内容
     * @param destFileFullPath 目标文件
     * @return true:复制成功
     */
    public static boolean copyContentToFile(String content, String destFileFullPath) {
        return sFileHelper.copyContentToFile(content, destFileFullPath);
    }

    /**
     * 删除某个文件夹
     *
     * @param targetFolderFullPath 要删除的文件夹的路径
     */
    public static boolean deleteFolder(String targetFolderFullPath) {
        return sFileHelper.deleteFolder(targetFolderFullPath);
    }

    /**
     * 删除某个文件
     *
     * @param targetFileFullPath 要删除的文件的路径
     */
    public static void deleteFile(String targetFileFullPath) {
        sFileHelper.deleteFile(targetFileFullPath);
    }

    /**
     * <pre>
     * 在某个大的文件夹中删除指定名称的所有文件，
     * 例如这个文件夹下不同的子文件夹下都有一个叫a.txt的文件，那么此时可以用这个方法来删除这个a.txt
     * </pre>
     *
     * @param targetFolderFullPath 目标文件夹
     * @param fileSimpleName       要删除的文件的名称（不要全路劲）
     */
    public static void deleteAppointedFilesInDirectory(String targetFolderFullPath, String fileSimpleName) {
        sFileHelper.deleteAppointedFilesInDirectory(targetFolderFullPath, fileSimpleName);
    }

    /**
     * <pre>
     * 在某个大的文件夹中删除指定名称的所有文件夹，
     * 例如这个文件夹下不同的子文件夹下都有一个叫.svn的文件夹，那么此时可以用这个方法来删除这个.svn夹
     * </pre>
     *
     * @param targetFolderFullPath 目标文件夹
     * @param directorySimpleName  要删除的文件夹的名称（不要全路劲）
     */
    public static void deleteAppointedDirectorysInDirectory(String targetFolderFullPath, String directorySimpleName) {
        sFileHelper.deleteAppointedDirectorysInDirectory(targetFolderFullPath, directorySimpleName);
    }
}
