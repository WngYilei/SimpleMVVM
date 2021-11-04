package com.xl.common.tool.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/8
 */
object ViewUtil {

    /**
     * 构建一个drawable
     * @param fillColor 背景填充颜色
     * @param radius    圆角大小
     * @param strokeColor   边框颜色
     * @param strokeWidth   边框粗细
     */
    fun createDrawable(
        fillColor: Int, radius: Float, strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0
    ): Drawable = GradientDrawable().apply {
        setColor(fillColor)
        cornerRadius = radius
        setStroke(strokeWidth, strokeColor)
    }
}