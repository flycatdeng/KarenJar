package com.karenjar.helper.jopengl;

import android.opengl.GLES20;

/**
 * <pre>
 *     默认使用TextureOptions.defaultOptions()来得到选项，mipmap默认使用TextureOptions.defaultMipmapOptions();
 *     其他情况通过使用new Builder().setUseMipmap(true).set....build()方法来获得对象
 *      纹理采样选项
 *      GL_NEAREST也叫邻近过滤，是OpenGL默认的纹理过滤方式。OpenGL会选择中心点最接近纹理坐标的那个像素；
 *      GL_LINEAR也叫线性过滤，它会基于纹理坐标附近的纹理像素，计算出一个插值，近似出这些纹理像素之间的颜色。一个纹理像素的中心距离纹理坐标越近，那么这个纹理像素的颜色对最终的样本颜色的贡献越大；
 *      环绕方式(Wrapping)
 *          GL_REPEAT	对纹理的默认行为。重复纹理图像。
 *          GL_MIRRORED_REPEAT	和GL_REPEAT一样，但每次重复图片是镜像放置的(就是相当于一面镜子)。
 *          GL_CLAMP_TO_EDGE	纹理坐标会被约束在0到1之间，超出的部分会重复纹理坐标的边缘，产生一种边缘被拉伸的效果（开发时默认）。
 *          GL_CLAMP_TO_BORDER	超出的坐标为用户指定的边缘颜色
 *      mipmap
 *          OpenGL使用一种叫做多级渐远纹理(Mipmap)的概念来解决这个问题，它简单来说就是一系列的纹理图像，后一个纹理图像是前一个的二分之一。
 *          多级渐远纹理背后的理念很简单：距观察者的距离超过一定的阈值，OpenGL会使用不同的多级渐远纹理，即最适合物体的距离的那个。
 *          由于距离远，解析度不高也不会被用户注意到。同时，多级渐远纹理另一加分之处是它的性能非常好
 *          mipmap 过滤方式
 *              GL_NEAREST_MIPMAP_NEAREST	使用最邻近的多级渐远纹理来匹配像素大小，并使用邻近插值进行纹理采样
 *              GL_LINEAR_MIPMAP_NEAREST	使用最邻近的多级渐远纹理级别，并使用线性插值进行采样
 *              GL_NEAREST_MIPMAP_LINEAR	在两个最匹配像素大小的多级渐远纹理之间进行线性插值，使用邻近插值进行采样
 *              GL_LINEAR_MIPMAP_LINEAR	在两个邻近的多级渐远纹理之间使用线性插值，并使用线性插值进行采样
 *       多级渐远纹理主要是使用在纹理被缩小的情况下的：纹理放大不会使用多级渐远纹理，为放大过滤设置多级渐远纹理的选项会产生一个GL_INVALID_ENUM错误代码
 * </pre>
 * <pre>
 * Created by Dandy on 2018/3/22.
 * Wechat: flycatdeng
 * 参考链接：<a href="https://learnopengl-cn.github.io/01%20Getting%20started/06%20Textures/"></a>
 * </pre>
 */
public class TextureOptions {
    private TextureOptions() {
    }

    /**
     * <pre>
     *     GL 目标
     * </pre>
     */
    public int glTarget = GLES20.GL_TEXTURE_2D;
    /**
     * <pre>
     *       MIN采样方式
     *      当一副尺寸较大的纹理图映射到一个显示到屏幕尺寸比其小的图元时系统采用通过GL_TEXTURE_MIN_FILTER设置采样方式
     * </pre>
     */
    public int glTextureMinFilter = GLES20.GL_NEAREST;
    /**
     * <pre>
     *     当一副尺寸较小的纹理图映射到一个显示到屏幕上尺寸比其大的图元时系统采用通过GL_TEXTURE_MAG_FILTER设置采样方式
     * </pre>
     */
    public int glTextureMagFilter = GLES20.GL_LINEAR;
    /**
     * <pre>
     *     环绕方式(Wrapping),S轴方向
     * </pre>
     */
    public int glTextureWapS = GLES20.GL_CLAMP_TO_EDGE;
    /**
     * <pre>
     *     环绕方式(Wrapping),T轴方向
     * </pre>
     */
    public int glTextureWapT = GLES20.GL_CLAMP_TO_EDGE;
    /**
     * <pre>
     *     是否使用mipmap技术，
     * </pre>
     */
    public boolean useMipmap = false;

    public static class Builder {
        public int glTarget = GLES20.GL_TEXTURE_2D;
        public int glTextureMinFilter = GLES20.GL_NEAREST;
        public int glTextureMagFilter = GLES20.GL_LINEAR;
        public int glTextureWapS = GLES20.GL_CLAMP_TO_EDGE;
        public int glTextureWapT = GLES20.GL_CLAMP_TO_EDGE;
        public boolean useMipmap = false;

        public Builder setTarget(int target) {
            this.glTarget = target;
            return this;
        }

        public Builder setTextureMinFilter(int minFilter) {
            this.glTextureMinFilter = minFilter;
            return this;
        }

        public Builder setTextureMagFilter(int magFilter) {
            this.glTextureMagFilter = magFilter;
            return this;
        }

        public Builder setTextureWapS(int wapS) {
            this.glTextureWapS = wapS;
            return this;
        }

        public Builder setTextureWapT(int wapT) {
            this.glTextureWapT = wapT;
            return this;
        }

        public Builder setUseMipmap(boolean useMipmap) {
            this.useMipmap = useMipmap;
            return this;
        }

        public TextureOptions build() {
            TextureOptions options = new TextureOptions();
            options.glTarget = glTarget;
            options.glTextureMinFilter = glTextureMinFilter;
            options.glTextureMagFilter = glTextureMagFilter;
            options.glTextureWapS = glTextureWapS;
            options.glTextureWapT = glTextureWapT;
            options.useMipmap = useMipmap;
            if (useMipmap) {//如果使用mipmap那么你就需要使用相应的值来做过滤处理
                if (glTextureMinFilter == GLES20.GL_NEAREST || glTextureMinFilter == GLES20.GL_LINEAR) {
                    throw new RuntimeException("the value GLES20.GL_NEAREST or GLES20.GL_LINEAR for GL_TEXTURE_MIN_FILTER is not good for texture when you wanna use mipmap");
                }
            }
            return options;
        }
    }

    public static TextureOptions defaultOptions() {
        return new Builder().build();
    }

    public static TextureOptions defaultMipmapOptions() {
        return new Builder().setUseMipmap(true).setTextureMinFilter(GLES20.GL_LINEAR_MIPMAP_LINEAR).setTextureMagFilter(GLES20.GL_LINEAR_MIPMAP_LINEAR).build();
    }
}
