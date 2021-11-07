package com.xl.xl_base.tool.ktx

import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xl.xl_base.adapter.vb.VbItemCell

/**
 * RecyclerView回滚到顶部，当父控件为[SmartRefreshLayout]则调用下拉刷新
 */
fun RecyclerView.autoRefresh(target: Int = 3) {
    val layoutManager = this.layoutManager
    if (layoutManager is LinearLayoutManager) {
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (position > -1) {
            if (position > target) {
                layoutManager.scrollToPosition(target)
            }
            smoothScrollToPosition(0)
        }
        val parent = this.parent
        if (parent is SmartRefreshLayout) {
            parent.autoRefresh()
        }
    }
}

fun SmartRefreshLayout.xfLoadComplete(pageIndex: Int, total: Int, pageCount: Int = 10) {
    loadComplete(pageIndex + pageCount, total)
}

/**
 * 分页加载完成，pageIndex表示页码，第一页：1，第二页：2
 */
fun SmartRefreshLayout.loadFinish(pageIndex: Int, total: Int, pageCount: Int = 10) {
    loadComplete((pageIndex) * pageCount, total)
}

/**
 * 加载完成
 *
 * @param displayed 已加载数量
 * @param total 列表总数量
 */
fun SmartRefreshLayout.loadComplete(displayed: Int, total: Int) {
    val noMoreData = displayed >= total
    when (state) {
        RefreshState.Refreshing -> {//下拉刷新
            finishRefresh()
            postDelayed({
                setNoMoreData(noMoreData)
            }, 400)
        }
        RefreshState.Loading -> {//加载更多
            if (noMoreData) {
                finishLoadMoreWithNoMoreData()
            } else {
                finishLoadMore()
            }
        }
        else -> {
            setNoMoreData(noMoreData)
        }
    }
}

fun SmartRefreshLayout.scrollTop(target: Int = 3) {
    children.forEach {
        val recyclerView = it.isClass<RecyclerView>()
        val layoutManager = recyclerView?.layoutManager?.isClass<LinearLayoutManager>()
        layoutManager?.also { lm ->
            val position = lm.findFirstCompletelyVisibleItemPosition()
            if (position > -1) {
                if (position > target) {
                    layoutManager.scrollToPosition(target)
                }
                recyclerView.smoothScrollToPosition(0)
            }
        }
    }
}

/**
 * [SmartRefreshLayout]刷新、加载更多回调
 */
class SmartRefreshCallback : OnRefreshLoadMoreListener {

    private var refresh: (() -> Unit)? = null
    private var loadMore: (() -> Unit)? = null

    infix fun onRefresh(block: () -> Unit) {
        refresh = block
    }

    infix fun onLoadMore(block: () -> Unit) {
        loadMore = block
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        loadMore?.invoke()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        refresh?.invoke()
    }
}

/**
 * [SmartRefreshLayout]设置刷新、更多回调
 */
inline fun SmartRefreshLayout.onSmartRefreshCallback(crossinline callback: SmartRefreshCallback.() -> Unit) {
    setOnRefreshLoadMoreListener(SmartRefreshCallback().apply(callback))
}

inline fun <reified CELL : VbItemCell, reified VB : ViewBinding> checkTarget(
    cell: VbItemCell, vb: ViewBinding, crossinline target: (targetCell: CELL, targetVB: VB) -> Unit
) {
    if (cell is CELL && vb is VB)
        target.invoke(cell, vb)
}

