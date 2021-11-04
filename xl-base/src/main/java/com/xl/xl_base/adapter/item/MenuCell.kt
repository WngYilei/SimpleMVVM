package com.xl.xl_base.adapter.item

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.xl.xl_base.adapter.recycler.DropMenuSupport

interface MenuCell {
    @LayoutRes
    fun layoutResId(): Int

    /**
     * 所属的组
     */
    fun group(): Int

    /**
     * item标志
     */
    fun itemType(): String

    /**
     * item内容
     */
    fun itemText(): String

    /**
     * item值
     */

    fun itemValue(): String

    /**
     * 仅特色使用
     */
    fun id(): String = ""

    /**
     * 筛选参数
     */
    fun params(): MutableMap<String, Any>
}

open class MenuRecyclerVH(itemView: View, val support: DropMenuSupport) :
    RecyclerView.ViewHolder(itemView) {

    open fun bind(itemCell: MenuCell, payloads: MutableList<Any> = mutableListOf()) {
        //empty
    }
}


