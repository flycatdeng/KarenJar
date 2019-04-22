package com.karenjar.demo.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.karenjar.demo.BaseDemoAty;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * <pre>
 *  简单的背景色显示
 * </pre>
 * Created by flycatdeng on 2019/4/22
 */
public class BackgroundAty extends BaseDemoAty {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout fl = new FrameLayout(this);
        FrameLayout.LayoutParams fll = new FrameLayout.LayoutParams(500, 500);
        fll.gravity = Gravity.CENTER;

        mGLSurfaceView = new StageView(this);

        fl.addView(mGLSurfaceView, fll);
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
    class StageView extends GLSurfaceView {

        public StageView(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context) {
            this.setEGLContextClientVersion(2); // 设置使用OPENGL ES3.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                setPreserveEGLContextOnPause(true);//如果没有这一句，那onPause之后再onResume屏幕将会是黑屏滴
            }
            setRenderer(mRenderer);//设置渲染器
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//请求的时候才去渲染一次;RENDERMODE_CONTINUOUSLY为持续渲染
        }

        /**
         * 渲染器
         */
        private Renderer mRenderer = new Renderer() {

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                GLES20.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);//设置背景色，黄色
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
//                GLES20.glViewport(0, 0, width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                //清楚深度缓存和颜色缓存
                GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            }
        };
    }
}