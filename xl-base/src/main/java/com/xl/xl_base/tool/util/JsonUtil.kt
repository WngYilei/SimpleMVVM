package com.xl.xl_base.tool.util

import android.annotation.SuppressLint
import android.os.Bundle
import com.xl.xl_base.tool.ktx.notEmpty
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject

/**
 * 描述: Json工具类
 */
object JsonUtil {

    /**
     */
    val moShi: Moshi
        get() = Moshi.Builder()
            .build()

    fun throwException(error: String?) {

    }

    /**
     *判断字符串 是否是json格式
     */
    @SuppressLint("CheckResult")
    fun isJson(str: String?): Boolean = try {
        if (str.isNullOrEmpty()) {
            false
        } else {
            moShi.adapter(Any::class.java).fromJson(str)
            true
        }
    } catch (e: Exception) {
        isJsonArray(str)
    }

    /**
     *判断字符串 是否是json数组格式
     */
    @SuppressLint("CheckResult")
    fun isJsonArray(str: String?): Boolean = try {
        if (str.isNullOrEmpty()) {
            false
        } else {
            val type = Types.newParameterizedType(List::class.java, Any::class.java)
            moShi.adapter<List<Any>>(type).fromJson(str)
            true
        }
    } catch (e: Exception) {
        false
    }

    /**
     * 解析json数组
     */
    inline fun <reified E> parseArray(json: String?): List<E> {
        val result = mutableListOf<E>()
        if (json.isNullOrEmpty()) return result
        try {
            val type = Types.newParameterizedType(List::class.java, E::class.java)
            val jsonAdapter: JsonAdapter<List<E>> = moShi.adapter(type)
            jsonAdapter.fromJson(json)?.run {
                result.addAll(this)
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return result
    }

    /**
     * 解析json数组
     */
    fun <E> parseArray(json: String?, clazz: Class<E>): List<E> {
        val result = mutableListOf<E>()
        if (json.isNullOrEmpty()) return result
        try {
            val type = Types.newParameterizedType(List::class.java, clazz)
            val jsonAdapter: JsonAdapter<List<E>> = moShi.adapter(type)
            jsonAdapter.fromJson(json)?.run {
                result.addAll(this)
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return result
    }

    /**
     * 解析json对象
     */
    inline fun <reified E> parseObject(json: String?): E? {
        if (json.isNullOrEmpty()) return null
        try {
            val jsonAdapter = moShi.adapter(E::class.java)
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            throwException(e.message)
        }
        return null
    }

    /**
     *解析jsonMap
     */
    inline fun <reified V> parseMap(json: String?): MutableMap<String, V?> {
        val mutableMapOf = mutableMapOf<String, V?>()
        try {
            val jsonObject = JSONObject(json ?: "")
            val iterator = jsonObject.keys()
            var key: String
            while (iterator.hasNext()) {
                key = iterator.next()
                key.notEmpty {
                    mutableMapOf[it] = jsonObject.get(key) as V
                }
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return mutableMapOf
    }

    fun parseBundle(json: String?): Bundle {
        val bundle = Bundle()
        try {
            val jsonObject = JSONObject(json ?: "")
            val iterator = jsonObject.keys()
            var key: String
            while (iterator.hasNext()) {
                key = iterator.next()
                key.notEmpty {
                    when (val value = jsonObject.get(key)) {
                        is String -> bundle.putString(key, value)
                        is Long -> bundle.putLong(key, value)
                        is Int -> bundle.putInt(key, value)
                        is Double -> bundle.putDouble(key, value)
                        is Float -> bundle.putFloat(key, value)
                    }
                }
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return bundle
    }

    /**
     * 数组 转 字符串
     */
    inline fun <reified T> toJson(list: List<T>?): String {
        return try {
            val type = Types.newParameterizedType(List::class.java, T::class.java)
            val jsonAdapter: JsonAdapter<List<T>> = moShi.adapter(type)
            jsonAdapter.toJson(list)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}