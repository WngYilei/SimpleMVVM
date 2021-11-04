package com.xl.common.tool.ktx

import com.xl.common.tool.util.JsonUtil
import org.json.JSONObject

/**
 * 描述:Json 扩展
 *
 * @author zys
 * create by 2020/04/07
 */

/**
 * 构建json字符串
 */
fun jsonOf(vararg pairs: Pair<String, Any?>): String {
    val map = mutableMapOf<String, Any>()
    for ((key, value) in pairs) {
        if (value != null) {
            map[key] = value
        }
    }
    return JsonUtil.toJson(map)
}

/**
 * 返回新的[JSONObject]
 */
fun jsonObjectOf(vararg pairs: Pair<String, Any?>) = JSONObject().apply {
    for ((key, value) in pairs) {
        when (value) {
            null -> put(key, "none")
            is Boolean -> put(key, value)
            is Double -> put(key, value)
            is Int -> put(key, value)
            is Long -> put(key, value)
            is String -> put(key, value)
        }
    }
}
