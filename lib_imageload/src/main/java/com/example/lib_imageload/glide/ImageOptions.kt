package com.example.lib_imageload.glide

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.Transformation
import com.example.lib_imageload.R


/**
 * @constructor placeholderImageRes 占位图,与加载错误显示图当做同一资源处理
 * @constructor transformations 常用属性 RoundedCornersTransformation,CircleCrop,CenterCrop、FitCenter
 */
data class ImageOptions(
    var requestWidth: Int = 0,
    var requestHeight: Int = 0,
    //大多数情况都需要一个默认占位图,不需求请设置为<=0
    @DrawableRes
    var placeholderImageRes: Int = R.drawable.img_glide_placeholder,
    @DrawableRes
    var errorImageRes: Int = R.drawable.img_glide_error,
    var transformations: MutableList<Transformation<Bitmap>> = mutableListOf()
)

/**
 *
    1.Crop //裁剪
        CropTransformation
        CropCircleTransformation //过时了,请使用 RequestOptions.circleCrop()
        CropCircleWithBorderTransformation //带边框的
        CropSquareTransformation    //自定义矩形剪裁
        RoundedCornersTransformation //圆角剪裁  cornerType 边角类型（可以指定4个角中的哪几个角是圆角）
    2.Color
        ColorFilterTransformation   //颜色滤镜
        GrayscaleTransformation //灰度级转换
    3.Blur
        BlurTransformation  //模糊处理
    4.Mask
        MaskTransformation  //遮罩掩饰（视图叠加处理）

 */
