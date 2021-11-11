package com.xl.xl_base.tool.ktx

import android.os.Bundle
import androidx.core.os.bundleOf

/**
 * 将map转换成Bundle
 */
fun convertToBundle(source: Map<String, Any>): Bundle {
    return bundleOf(*source.map { Pair(it.key, it.value) }.toTypedArray())
}

/**
 * Bundle 转 Map
 */
fun convertToMap(data: Bundle): MutableMap<String, Any> {
    val dataMap = mutableMapOf<String, Any>()
    data.keySet().forEach {
        val value = data.get(it)
        if (value != null) {
            dataMap[it] = value
        }
    }
    return dataMap
}

/**
 * 连接Map
 */
fun <K, V> contactMap(vararg source: Map<K, V>): MutableMap<K, V> {
    val params = mutableMapOf<K, V>()
    source.forEach {
        params.putAll(it)
    }
    return params
}

fun <M : Map<String, Any?>> M.toBundle():Bundle {
  return  bundleOf(*this.map { it.key to it.value }.toTypedArray())
}



