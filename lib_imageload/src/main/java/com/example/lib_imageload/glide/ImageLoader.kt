package com.example.lib_imageload.glide

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.lib_imageload.R
import com.ljp.lib_base.extensions.imageUrlIsGif
import java.lang.ref.WeakReference
import com.bumptech.glide.request.target.Target



/**
 * 加载并显示图片
 */
@SuppressLint("CheckResult")
fun ImageView.loadImage(
    imageUrl: String?,
    imageOptions: ImageOptions? = null
) {
    if (!isContextAvailable(this.context)) {
        return
    }

    val imageWeakReference: WeakReference<ImageView> = WeakReference<ImageView>(this)
    imageWeakReference.get()?.run {
        //是否是GIF
        val isGif = imageUrl?.imageUrlIsGif() ?: false

        val glideRequests = if (isGif) {
            GlideApp.with(this).asGif()
        } else {
            GlideApp.with(this).asDrawable()
        }

        //requestOptions
        val options = getGlideRequestOptions(this.context, imageOptions)

        options.set(GifOptions.DISABLE_ANIMATION, !isGif)
        //缓存方式
        options
            .skipMemoryCache(isGif)
            .diskCacheStrategy((if (isGif) DiskCacheStrategy.NONE else DiskCacheStrategy.RESOURCE))

//        //在占位符和第一个资源之间以及它们之间启用交叉淡入动画
//        glideRequests.transition(
//            DrawableTransitionOptions.with(
//                DrawableCrossFadeFactory.Builder(200).setCrossFadeEnabled(true).build()
//            )
//        )

        glideRequests
            .apply(options)
            .load(imageUrl)
            .into(this)
    }
}

/**
 * 加载图片并监听结果,返回文件(File)路径
 *
 * 不对imageView做into操作,采用了submit,结果请再回调中自行处理
 */
@SuppressLint("CheckResult")
fun ImageView.setImageUrlAndListener(
    imageUrl: String?,
    imageOptions: ImageOptions? = null,
    listener: ImageLoadingListener
) {
    if (!isContextAvailable(this.context)) {
        return
    }
    val imageWeakReference: WeakReference<ImageView> = WeakReference<ImageView>(this)
    imageWeakReference.get()?.run {
        val glideRequests = GlideApp
            .with(this)
            .asDrawable()

        //requestOptions
        var options = getGlideRequestOptions(context, imageOptions)
        //缓存方式
        options
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.DATA)

        glideRequests
            .apply(options)
            .load(imageUrl)
            .listener(ImageRequestListener(imageUrl, this, listener))
            .into(this)
    }
}

/**
 * 图片的其他参数设置
 */
@SuppressLint("CheckResult")
private fun getGlideRequestOptions(
    context: Context,
    imageOptions: ImageOptions?
): RequestOptions {
    val options = RequestOptions()

    if ((imageOptions?.placeholderImageRes ?: 0) > 0) {
        options.placeholder(getPlaceholderDrawable(context, imageOptions))
    }
    if ((imageOptions?.errorImageRes ?: 0) > 0) {
        options.error(getErrorDrawable(context, imageOptions))
    }

    imageOptions?.apply {
        //加载的图片大小
        if (requestWidth > 0 && requestHeight > 0) {
            options.override(requestWidth, requestHeight)
        }
        //圆形头像或者圆角图片等
        if (transformations.isNotEmpty()) {
            options.transform(MultiTransformation(transformations))
        }
    }
    return options
}

private fun getPlaceholderDrawable(context: Context, imageOptions: ImageOptions?): Drawable? {
    return ContextCompat.getDrawable(
        context, imageOptions?.placeholderImageRes ?: R.drawable.img_glide_placeholder
    )
}

private fun getErrorDrawable(context: Context, imageOptions: ImageOptions?): Drawable? {
    return ContextCompat.getDrawable(
        context,
        imageOptions?.errorImageRes ?: R.drawable.img_glide_error
    )
}

class ImageRequestListener(
    private var imageUrl: String?,
    private var imageView: ImageView?,
    private var listener: ImageLoadingListener
) : RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        com.ljp.lib_base.extensions.uiThread {
            listener.onLoadingFailed(imageUrl, imageView, e?.cause)
        }
        return false
    }

    override fun onResourceReady(
        resource: Drawable,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        //一般下载图片到本地用submit形式操作,并做了监听,所以把本地文件返回给调用者,自行做显示的操作等其他操作逻辑
        com.ljp.lib_base.extensions.uiThread {
            listener.onLoadingComplete(imageUrl, imageView, resource)
        }
        return false
    }

}


fun isContextAvailable(context: Context?): Boolean {
    if (null == context) {
        return false
    }
    if (context is Activity) {
        if (context.isDestroyed || context.isFinishing) {
            return false
        }
    }
    return true
}

/**
 * 加载 圆角图片
 * @param corner
 */
fun ImageView.loadImageCorner(url: String?, corner: Int = 0) {
    val list = mutableListOf<Transformation<Bitmap>>()
    list.add(CenterCrop())
    list.add(RoundedCorners(corner))
    loadImage(url, ImageOptions(transformations = list))
}

/**
 * 加载圆形图片
 */
fun ImageView.loadImageCircleCrop(url: String?) {
    loadImage(url, ImageOptions(transformations = mutableListOf(CircleCrop())))
}