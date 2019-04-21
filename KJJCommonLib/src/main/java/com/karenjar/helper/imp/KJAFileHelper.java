package com.karenjar.helper.imp;

import com.karenjar.helper.KJLogHelper;
import com.karenjar.ihelper.IFileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * <pre>
 * </pre>
 * Created by Dandy
 * Wechat: flycatdeng
 */
public abstract class KJAFileHelper implements IFileHelper{
    private static final String TAG = "QJAFileHelper";

    /**
     * 得到文件流
     *
     * @param path 文件路劲
     * @return 文件输入流
     */
    public InputStream getInputStreamFromPath(String path) {
        KJLogHelper.d(TAG, KJLogHelper.getThreadName());
        InputStream ins = null;
        // File file = new File(path);
        try {
            ins = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ins;
    }

    /**
     * 判断某个路径的文件是否存在
     *
     * @param path 文件路劲
     * @return
     */
    @Override
    public boolean isFileExit(String path) {
        File f = new File(path);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回该文件的大小，例如1.3GB
     *
     * @param fileLength 文件长度，可以由File.getLength（）得到
     * @return 回该文件的大小，例如1.3GB
     */
    @Override
    public String formatFileLength(long fileLength) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (fileLength >= gb) {
            return String.format(Locale.getDefault(), "%.1f GB", (float) fileLength / gb);
        } else if (fileLength >= mb) {
            float f = (float) fileLength / mb;
            return String.format(Locale.getDefault(), f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (fileLength >= kb) {
            float f = (float) fileLength / kb;
            return String.format(Locale.getDefault(), f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format(Locale.getDefault(), "%d B", fileLength);
        }
    }

    /**
     * 复制文件
     *
     * @param ins              该文件的输入流
     * @param destFileFullPath 要存放的文件的路径
     * @return 是否复制成功
     */
    @Override
    public boolean copyFile(InputStream ins, String destFileFullPath) {
        KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "destFileFullPath-" + destFileFullPath);
        FileOutputStream fos = null;
        try {
            File file = new File(destFileFullPath);
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "开始读入");
            fos = new FileOutputStream(file);
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "开始写出");
            byte[] buffer = new byte[8192];
            int count = 0;
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "准备循环了");
            while ((count = ins.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "已经创建该文件");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + e.getMessage());
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                KJLogHelper.d(TAG, KJLogHelper.getThreadName() + e.getMessage());
            }
        }
    }

    /**
     * 复制文件夹
     *
     * @param srcFolderFullPath  源文件夹
     * @param destFolderFullPath 目标文件夹
     * @return 是否复制成功
     */
    @Override
    public boolean copyFolder(String srcFolderFullPath, String destFolderFullPath) {
        KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "srcFolderFullPath-" + srcFolderFullPath + " destFolderFullPath-" + destFolderFullPath);
        try {
            boolean success = (new File(destFolderFullPath)).mkdirs(); // 如果文件夹不存在
            // 则建立新文件夹
            if (!success) {
                return false;
            }
            File file = new File(srcFolderFullPath);
            String[] files = file.list();
            File temp = null;
            for (int i = 0; i < files.length; i++) {
                if (srcFolderFullPath.endsWith(File.separator)) {
                    temp = new File(srcFolderFullPath + files[i]);
                } else {
                    temp = new File(srcFolderFullPath + File.separator + files[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    copyFile(input, destFolderFullPath + "/" + (temp.getName()).toString());
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(srcFolderFullPath + "/" + files[i], destFolderFullPath + "/" + files[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 从文件获取文件内容
     *
     * @param filePath 文件
     * @return 文件内容
     */
    @Override
    public String getFileContent(String filePath) {
        try {
            FileInputStream fins = new FileInputStream(filePath);
            return getFileContent(fins);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从输入流获取文件的内容
     *
     * @param ins 文件输入流
     * @return 文件内容
     */
    @Override
    public String getFileContent(InputStream ins) {
        try {
            byte[] contentByte = new byte[ins.available()];
            ins.read(contentByte);
            return new String(contentByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 复制内容到某个文件
     *
     * @param content          要复制的内容
     * @param destFileFullPath 目标文件
     * @return true:复制成功
     */
    @Override
    public boolean copyContentToFile(String content, String destFileFullPath) {
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(destFileFullPath);
            fWriter.write(content);
            fWriter.flush();
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 删除某个文件夹
     *
     * @param targetFolderFullPath 要删除的文件夹的路径
     */
    @Override
    public boolean deleteFolder(String targetFolderFullPath) {
        KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "targetFolderFullPath-" + targetFolderFullPath);
        File file = new File(targetFolderFullPath);
        if (!file.exists()) {
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "file does not exist");
            return true;
        }
        String[] files = file.list();
        File temp = null;
        if (files == null) {
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "files == null");
            return true;
        }
        if (files.length == 0) {
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "files.length == 0");
            boolean success = file.delete();
            return success;
        }
        for (int i = 0; i < files.length; i++) {
            if (targetFolderFullPath.endsWith(File.separator)) {
                temp = new File(targetFolderFullPath + files[i]);
            } else {
                temp = new File(targetFolderFullPath + File.separator + files[i]);
            }
            if (temp.isFile()) {
                deleteFile(targetFolderFullPath + File.separator + (temp.getName()).toString());
            }
            if (temp.isDirectory()) {// 如果是子文件夹
                deleteFolder(targetFolderFullPath + File.separator + files[i]);
            }
        }
        boolean success = file.delete();
        return success;
    }

    /**
     * 删除某个文件
     *
     * @param targetFileFullPath 要删除的文件的路径
     */
    @Override
    public void deleteFile(String targetFileFullPath) {
        File file = new File(targetFileFullPath);
        file.delete();
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
    @Override
    public void deleteAppointedFilesInDirectory(String targetFolderFullPath, String fileSimpleName) {
        File file = new File(targetFolderFullPath);
        if (!file.exists()) {// 文件夹不存在，不用查找
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "file does not exist");
            return;
        }
        String[] files = file.list();
        File temp = null;
        if (files == null || files.length == 0) {// 文件夹下没有子文件或子文件夹，不用查找
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "files.length == 0");
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (!files[i].equals(fileSimpleName)) {// 如果你的文件名或者文件夹的名称都和目标名称不一致了，那这个就不用判断了，直接判断下一个
                continue;
            }
            if (targetFolderFullPath.endsWith(File.separator)) {
                temp = new File(targetFolderFullPath + files[i]);
            } else {
                temp = new File(targetFolderFullPath + File.separator + files[i]);
            }
            if (temp.isFile()) {// 是文件，而且名称相同，删除
                temp.delete();
//                deleteAppointedFile(targetFolderFullPath + "/" + (temp.getName()).toString());
            }
            if (temp.isDirectory()) {// 如果是子文件夹
                deleteAppointedFilesInDirectory(targetFolderFullPath + "/" + files[i], fileSimpleName);
            }
        }
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
    @Override
    public void deleteAppointedDirectorysInDirectory(String targetFolderFullPath, String directorySimpleName) {
        File file = new File(targetFolderFullPath);
        if (file.getName().equals(directorySimpleName)) {// 如果该文件夹已经是要删除的文件夹名称了，直接删除这个文件夹
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + " file.getName()=" + file.getName() + " directorySimpleName=" + directorySimpleName);
            deleteFolder(targetFolderFullPath);
            return;
        }
        String[] files = file.list();
        File temp = null;
        if (files == null || files.length == 0) {// 文件夹下没有子文件或子文件夹，不用查找
            KJLogHelper.d(TAG, KJLogHelper.getThreadName() + "files.length == 0");
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (!files[i].equals(directorySimpleName)) {// 如果你的文件名或者文件夹的名称都和目标名称不一致了，那这个就不用判断了，直接判断下一个
                continue;
            }
            if (targetFolderFullPath.endsWith(File.separator)) {
                temp = new File(targetFolderFullPath + files[i]);
            } else {
                temp = new File(targetFolderFullPath + File.separator + files[i]);
            }
            if (temp.isFile()) {// 是文件，而且名称相同，删除
                continue;
            }
            if (temp.isDirectory()) {// 如果是子文件夹
                deleteAppointedDirectorysInDirectory(targetFolderFullPath + File.separator + files[i], directorySimpleName);
            }
        }
    }
}
