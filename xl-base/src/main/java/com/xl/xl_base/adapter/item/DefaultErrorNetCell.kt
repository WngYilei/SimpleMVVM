package com.xl.xl_base.adapter.item

import android.view.View
import com.centanet.android.ktx.debounceClick
import com.xl.xl_base.R
import com.xl.xl_base.adapter.recycler.RecyclerSupport
import com.xl.xl_base.adapter.recycler.RecyclerVH

/**
 * 默認Error - 网络出错
 */
class DefaultErrorNetCell : ItemCell {

    override fun layoutResId() = R.layout.item_default_error

    override fun itemId() = "DefaultErrorCell"

    override fun itemContent() = "DefaultErrorCell"

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport) =
        DefaultErrorNetVH(itemView, support)
}

class DefaultErrorNetVH(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {

    init {
        itemView.debounceClick {
            support.retry?.invoke()
        }
    }
}