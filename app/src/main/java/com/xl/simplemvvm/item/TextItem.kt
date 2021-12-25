package com.xl.simplemvvm.item

import android.view.View
import com.xl.simplemvvm.R
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.recycler.RecyclerSupport
import com.xl.xl_base.adapter.recycler.RecyclerVH
import kotlinx.android.synthetic.main.item_text.view.*

class TextItem(val data: String) : ItemCell {

    override fun layoutResId(): Int = R.layout.item_text

    override fun itemId(): String = "TextItem"
    override fun itemContent(): String = "$data"
    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        TextHoller(itemView, support)

    class TextHoller(itemView: View, support: RecyclerSupport) :
        RecyclerVH(itemView, support) {
        override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
            val item = itemCell as TextItem
            itemView.tv_item_text.text = item.data
        }
    }
}