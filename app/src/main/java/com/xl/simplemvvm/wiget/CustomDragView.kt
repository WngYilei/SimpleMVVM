package com.xl.simplemvvm.wiget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper


class DragViewGroup constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var dragViewId: Int = -1
    private var rectTop: Int = -1
    private var rectBottom: Int = -1

    private var finalLeft: Int = 0
    private var finalTop: Int = 0

    private var clickCallBack: (() -> Unit)? = null

    fun setDragViewClickCallBack(temp: () -> Unit) {
        clickCallBack = temp
    }

    private val viewDragCallback = object : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean = child.id == dragViewId

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int = left

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return when {
                top > (rectBottom - child.height) -> (rectBottom - child.height)
                top < rectTop -> rectTop
                else -> top
            }
        }

        override fun onViewPositionChanged(
            changedView: View, left: Int, top: Int, dx: Int, dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            finalLeft = left
            finalTop = top
            changedView.layout(
                left, top, left + changedView.width, top + changedView.height
            )
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val finalLeft =
                if (releasedChild.left + releasedChild.right > this@DragViewGroup.width) {
                    //在右侧
                    this@DragViewGroup.width - releasedChild.width
                } else {
                    //在左侧
                    0
                }
            viewDragHelper.smoothSlideViewTo(releasedChild, finalLeft, releasedChild.top)
            ViewCompat.postInvalidateOnAnimation(this@DragViewGroup)
            this@DragViewGroup.finalLeft = finalLeft
            this@DragViewGroup.finalTop = releasedChild.top
            releasedChild.layout(
                finalLeft, releasedChild.top, finalLeft + releasedChild.width, releasedChild.bottom
            )
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            isMoving = ViewDragHelper.STATE_IDLE != state

        }
    }
    private val viewDragHelper: ViewDragHelper = ViewDragHelper.create(this, 1f, viewDragCallback)

    fun initialize(tempRectTop: Int, tempRectBottom: Int, dragResId: Int) {
        rectTop = tempRectTop
        rectBottom = tempRectBottom
        dragViewId = dragResId
        val dragView = findViewById<View>(dragViewId)
        finalLeft = resources.displayMetrics.widthPixels - dragView.width
        finalTop = rectBottom - dragView.height
    }

    private var isMoving: Boolean = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isMoving = false
            }
            MotionEvent.ACTION_MOVE -> {
                isMoving = true
            }
            MotionEvent.ACTION_UP -> {
                if (!isMoving) {
                    performClick()
                }
            }
        }
        return true
    }

    private var clickTime: Long = 0L
    override fun performClick(): Boolean {
        if (System.currentTimeMillis() - clickTime > 1000L) {
            clickTime = System.currentTimeMillis()
            clickCallBack?.invoke()
        }
        return super.performClick()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val dragView = findViewById<View>(dragViewId)

        return if (ev.x in dragView.left.toFloat()..dragView.right.toFloat() &&
            ev.y in dragView.top.toFloat()..dragView.bottom.toFloat()
        ) true else viewDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        findViewById<View>(dragViewId)?.also {
            it.layout(finalLeft, finalTop, finalLeft + it.width, finalTop + it.height)
        }
    }
}