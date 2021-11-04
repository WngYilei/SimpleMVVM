package com.xl.xl_base.adapter.item

import android.view.View
import com.xl.xl_base.R
import com.xl.xl_base.adapter.recycler.RecyclerSupport
import com.xl.xl_base.adapter.recycler.RecyclerVH

/**
 * 默認Empty
 */
class DefaultEmptyCell : SortedItemCell {

    override fun order(): Long = 0

    override fun layoutResId() = R.layout.item_default_empty

    override fun itemId() = "DefaultEmptyCell"

    override fun itemContent() = "DefaultEmptyCell"

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport) =
        DefaultEmptyVH(itemView, support)
}

class DefaultEmptyVH(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support)