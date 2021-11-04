package com.xl.simplemvvm.item

import android.view.View
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.recycler.RecyclerVH
import com.xl.simplemvvm.R
import com.xl.simplemvvm.bean.BannerImg
import com.xl.xl_base.adapter.recycler.RecyclerSupport
import kotlinx.android.synthetic.main.item_layout.view.*

class TitleItem(val data: BannerImg) : ItemCell {
    override fun layoutResId(): Int = R.layout.item_layout

    override fun itemId(): String = "TitleItem"
    override fun itemContent(): String = "TitleItem"
    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        TitleHoller(itemView, support)

    class TitleHoller(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
        override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
            val cell = itemCell as TitleItem
            itemView.tv_item.text = cell.data.title
            itemView.tv_item.setOnClickListener {
                support.simpleCallback?.let { it1 -> it1(layoutPosition) }
            }
        }
    }
}


