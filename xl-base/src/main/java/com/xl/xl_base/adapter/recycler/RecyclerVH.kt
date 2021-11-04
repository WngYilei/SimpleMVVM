package com.xl.xl_base.adapter.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.recycler.RecyclerSupport

open class RecyclerVH(itemView: View, val support: RecyclerSupport) : RecyclerView.ViewHolder(itemView) {

    open fun bind(itemCell: ItemCell, payloads: MutableList<Any> = mutableListOf()) {
        //empty
    }
}