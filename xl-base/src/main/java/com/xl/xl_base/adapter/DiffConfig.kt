package com.xl.xl_base.adapter

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.item.MenuCell
import com.xl.xl_base.adapter.vb.VbItemCell

/**
 * 描述:
 *
 * author zys
 * create by 2020/11/5
 */
object DiffConfig {

    private val COMPARATOR = object : DiffUtil.ItemCallback<ItemCell>() {
        override fun areItemsTheSame(oldItem: ItemCell, newItem: ItemCell) =
            oldItem.layoutResId() == newItem.layoutResId() && oldItem.itemId() == newItem.itemId()


        override fun areContentsTheSame(oldItem: ItemCell, newItem: ItemCell) =
            oldItem.itemContent() == newItem.itemContent()

        override fun getChangePayload(oldItem: ItemCell, newItem: ItemCell): Any = "update"
    }

    fun default(): AsyncDifferConfig<ItemCell> = AsyncDifferConfig.Builder(COMPARATOR).build()

    fun defaultVb(): AsyncDifferConfig<VbItemCell> {
        return AsyncDifferConfig.Builder(object :
            DiffUtil.ItemCallback<VbItemCell>() {
            override fun areItemsTheSame(oldItem: VbItemCell, newItem: VbItemCell) =
                oldItem.layoutResId() == newItem.layoutResId() && oldItem.itemId() == newItem.itemId()


            override fun areContentsTheSame(oldItem: VbItemCell, newItem: VbItemCell) =
                oldItem.itemContent() == newItem.itemContent()

            override fun getChangePayload(oldItem: VbItemCell, newItem: VbItemCell): Any = "update"
        }).build()
    }

    fun districtMenu(): AsyncDifferConfig<MenuCell> {
        return AsyncDifferConfig.Builder(object :
            DiffUtil.ItemCallback<MenuCell>() {
            override fun areItemsTheSame(oldItem: MenuCell, newItem: MenuCell) =
                oldItem.layoutResId() == newItem.layoutResId() && oldItem.itemType() == newItem.itemType()


            override fun areContentsTheSame(oldItem: MenuCell, newItem: MenuCell) =
                oldItem.itemText() == newItem.itemText() && newItem.itemType() == "不限"

            override fun getChangePayload(oldItem: MenuCell, newItem: MenuCell): Any = "update"
        }).build()
    }

}