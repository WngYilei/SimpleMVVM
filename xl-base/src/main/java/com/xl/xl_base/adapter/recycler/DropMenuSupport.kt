package com.xl.xl_base.adapter.recycler

import android.util.SparseArray
import android.util.SparseBooleanArray
import androidx.core.util.forEach
import androidx.core.util.putAll

open class DropMenuSupport {
    //
    val curAreaArray = floatArrayOf(-1F, -1F)
    val lastAreaArray = floatArrayOf(-1F, -1F)

    //  点击监听
    var simpleCallback: ((position: Int) -> Unit)? = null

    //输入框焦点监听
    var inputFocusChange: ((Int, Boolean) -> Unit)? = null

    //记录每组选中
    private val arrMap = SparseArray<SparseBooleanArray>()

    //缓存上一次选中的position
    private val lastMap = SparseArray<SparseBooleanArray>()

    fun setStatus(groupIndex: Int, position: Int, flag: Boolean) {
        val select = arrMap.get(groupIndex, SparseBooleanArray())
        select.put(position, flag)
        arrMap.put(groupIndex, select)
    }

    fun nextStatus(groupIndex: Int, position: Int) {
        val select = arrMap.get(groupIndex, SparseBooleanArray())
        select.put(position, !select.get(position, false))
        arrMap.put(groupIndex, select)
    }

    /**
     * 清除当前组的筛选
     */
    fun clearGroup(groupIndex: Int) {
        arrMap.remove(groupIndex)
    }

    /**
     * 清除其他组的筛选
     */
    fun clearOtherGroup(groupIndex: Int) {
        val curGroup = arrMap.get(groupIndex)
        arrMap.clear()
        curGroup?.let { arrMap.put(groupIndex, curGroup) }
    }

    /**
     * 获取当前组当前位置的选中状态
     */
    fun getStatus(groupIndex: Int, position: Int): Boolean {
        return arrMap.get(groupIndex, SparseBooleanArray()).get(position, false)
    }

    fun getAll(): SparseBooleanArray {
        val temp = SparseBooleanArray()
        arrMap.forEach { _, value ->
            temp.putAll(value)
        }
        return temp
    }

    fun resetArrayMap() {
        arrMap.clear()
    }

    fun clearLastGroup(groupIndex: Int) {
        lastMap.remove(groupIndex)
    }

    fun resetLastMap() {
        lastMap.clear()
    }

    fun resetEditCache() {
        lastAreaArray[0] = -1f
        lastAreaArray[1] = -1f
    }

    fun resetEdit() {
        curAreaArray[0] = -1f
        curAreaArray[1] = -1f
    }

    /**
     * @param isFlag true 将选中的 同步到 缓存
     *               false 将缓存 同步为选中
     */
    fun syncCache(isFlag: Boolean = true) {
        if (isFlag) {
            lastMap.clear()
            lastMap.putAll(arrMap)
        } else {
            arrMap.clear()
            arrMap.putAll(lastMap)
        }
    }

    fun syncCacheEditText(isFlag: Boolean = true) {
        if (isFlag) {
            lastAreaArray[0] = curAreaArray[0]
            lastAreaArray[1] = curAreaArray[1]
        } else {
            curAreaArray[0] = lastAreaArray[0]
            curAreaArray[1] = lastAreaArray[1]
        }
    }

    private fun copyMap(
        from: SparseArray<SparseBooleanArray>,
        to: SparseArray<SparseBooleanArray>
    ) {
        to.clear()
        from.forEach { key, value ->
            to.put(key, SparseBooleanArray().apply {
                putAll(value)
            })
        }
    }

    fun syncCache2(isFlag: Boolean = true) {
        if (isFlag) {
            copyMap(arrMap, lastMap)
        } else {
            copyMap(lastMap, arrMap)
        }
    }


}