package com.karenjar.helper;

/**
 * <pre>
 * </pre>
 * Created by Dandy
 */

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <pre>
 * </pre>
 * Created by Dandy
 * Wechat: flycatdeng
 */
public class KJStreamHelper{
    private static final String TAG = "QJStreamHelper";

    /**
     * 输入流转字节数组
     *
     * @param ins 输入流
     * @return
     * @throws IOException
     */
    public static void copyStream(InputStream ins, OutputStream out) throws IOException{
        try{
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len = ins.read(buffer)) > 0){
                out.write(buffer, 0, len);
            }
            out.flush();
        }finally{
            closeIOStream(ins, out);
        }
    }

    /**
     * 关闭io流
     *
     * @param closeable
     */
    public static void closeIOStream(Closeable... closeable){
        if(closeable == null){
            return;
        }
        for(Closeable ca : closeable){
            try{
                if(ca == null){
                    continue;
                }
                ca.close();
                ca = null;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

