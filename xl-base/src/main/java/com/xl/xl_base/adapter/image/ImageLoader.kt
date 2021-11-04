package com.xl.xl_base.adapter.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
class ImageLoader {

    private val requestManager: RequestManager
    private val requestOptions by lazy {
        RequestOptions()
    }

    constructor(activity: FragmentActivity) {
        requestManager = Glide.with(activity)
    }

    constructor(fragment: Fragment) {
        requestManager = Glide.with(fragment)
    }

    constructor(view: View) {
        requestManager = Glide.with(view)
    }

    constructor(context: Context) {
        requestManager = Glide.with(context)
    }

    fun display(
        view: ImageView,
        url: String?,
        placeholder: Int = 0,
        error: Int = 0,
        centerCrop: Boolean = false,
        radius: Int = 0,//圆角
        crossFade: Boolean = false,//缩放动画
        transformation: BitmapTransformation? = null,
        callback: ((Boolean) -> Unit)? = null
    ) {
        requestManager
            .load(url)
            .apply(
                requestOptions.autoClone()
                    .placeholder(placeholder)
                    .error(error)
                    .fallback(error)
            )
            .apply {
                if (crossFade) {
                    transition(DrawableTransitionOptions.withCrossFade())
                }
                //RoundedCorners 与 CenterCrop 冲突
                //必须先 CenterCrop ,...，后RoundedCorners
                val list = mutableListOf<BitmapTransformation>()
                if (centerCrop) {
                    list.add(CenterCrop())
                }
                if (transformation != null) {
                    list.add(transformation)
                }
                if (radius > 0) {
                    list.add(RoundedCorners(radius))
                }
                transform(*list.toTypedArray())
                if (callback != null) {
                    addCallback(callback)
                }
            }
            .into(view)

    }

    fun displayAvatar(
        view: ImageView,
        url: String?,
        clipTop: Boolean = true,//经纪人头像需裁剪上半部分
//        placeholder: Int = R.drawable.ic_launcher_background,
//        error: Int = R.drawable.ic_launcher_background
    ) {
        requestManager
            .load(url)
            .apply(
                requestOptions.autoClone()
//                    .placeholder(placeholder)
//                    .error(error)
                    .transform(if (clipTop) TopCircleCrop() else CircleCrop())
            )
            .into(view)
    }

    fun resources(view: ImageView, id: Int) {
        requestManager.load(id).into(view)
    }

    private fun RequestBuilder<Drawable>.addCallback(block: (Boolean) -> Unit): RequestBuilder<Drawable> {

        return this.addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                block.invoke(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?, model: Any?, target: Target<Drawable>?,
                dataSource: DataSource?, isFirstResource: Boolean
            ): Boolean {
                block.invoke(true)
                return false
            }
        })
    }

}