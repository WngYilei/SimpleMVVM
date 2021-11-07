package com.xl.xl_base.tool.ktx

import android.content.res.Resources

/**
 * 描述:Density扩展
 *
 * author zys
 * create by 2020/5/27
 */
//dp转px
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Number.dp: Int
    get() = when (this) {
        is Int -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        is Float -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        is Double -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        else -> (this.toInt() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

val Number.dpf: Float
    get() = when (this) {
        is Int -> this * Resources.getSystem().displayMetrics.density + 0.5f
        is Float -> this * Resources.getSystem().displayMetrics.density + 0.5f
        is Double -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toFloat()
        else -> this.toInt() * Resources.getSystem().displayMetrics.density + 0.5f
    }