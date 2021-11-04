package com.xl.xl_base.adapter.vb

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.xl.xl_base.adapter.recycler.RecyclerSupport
import com.xl.xl_base.adapter.vb.VbItemCell

open class VbRecyclerVH( vb: ViewBinding, val support: RecyclerSupport) :
    RecyclerView.ViewHolder(vb.root) {

    open fun bind(itemCell: VbItemCell, payloads: MutableList<Any> = mutableListOf()) {
        //empty
    }
}