package com.karenjar.demo.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.karenjar.demo.BaseDemoAty;
import com.karenjar.helper.android.LogHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * <pre>
 *  从缓冲区读取内容并保存图片
 * </pre>
 * Created by flycatdeng on 2019/4/22
 */
public class SaveImageAty extends BaseDemoAty {
    private StageView mGLSurfaceView;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout fl = new FrameLayout(this);
        FrameLayout.LayoutParams fll = new FrameLayout.LayoutParams(500, 500);
        fll.gravity = Gravity.TOP;

        mGLSurfaceView = new StageView(this);
        fl.addView(mGLSurfaceView, fll);

        mImageView = new ImageView(this);
        mImageView.setBackgroundColor(Color.BLACK);//黑色的ImageView
        FrameLayout.LayoutParams fl2 = new FrameLayout.LayoutParams(500, 500);
        fl2.gravity = Gravity.BOTTOM;
        fl.addView(mImageView, fl2);

        Button btn = new Button(this);
        btn.setText("Read");
        FrameLayout.LayoutParams fl3 = new FrameLayout.LayoutParams(500, 500);
        fl3.gravity = Gravity.CENTER;
        fl.addView(btn, fl3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGLSurfaceView.readPixel(new StageView.IReadPixelListener() {
                    @Override
                    public void onReadPixelCallback(final Bitmap bmp) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("dandy", "onReadPixelCallback bmp=" + bmp);
                                Toast.makeText(SaveImageAty.this, "onReadPixelCallback bmp=" + bmp, Toast.LENGTH_SHORT).show();
                                mImageView.setImageBitmap(bmp);
                            }
                        });
                    }
                });
            }
        });
        setContentView(fl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();//唤醒渲染线程
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();//暂停渲染线程
    }

    /**
     * 一个GLSurfaceView的子类
     */
    static class StageView extends GLSurfaceView implements GLSurfaceView.Renderer {

        private int mWinWidth;
        private int mWinHeight;

        public StageView(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context) {
            this.setEGLContextClientVersion(2); // 设置使用OPENGL ES3.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                setPreserveEGLContextOnPause(true);//如果没有这一句，那onPause之后再onResume屏幕将会是黑屏滴
            }
            setRenderer(this);//设置渲染器
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//请求的时候才去渲染一次;RENDERMODE_CONTINUOUSLY为持续渲染
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);//设置背景色，黄色
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
//                GLES20.glViewport(0, 0, width, height);
            mWinWidth = width;
            mWinHeight = height;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清楚深度缓存和颜色缓存
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            if (mNeedReadPixel) {
                Bitmap bmp = readBufferPixelToBitmap(mWinWidth, mWinHeight);
                mNeedReadPixel = false;
                if (mListener != null) {
                    mListener.onReadPixelCallback(bmp);
                }
            }
        }

        @NonNull
        private Bitmap readBufferPixelToBitmap(int width, int height) {
            ByteBuffer buf = ByteBuffer.allocateDirect(width * height * 4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf);
            buf.rewind();

            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.copyPixelsFromBuffer(buf);
            return bmp;
        }

        public interface IReadPixelListener {
            void onReadPixelCallback(Bitmap bmp);
        }

        private IReadPixelListener mListener;
        private boolean mNeedReadPixel = false;

        public void readPixel(IReadPixelListener listener) {
            mListener = listener;
            mNeedReadPixel = true;
        }
    }
}