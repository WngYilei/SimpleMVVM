package com.xl.xl_base.tool.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

object ViewUtil {

    fun createDrawable(
        fillColor: Int, radius: Float, strokeColor: Int = Color.TRANSPARENT, strokeWidth: Int = 0
    ): Drawable = GradientDrawable().apply {
        setColor(fillColor)
        cornerRadius = radius
        setStroke(strokeWidth, strokeColor)
    }
}