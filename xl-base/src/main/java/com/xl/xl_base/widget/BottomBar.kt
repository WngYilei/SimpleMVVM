package com.xl.xl_base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class BottomBar : View {
    private var containerId: Int = 0
    private val fragmentClassList = ArrayList<Class<*>>()
    private val titleList = ArrayList<String>()
    private val iconResBeforeList = ArrayList<Int>()
    private val iconResAfterList = ArrayList<Int>()

    private val fragmentList = ArrayList<Fragment>()

    private var itemCount: Int = 0

    private val paint = Paint()

    private val iconBitmapBeforeList = ArrayList<Bitmap?>()
    private val iconBitmapAfterList = ArrayList<Bitmap?>()
    private val iconRectList = ArrayList<Rect>()

    var currentIndex: Int = 0
        private set
    private var firstCheckedIndex: Int = 0

    private var titleColorBefore = Color.parseColor("#999999")
    private var titleColorAfter = Color.parseColor("#ff5d5e")

    private var titleSizeInDp = 10
    private var iconWidth = 20
    private var iconHeight = 20
    private var titleIconMargin = 5

    private var titleBaseLine: Int = 0
    private val titleXList = ArrayList<Int>()

    private var parentItemWidth: Int = 0


    private var target = -1

    private var currentFragment: Fragment? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private lateinit var listen: (Int) -> Unit

    fun setClickListen(listen: (Int) -> Unit) {
        this.listen = listen
    }


    fun setContainer(containerId: Int): BottomBar {
        this.containerId = containerId
        return this
    }

    fun setTitleBeforeAndAfterColor(beforeResCode: String, AfterResCode: String): BottomBar {
        titleColorBefore = Color.parseColor(beforeResCode)
        titleColorAfter = Color.parseColor(AfterResCode)
        return this
    }

    fun setTitleSize(titleSizeInDp: Int): BottomBar {
        this.titleSizeInDp = titleSizeInDp
        return this
    }

    fun setIconWidth(iconWidth: Int): BottomBar {
        this.iconWidth = iconWidth
        return this
    }

    fun setTitleIconMargin(titleIconMargin: Int): BottomBar {
        this.titleIconMargin = titleIconMargin
        return this
    }

    fun setIconHeight(iconHeight: Int): BottomBar {
        this.iconHeight = iconHeight
        return this
    }

    fun addItem(
        fragmentClass: Class<*>, title: String, iconResBefore: Int, iconResAfter: Int
    ): BottomBar {
        fragmentClassList.add(fragmentClass)
        titleList.add(title)
        iconResBeforeList.add(iconResBefore)
        iconResAfterList.add(iconResAfter)
        return this
    }


    fun setFirstChecked(firstCheckedIndex: Int): BottomBar {
        this.firstCheckedIndex = firstCheckedIndex
        return this
    }

    fun build() {
        itemCount = fragmentClassList.size
        for (i in 0 until itemCount) {
            val beforeBitmap = getBitmap(iconResBeforeList[i])
            iconBitmapBeforeList.add(beforeBitmap)

            val afterBitmap = getBitmap(iconResAfterList[i])
            iconBitmapAfterList.add(afterBitmap)

            val rect = Rect()
            iconRectList.add(rect)

            val clx = fragmentClassList[i]

            var fragment: Fragment? = null
            try {
                fragment = clx.newInstance() as Fragment
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            fragmentList.add(fragment!!)

        }

        currentIndex = firstCheckedIndex
        switchFragment(currentIndex)

        invalidate()
    }

    private fun getBitmap(resId: Int): Bitmap? {
        if (resId == 0) return null
        val bitmapDrawable = ContextCompat.getDrawable(context, resId) as BitmapDrawable
        return bitmapDrawable.bitmap
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initParam()
    }

    private fun initParam() {
        if (itemCount != 0) {
            //单个item宽高
            parentItemWidth = width / itemCount
            val parentItemHeight = height

            //图标边长
            val iconWidth = dp2px(this.iconWidth.toFloat())//先指定20dp
            val iconHeight = dp2px(this.iconHeight.toFloat())

            //图标文字margin
            val textIconMargin = dp2px(titleIconMargin.toFloat() / 2)

            //标题高度
            val titleSize = dp2px(titleSizeInDp.toFloat())//这里先指定10dp
            paint.textSize = titleSize.toFloat()
            val rect = Rect()
            paint.getTextBounds(titleList[0], 0, titleList[0].length, rect)
            val titleHeight = rect.height()

            //从而计算得出图标的起始top坐标、文本的baseLine
            val iconTop = (parentItemHeight - iconHeight - textIconMargin - titleHeight) / 2
            titleBaseLine = parentItemHeight - iconTop

            //对icon的rect的参数进行赋值
            val firstRectX = (parentItemWidth - iconWidth) / 2//第一个icon的左
            for (i in 0 until itemCount) {
                val rectX = i * parentItemWidth + firstRectX

                val temp = iconRectList[i]

                temp.left = rectX
                temp.top = iconTop
                temp.right = rectX + iconWidth
                temp.bottom = iconTop + iconHeight
            }

            //标题
            for (i in 0 until itemCount) {
                val title = titleList[i]
                paint.getTextBounds(title, 0, title.length, rect)
                titleXList.add((parentItemWidth - rect.width()) / 2 + parentItemWidth * i)
            }
        }
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)//这里让view自身替我们画背景 如果指定的话

        if (itemCount != 0) {
            //画背景
            paint.isAntiAlias = false
            for (i in 0 until itemCount) {
                var bitmap: Bitmap? = null
                bitmap = if (i == currentIndex) {
                    iconBitmapAfterList[i]
                } else {
                    iconBitmapBeforeList[i]
                }
                if (bitmap == null) continue
                val rect = iconRectList[i]
                canvas.drawBitmap(bitmap, null, rect, paint)//null代表bitmap全部画出
            }

            //画文字
            paint.isAntiAlias = true
            for (i in 0 until itemCount) {
                val title = titleList[i]
                if (i == currentIndex) {
                    paint.color = titleColorAfter
                } else {
                    paint.color = titleColorBefore
                }
                val x = titleXList[i]
                canvas.drawText(title, x.toFloat(), titleBaseLine.toFloat(), paint)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> target = withinWhichArea(event.x.toInt())
            MotionEvent.ACTION_UP -> {
                if (event.y >= 0 && target == withinWhichArea(event.x.toInt())) {
//                    listen(target)
                    switchFragment(target)
                    currentIndex = target
                    invalidate()
                }
                target = -1
            }
        }
        return true
    }

    private fun withinWhichArea(x: Int): Int {
        return x / parentItemWidth
    }

    private fun switchFragment(whichFragment: Int) {
        val fragment = fragmentList[whichFragment]
        val frameLayoutId = containerId
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        if (fragment.isAdded) {
            if (currentFragment != null) {
                transaction.hide(currentFragment!!).show(fragment)
            } else {
                transaction.show(fragment)
            }
        } else {
            if (currentFragment != null) {
                transaction.hide(currentFragment!!).add(frameLayoutId, fragment)
            } else {
                transaction.add(frameLayoutId, fragment)
            }
        }
        currentFragment = fragment
        transaction.commitAllowingStateLoss()
    }
}
