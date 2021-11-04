package com.xl.xl_base.adapter.vb

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.*
import com.xl.myapplication.adapter.DiffConfig
import com.xl.xl_base.adapter.recycler.RecyclerSupport

/**
 * 通用列表适配器
 */
@Suppress("unused")
class VbRecyclerAdapter(private val support: RecyclerSupport) :
    RecyclerView.Adapter<VbRecyclerVH>() {

    val currentList: MutableList<VbItemCell>
        get() = differ.currentList

    private val differ = AsyncListDiffer(AdapterListUpdateCallback(this), DiffConfig.defaultVb())

    override fun getItemViewType(position: Int) = differ.currentList[position].layoutResId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VbRecyclerVH {
        differ.currentList.forEach {
            if (viewType == it.layoutResId())
                return it.onCreateViewHolder(parent, support)
        }
        throw IllegalArgumentException("viewType not found")
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: VbRecyclerVH, position: Int, payloads: MutableList<Any>) {
        holder.bind(differ.currentList[position], payloads)
    }

    override fun onBindViewHolder(holder: VbRecyclerVH, position: Int) {
        //empty
    }

    /**
     * 提交加载页面cell
     * @param loadingCell 加载页码Cell
     * @param callback 加载完成回调
     */
    fun submitLoadingCell(
        loadingCell: VbItemCell,
        callback: () -> Unit = {}
    ) {
        differ.submitList(mutableListOf(loadingCell)) {
            callback.invoke()
        }
    }

    /**
     * 提交错误页面cell
     * @param pageIndex 页码
     * @param errorCell 错误页码Cell
     * @param callback 加载完成回调
     */
    fun submitErrorCell(
        @IntRange(from = 1) pageIndex: Int, errorCell: VbItemCell,
        callback: () -> Unit = {}
    ) {
        if (pageIndex == 1) {
            differ.submitList(mutableListOf(errorCell)) {
                callback.invoke()
            }
        } else {
            callback.invoke()
        }
    }

    /**
     * 提交列表数据
     * @param pageIndex 页码
     * @param list 列表数据
     * @param emptyCell 空页面
     * @param callback 加载完成回调
     *
     */
    fun submitList(
        @IntRange(from = 1) pageIndex: Int,
        list: List<VbItemCell>,
        emptyCell: VbItemCell,
        callback: () -> Unit = {}
    ) {
        when (pageIndex) {
            1 -> {
                if (list.isEmpty()) {
                    differ.submitList(mutableListOf(emptyCell)) {
                        callback.invoke()
                    }
                } else {
                    differ.submitList(list) {
                        callback.invoke()
                    }
                }
            }
            else -> {
                val temp = mutableListOf<VbItemCell>()
                temp.addAll(differ.currentList)
                if (list.isEmpty()) {
                    callback.invoke()
                } else {
                    temp.addAll(list)
                    differ.submitList(temp) {
                        callback.invoke()
                    }
                }
            }
        }
    }
}