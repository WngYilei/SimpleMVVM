package com.xl.xl_base.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.*
import com.xl.myapplication.adapter.DiffConfig
import com.xl.xl_base.adapter.item.ItemCell

/**
 * 通用列表适配器
 */
class RecyclerAdapter(private val support: RecyclerSupport) : RecyclerView.Adapter<RecyclerVH>() {

    val currentList: MutableList<ItemCell>
        get() = differ.currentList

    private val differ = AsyncListDiffer(AdapterListUpdateCallback(this), DiffConfig.default())

    override fun getItemViewType(position: Int) = differ.currentList[position].layoutResId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVH {
        differ.currentList.forEach {
            if (viewType == it.layoutResId()) {
                return it.onCreateViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                    ), support
                )
            }
        }
        throw IllegalArgumentException("viewType not found")
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerVH, position: Int, payloads: MutableList<Any>) {
        holder.bind(differ.currentList[position], payloads)
    }

    override fun onBindViewHolder(holder: RecyclerVH, position: Int) {
        //empty
    }

    /**
     * 提交加载页面cell
     * @param loadingCell 加载页码Cell
     * @param callback 加载完成回调
     */
    fun submitLoadingCell(
        loadingCell: ItemCell,
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
        @IntRange(from = 1) pageIndex: Int, errorCell: ItemCell,
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
        @IntRange(from = 1) pageIndex: Int?,
        list: List<ItemCell>,
//        emptyCell: ItemCell ,
        callback: () -> Unit = {}
    ) {
        when (pageIndex) {
            1 -> {
                if (list.isEmpty()) {
//                    differ.submitList(mutableListOf(emptyCell)) {
//                        callback.invoke()
//                    }
                } else {
                    differ.submitList(list) {
                        callback.invoke()
                    }
                }
            }
            else -> {
                val temp = mutableListOf<ItemCell>()
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