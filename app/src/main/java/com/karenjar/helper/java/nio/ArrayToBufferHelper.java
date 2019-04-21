package com.karenjar.helper.java.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * <pre>
 *    数组转成buffer的帮助类
 * </pre>
 * Created by Dandy
 * Wechat: flycatdeng
 */

public class ArrayToBufferHelper{
    /**
     * <pre>
     *      float数组转换成float buffer
     * </pre>
     *
     * @param arrays float 数组,非空
     * @return
     */
    public static FloatBuffer floatArrayToBuffer(float[] arrays) {
        FloatBuffer floatBuffer;
        if (arrays == null) {
            throw new RuntimeException("ArrayToBufferHelper floatArrayToBuffer arrays should not be null");
        }
        // 数据的初始化================begin============================
        // 数据缓冲
        // arrays.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(arrays.length * 4);
        vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
        floatBuffer = vbb.asFloatBuffer();// 转换为Float型缓冲
        floatBuffer.put(arrays);// 向缓冲区中放入数据
        floatBuffer.position(0);// 设置缓冲区起始位置
        // 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        // 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        // 数据的初始化================end============================
        return floatBuffer;
    }

    /**
     * <pre>
     *      short数组转换成short buffer
     * </pre>
     *
     * @param arrays short 数组,非空
     * @return
     */
    public static ShortBuffer shortArrayToBuffer(short[] arrays) {
        ShortBuffer buffer;
        if (arrays == null) {
            throw new RuntimeException("ArrayToBufferHelper shortArrayToBuffer arrays should not be null");
        }
        // 数据的初始化================begin============================
        // 数据缓冲
        // arrays.length*2是因为一个整数2个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(arrays.length * 2);
        vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
        buffer = vbb.asShortBuffer();// 转换为Float型缓冲
        buffer.put(arrays);// 向缓冲区中放入数据
        buffer.position(0);// 设置缓冲区起始位置
        // 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        // 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        // 数据的初始化================end============================
        return buffer;
    }

}
