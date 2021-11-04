package com.xl.xl_base.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.item.SortedItemCell

class SortedRecyclerAdapter(
    private val support: RecyclerSupport,
    private val isRevers: Boolean = false
) :
    RecyclerView.Adapter<RecyclerVH>() {

    private lateinit var loadingCell: SortedItemCell
    private lateinit var emptyCell: SortedItemCell

    private val list = SortedList(
        SortedItemCell::class.java,
        object : SortedListAdapterCallback<SortedItemCell>(this) {

            override fun areItemsTheSame(item1: SortedItemCell?, item2: SortedItemCell?): Boolean {
                return item1?.itemId() == item2?.itemId()
            }

            override fun compare(o1: SortedItemCell?, o2: SortedItemCell?): Int {
                val order1 = o1?.order() ?: 0
                val order2 = o2?.order() ?: 0
                return if (isRevers) order2.compareTo(order1) else order1.compareTo(order2)
            }

            override fun areContentsTheSame(
                oldItem: SortedItemCell?,
                newItem: SortedItemCell?
            ): Boolean {
                return oldItem?.itemContent() == newItem?.itemContent()
            }

        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVH {
        for (index in 0 until list.size()) {
            if (viewType == list[index].layoutResId()) {
                return list[index].onCreateViewHolder(
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

    override fun getItemViewType(position: Int): Int {
        return list[position].layoutResId()
    }

    override fun getItemCount(): Int {
        return list.size()
    }

    override fun onBindViewHolder(holder: RecyclerVH, position: Int) {
        //empty
    }

    override fun onBindViewHolder(holder: RecyclerVH, position: Int, payloads: MutableList<Any>) {
        holder.bind(list[position], payloads)
    }

    fun submitItem(cell: SortedItemCell) {
        finishLoading()
        if (this::emptyCell.isInitialized)
            list.remove(emptyCell)
        list.add(cell)
    }

    fun updateItemAt(position: Int, cell: SortedItemCell) {
        finishLoading()
        list.updateItemAt(position, cell)
    }

    fun removeCell(tempCell: SortedItemCell){
        list.remove(emptyCell)
    }

    fun clearAndSubmitList(tempList: List<SortedItemCell>, block: () -> Unit = {}) {
        finishLoading()
        list.clear()
        list.beginBatchedUpdates()
        list.addAll(tempList)
        list.endBatchedUpdates()
        block.invoke()
    }

    fun submitList(tempList: List<SortedItemCell>, block: () -> Unit = {}) {
        finishLoading()
        if (!tempList.isNullOrEmpty() && this::emptyCell.isInitialized)
            list.remove(emptyCell)
        list.beginBatchedUpdates()
        list.addAll(tempList)
        list.endBatchedUpdates()
        block.invoke()
    }

    fun notifyItemSortAtPosition(position: Int, payload: String ) {
        notifyItemChanged(position, payload)
        list.recalculatePositionOfItemAt(position)
    }

    fun currentList() = list

    fun getItem(position: Int): ItemCell? {
        return if (position in 0 until list.size())
            list[position] else null
    }

    inline fun <reified T> findItemIdAndCell(id: String): Pair<Int, T>? {
        for (index in 0 until currentList().size()) {
            val item = currentList()[index]
            if (item.itemId() == id && item is T) {
                return Pair(index, item)
            }
        }
        return null
    }

    fun findFirst(): SortedItemCell? =
        if (list.size() > 0) {
            list[0]
        } else null

    fun findLast(): SortedItemCell? =
        if (list.size() > 0) {
            list[list.size() - 1]
        } else null

    /**
     * 提交加载页面cell
     * @param loadingCell 加载页码Cell
     */
    fun submitLoadingCell(
        loadingCell: SortedItemCell
    ) {
        this.loadingCell = loadingCell
        list.add(this.loadingCell)
    }

    /**
     * 提交空页面cell
     * @param emptyCell 空页码Cell
     */
    fun submitEmptyCell(
        emptyCell: SortedItemCell
    ) {
        finishLoading()
        this.emptyCell = emptyCell
        list.add(this.emptyCell)
    }

    /**
     * 加载结束
     */
    private fun finishLoading() {
        if (this::loadingCell.isInitialized)
            list.remove(this.loadingCell)
    }
}