package com.karenjar.ihelper;

import java.io.InputStream;

/**
 * <pre>
 * </pre>
 * Created by Dandy
 * Wechat: flycatdeng
 */
public interface IFileHelper {
    /**
     * 得到文件流
     *
     * @param path 文件路劲
     * @return 文件输入流
     */
    InputStream getInputStreamFromPath(String path);

    /**
     * 判断某个路径的文件是否存在
     *
     * @param path 文件路劲
     * @return
     */
    boolean isFileExit(String path);

    /**
     * 返回该文件的大小，例如1.3GB
     *
     * @param fileLength 文件长度，可以由File.getLength（）得到
     * @return 回该文件的大小，例如1.3GB
     */
    String formatFileLength(long fileLength);

    /**
     * 复制文件
     *
     * @param ins              该文件的输入流
     * @param destFileFullPath 要存放的文件的路径
     * @return 是否复制成功
     */
    boolean copyFile(InputStream ins, String destFileFullPath);

    /**
     * 复制文件夹
     *
     * @param srcFolderFullPath  源文件夹
     * @param destFolderFullPath 目标文件夹
     * @return 是否复制成功
     */
    boolean copyFolder(String srcFolderFullPath, String destFolderFullPath);

    /**
     * 从文件获取文件内容
     *
     * @param filePath 文件
     * @return 文件内容
     */
    String getFileContent(String filePath);

    /**
     * 从输入流获取文件的内容
     *
     * @param ins 文件输入流
     * @return 文件内容
     */
    String getFileContent(InputStream ins);

    /**
     * 复制内容到某个文件
     *
     * @param content          要复制的内容
     * @param destFileFullPath 目标文件
     * @return true:复制成功
     */
    boolean copyContentToFile(String content, String destFileFullPath);

    /**
     * 删除某个文件夹
     *
     * @param targetFolderFullPath 要删除的文件夹的路径
     */
    boolean deleteFolder(String targetFolderFullPath);

    /**
     * 删除某个文件
     *
     * @param targetFileFullPath 要删除的文件的路径
     */
    void deleteFile(String targetFileFullPath);

    /**
     * <pre>
     * 在某个大的文件夹中删除指定名称的所有文件，
     * 例如这个文件夹下不同的子文件夹下都有一个叫a.txt的文件，那么此时可以用这个方法来删除这个a.txt
     * </pre>
     *
     * @param targetFolderFullPath 目标文件夹
     * @param fileSimpleName       要删除的文件的名称（不要全路劲）
     */
    void deleteAppointedFilesInDirectory(String targetFolderFullPath, String fileSimpleName);

    /**
     * <pre>
     * 在某个大的文件夹中删除指定名称的所有文件夹，
     * 例如这个文件夹下不同的子文件夹下都有一个叫.svn的文件夹，那么此时可以用这个方法来删除这个.svn夹
     * </pre>
     *
     * @param targetFolderFullPath 目标文件夹
     * @param directorySimpleName  要删除的文件夹的名称（不要全路劲）
     */
    void deleteAppointedDirectorysInDirectory(String targetFolderFullPath, String directorySimpleName);
}
