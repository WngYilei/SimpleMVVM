package com.xl.xl_base.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import com.xl.xl_base.R
import kotlinx.android.synthetic.main.lottie_header.view.*
class LottieHeader @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    InternalAbstract(context, attrs, defStyleAttr), RefreshHeader {

    init {
        View.inflate(context, R.layout.lottie_header, this)
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)
        if (isDragging) {
            lottieView.progress = percent
        }
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {

        lottieView.playAnimation()
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        lottieView.cancelAnimation()
        return super.onFinish(refreshLayout, success)
    }
}