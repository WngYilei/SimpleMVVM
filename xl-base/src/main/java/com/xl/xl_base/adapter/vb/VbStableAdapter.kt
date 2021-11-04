package com.xl.xl_base.adapter.vb

import android.util.SparseArray
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.*
import com.xl.myapplication.adapter.DiffConfig
import com.xl.xl_base.adapter.recycler.RecyclerSupport

/**
 * 适配器（适用详情页面）
 */
class VbStableAdapter(private val support: RecyclerSupport) : RecyclerView.Adapter<VbRecyclerVH>() {

    val currentList: MutableList<VbItemCell>
        get() = differ.currentList

    private val differ = AsyncListDiffer(AdapterListUpdateCallback(this), DiffConfig.defaultVb())
    private val keyList = mutableListOf<Int>()
    private val sparseArray by lazy {
        SparseArray<List<VbItemCell>>()
    }

    override fun getItemViewType(position: Int) = differ.currentList[position].layoutResId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VbRecyclerVH {
        differ.currentList.forEach {
            if (viewType == it.layoutResId()) {
                return it.onCreateViewHolder(parent, support)
            }
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
     * 按照范围提交数据
     * @param position 局部更新的位置，范围>=0
     */
    fun submitList(
        @IntRange(from = 0) position: Int, temp: List<VbItemCell>,
        callback: () -> Unit = {}
    ) {
        if (!keyList.contains(position)) {
            keyList.add(position)
        }
        sparseArray.put(position, temp)
        val resultList = mutableListOf<VbItemCell>()
        keyList.sorted().forEach { key ->
            resultList.addAll(sparseArray[key, mutableListOf()])
        }
        differ.submitList(resultList, callback)
    }

    /**
     * 按照范围提交数据
     * @param position 局部更新的位置，范围>=0
     */
    fun submitList(
        @IntRange(from = 0) position: Int, itemCell: VbItemCell,
        callback: () -> Unit = {}
    ) {
        val temp = listOf(itemCell)
        submitList(position, temp, callback)
    }

    /**
     * 全量更新数据
     */
    fun submitList(temp: List<VbItemCell>, callback: () -> Unit = {}) {
        keyList.clear()
        sparseArray.clear()
        differ.submitList(temp, callback)
    }

    /**
     * 按照范围提交数据
     * @param position 局部更新的位置，范围>=0
     * @param clear 是否清空之前的列表
     */
    fun submitList(
        @IntRange(from = 0) position: Int, temp: List<VbItemCell>,
        clear: Boolean = false, callback: () -> Unit = {}
    ) {
        if (clear) {
            keyList.clear()
            sparseArray.clear()
            submitList(listOf()) {
                submitList(position, temp, callback)
            }
        } else {
            submitList(position, temp, callback)
        }
    }
}