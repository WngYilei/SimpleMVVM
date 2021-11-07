package com.xl.xl_base.adapter.recycler

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.*
import com.xl.xl_base.adapter.DiffConfig
import com.xl.xl_base.adapter.item.DefaultEmptyCell
import com.xl.xl_base.adapter.item.DefaultLoadingCell
import com.xl.xl_base.adapter.item.ItemCell

/**
 * 适配器（适用详情页面）
 */
class StableAdapter(private val support: RecyclerSupport) : RecyclerView.Adapter<RecyclerVH>() {

    val currentList: MutableList<ItemCell>
        get() = differ.currentList

    private val differ = AsyncListDiffer(AdapterListUpdateCallback(this), DiffConfig.default())
    private val keyList = mutableListOf<Int>()
    private val sparseArray by lazy {
        SparseArray<List<ItemCell>>()
    }

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
        loadingCell: ItemCell = DefaultLoadingCell(),
    ) {
        differ.submitList(mutableListOf(loadingCell))
    }

    /**
     * 提交错误页面cell
     * @param pageIndex 页码
     * @param errorCell 错误页码Cell
     * @param callback 加载完成回调
     */
    fun submitErrorCell(
        errorCell: ItemCell = DefaultEmptyCell(),
    ) {
        differ.submitList(mutableListOf(errorCell))
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
        list: List<ItemCell>,
        isRefresh: Boolean = false,
        emptyCell: ItemCell = DefaultEmptyCell(),
        callback: () -> Unit = {}
    ) {
        if (isRefresh) {
            keyList.clear()
            sparseArray.clear()
            submitList(listOf()) {
                submitList(pageIndex, list)
            }
            return
        }
        when (pageIndex) {
            1 -> {
                if (list.isEmpty()) {
                    differ.submitList(mutableListOf(emptyCell)) {
                        callback.invoke()
                    }
                } else {

                    if (!keyList.contains(pageIndex)) {
                        keyList.add(pageIndex)
                    }
                    sparseArray.put(pageIndex, list)
                    val resultList = mutableListOf<ItemCell>()
                    resultList.addAll(differ.currentList)
                    keyList.sorted().forEach { key ->
                        resultList.addAll(sparseArray[key, mutableListOf()])
                    }
                    differ.submitList(resultList, callback)
                }
            }
            else -> {
                if (!keyList.contains(pageIndex)) {
                    keyList.add(pageIndex)
                }
                sparseArray.put(pageIndex, list)
                val resultList = mutableListOf<ItemCell>()
                resultList.addAll(differ.currentList)
                keyList.sorted().forEach { key ->
                    resultList.addAll(sparseArray[key, mutableListOf()])
                }
                differ.submitList(resultList, callback)
            }
        }
    }

    private fun submitList(temp: List<ItemCell>, callback: () -> Unit = {}) {
        keyList.clear()
        sparseArray.clear()
        differ.submitList(temp, callback)
    }
}