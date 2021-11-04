package com.xl.xl_base.adapter.item

import android.view.View
import androidx.annotation.LayoutRes
import com.xl.xl_base.adapter.recycler.RecyclerSupport
import com.xl.xl_base.adapter.recycler.RecyclerVH

interface ItemCell {

    @LayoutRes
    fun layoutResId(): Int

    /**
     * item标志，用于比较item是否一样
     */
    fun itemId(): String

    /**
     * item内容，用于比较内容是否一致
     */
    fun itemContent(): String

    /**
     * 创建ViewHolder
     */
    fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH

}