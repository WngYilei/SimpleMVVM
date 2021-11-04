package com.xl.xl_base.tool.util

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * 描述: 轻量的键值对存储工具，默认不支持多进程
 *
 * author zys
 * create by 2021/1/18
 */
object KVUtil {

    private val defaultMMKV: MMKV?
        get() = MMKV.defaultMMKV()

    /**
     * 存储键值对
     */
    @JvmStatic
    fun put(key: String, value: Any?) {
        when (value) {
            null -> {
                //do nothing
            }
            is Int -> defaultMMKV?.encode(key, value)
            is Long -> defaultMMKV?.encode(key, value)
            is Double -> defaultMMKV?.encode(key, value)
            is Float -> defaultMMKV?.encode(key, value)
            is Boolean -> defaultMMKV?.encode(key, value)
            is String -> defaultMMKV?.encode(key, value)
            is Parcelable -> defaultMMKV?.encode(key, value)
            is ByteArray -> defaultMMKV?.encode(key, value)
        }
    }

    /**
     * 通过key移除
     */
    @JvmStatic
    fun removeByKey(key: String) {
        defaultMMKV?.removeValueForKey(key)
    }

    @JvmStatic
    fun get(key: String, default: Int = 0): Int =
        defaultMMKV?.decodeInt(key, default) ?: default

    @JvmStatic
    fun get(key: String, default: Long = 0): Long =
        defaultMMKV?.decodeLong(key, default) ?: default

    @JvmStatic
    fun get(key: String, default: Double = 0.0): Double =
        defaultMMKV?.decodeDouble(key, default) ?: default

    @JvmStatic
    fun get(key: String, default: Float = 0F): Float =
        defaultMMKV?.decodeFloat(key, default) ?: default

    @JvmStatic
    fun get(key: String, default: Boolean = false): Boolean =
        defaultMMKV?.decodeBool(key, default) ?: default

    @JvmStatic
    fun get(key: String, default: String = ""): String =
        defaultMMKV?.decodeString(key, default) ?: default

    @JvmStatic
    fun getBytes(key: String, default: ByteArray = byteArrayOf()): ByteArray =
        defaultMMKV?.decodeBytes(key) ?: default

    @JvmStatic
    fun <T : Parcelable> getParcelable(key: String, clazz: Class<T>): T? =
        defaultMMKV?.decodeParcelable(key, clazz, null)
}