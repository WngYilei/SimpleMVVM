package com.xl.xl_base.tool.util

import android.os.Handler
import android.widget.Toast
import android.os.Looper
import java.lang.UnsupportedOperationException

class ToastUtils private constructor() {
    companion object {
        private var sToast: Toast? = null
        private val sHandler = Handler(Looper.getMainLooper())
        private var isJumpWhenMore = false

        /**
         * 吐司初始化
         *
         * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
         *
         * `true`: 弹出新吐司<br></br>`false`: 只修改文本内容
         *
         * 如果为`false`的话可用来做显示任意时长的吐司
         */
        fun init(isJumpWhenMore: Boolean) {
            Companion.isJumpWhenMore = isJumpWhenMore
        }

        /**
         * 安全地显示短时吐司
         *
         * @param text 文本
         */
        fun showShortToastSafe(text: CharSequence) {
            sHandler.post { showToast(text, Toast.LENGTH_SHORT) }
        }

        /**
         * 安全地显示短时吐司
         *
         * @param resId 资源Id
         */
        fun showShortToastSafe(resId: Int) {
            sHandler.post { showToast(resId, Toast.LENGTH_SHORT) }
        }

        /**
         * 安全地显示短时吐司
         *
         * @param resId 资源Id
         * @param args  参数
         */
        fun showShortToastSafe(resId: Int, vararg args: Any?) {
            sHandler.post { showToast(resId, Toast.LENGTH_SHORT) }
        }


        /**
         * 安全地显示短时吐司
         *
         * @param format 格式
         * @param args   参数
         */
        fun showShortToastSafe(format: String) {
            sHandler.post { showToast(format, Toast.LENGTH_SHORT) }
        }

        /**
         * 安全地显示长时吐司
         *
         * @param text 文本
         */
        fun showLongToastSafe(text: CharSequence) {
            sHandler.post { showToast(text, Toast.LENGTH_LONG) }
        }

        /**
         * 安全地显示长时吐司
         *
         * @param resId 资源Id
         */
        fun showLongToastSafe(resId: Int) {
            sHandler.post { showToast(resId, Toast.LENGTH_LONG) }
        }



        /**
         * 安全地显示长时吐司
         *
         * @param format 格式
         * @param args   参数
         */
        fun showLongToastSafe(format: String) {
            sHandler.post { showToast(format, Toast.LENGTH_LONG) }
        }

        /**
         * 显示短时吐司
         *
         * @param text 文本
         */
        fun showShortToast(text: CharSequence) {
            showToast(text, Toast.LENGTH_SHORT)
        }

        /**
         * 显示短时吐司
         *
         * @param resId 资源Id
         */
        fun showShortToast(resId: Int) {
            showToast(resId, Toast.LENGTH_SHORT)
        }

        /**
         * 显示短时吐司
         *
         * @param format 格式
         * @param args   参数
         */
        fun showShortToast(format: String) {
            showToast(format, Toast.LENGTH_SHORT)
        }

        /**
         * 显示长时吐司
         *
         * @param text 文本
         */
        fun showLongToast(text: CharSequence) {
            showToast(text, Toast.LENGTH_LONG)
        }

        /**
         * 显示长时吐司
         *
         * @param resId 资源Id
         */
        fun showLongToast(resId: Int) {
            showToast(resId, Toast.LENGTH_LONG)
        }

        /**
         * 显示长时吐司
         *
         * @param format 格式
         * @param args   参数
         */
        fun showLongToast(format: String) {
            showToast(format, Toast.LENGTH_LONG)
        }

        /**
         * 显示吐司
         *
         * @param resId    资源Id
         * @param duration 显示时长
         */
        private fun showToast(resId: Int, duration: Int) {
            showToast(XApp.getApp().resources.getText(resId).toString(), duration)
        }


        /**
         * 显示吐司
         *
         * @param text     文本
         * @param duration 显示时长
         */
        private fun showToast(text: CharSequence, duration: Int) {
            if (isJumpWhenMore) cancelToast()
            if (sToast == null) {
                sToast = Toast.makeText(XApp.getApp(), text, duration)
            } else {
                sToast?.setText(text)
                sToast?.duration = duration
            }
            sToast?.show()
        }

        /**
         * 取消吐司显示
         */
        fun cancelToast() {
            if (sToast != null) {
                sToast!!.cancel()
                sToast = null
            }
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}