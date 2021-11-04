package com.xl.xl_base.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

/**
 * 描述:  BaseDialogFragment
 */
abstract class BaseDialogFragment : DialogFragment() {

    /**
     * 范围是从1.0（完全不透明）到0.0（不模糊）
     */
    open val dimAmount = 0.5f

    /**
     * 点击空白Dialog是否消失
     */
    open val isOutCancel: Boolean = false

    /**
     * 按返回键Dialog是否消失
     */
    open val backPressedEnable: Boolean = true

    /**
     * 左右padding
     */
    open val paddingStartAndEnd: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_TITLE, R.style.ZF_Dialog_Center_Style)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(isOutCancel)
            setCancelable(isOutCancel)
            setOnKeyListener { _: DialogInterface?, keyCode: Int, _: KeyEvent? ->
                if (backPressedEnable) {
                    dismissDialog()
                }
                keyCode == KeyEvent.KEYCODE_BACK && !backPressedEnable
            }
        }
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    abstract fun getLayoutRes(): Int

    abstract fun bindView(view: View)

    override fun onStart() {
        super.onStart()
        dialog?.window?.also {
            it.decorView.setPadding(paddingStartAndEnd, 0, paddingStartAndEnd, 0)
            val params = it.attributes
            params.dimAmount = this.dimAmount
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.gravity = Gravity.CENTER
            it.attributes = params
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissDialog()
    }

    fun dismissDialog() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }
}