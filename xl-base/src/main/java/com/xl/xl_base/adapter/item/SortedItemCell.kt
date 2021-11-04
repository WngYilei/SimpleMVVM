package com.xl.xl_base.adapter.item
interface SortedItemCell : ItemCell {

    /**
     *  排序标志
     */
    fun order(): Long
}