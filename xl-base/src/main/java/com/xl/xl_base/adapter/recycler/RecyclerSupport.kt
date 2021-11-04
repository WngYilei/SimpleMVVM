package com.xl.xl_base.adapter.recycler

import android.util.Log
import android.view.View
import com.xl.xl_base.adapter.image.ImageLoader
import com.xl.xl_base.adapter.vb.VbRecyclerAdapter
import com.xl.xl_base.adapter.vb.VbStableAdapter
import com.xl.xl_base.adapter.callback.OnSuperLinkCallback

/**
 * 组装适配器需要的图片加载、点击回调
 */
open class RecyclerSupport(val pageCount: Int = 10) {

    /**
     * 图片加载
     */
    var imageLoader: ImageLoader? = null

    /**
     * 简单点击
     */
    var simpleCallback: ((position: Int) -> Unit)? = null

    /**
     * 同一个View多个事件，点击或长按
     */
    var clickCallback: ((position: Int, type: Int) -> Unit)? = null

    /**
     * 同一个item中多个View的监听
     */
    var clickViewCallback: ((view: View, position: Int) -> Unit)? = null

    /**
     * 错误页面item点击触发重试功能，
     */
    var retry: (() -> Unit)? = null

    /**
     * 空白页面Item点击监听
     */
    var emptyCallBack: (() -> Unit)? = null

    /**
     * 登录页面Item点击监听
     */
    var loginCallBack: (() -> Unit)? = null

    /**
     * 登录页面Item点击监听
     */
    var onSuperLinkCallback: OnSuperLinkCallback? = null

    /**
     * 详情页面Item点击事件
     */
    var detailClickCallback: ((position: Int, type: Int, value: Any?) -> Unit)? = null

    infix fun onSimpleCallback(block: (position: Int) -> Unit) {
        Log.e("TAG", "onSimpleCallback: ")
        simpleCallback = block
    }

    infix fun onClickCallback(block: (position: Int, type: Int) -> Unit) {
        clickCallback = block
    }


    infix fun onClickViewCallback(block: (view: View, position: Int) -> Unit) {
        clickViewCallback = block
    }

    infix fun onRetry(block: () -> Unit) {
        retry = block
    }

    infix fun onEmptyCallBack(block: () -> Unit) {
        emptyCallBack = block
    }

    infix fun onLoginCallBack(block: () -> Unit) {
        loginCallBack = block
    }

    infix fun onDetailClickCallback(block: (position: Int, type: Int, value: Any?) -> Unit) {
        detailClickCallback = block
    }
}

inline fun createAdapter(crossinline support: RecyclerSupport.() -> Unit): RecyclerAdapter {
    return RecyclerAdapter(RecyclerSupport().apply(support))
}

inline fun createStableAdapter(crossinline support: RecyclerSupport.() -> Unit): StableAdapter {
    return StableAdapter(RecyclerSupport().apply(support))
}

inline fun createVbAdapter(crossinline support: RecyclerSupport.() -> Unit): VbRecyclerAdapter {
    return VbRecyclerAdapter(RecyclerSupport().apply(support))
}

inline fun createVbStableAdapter(crossinline support: RecyclerSupport.() -> Unit): VbStableAdapter {
    return VbStableAdapter(RecyclerSupport().apply(support))
}

inline fun createSortedAdapter(
    crossinline support: RecyclerSupport.() -> Unit,
    isRevers: Boolean = false
): SortedRecyclerAdapter {

    return SortedRecyclerAdapter(RecyclerSupport().apply(support), isRevers)
}
