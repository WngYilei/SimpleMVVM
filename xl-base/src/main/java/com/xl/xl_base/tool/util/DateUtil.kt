package com.xl.common.tool.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@Suppress("UNUSED")
object DateUtil {

    /**
     * 格式化时间
     *
     *  yyyy-MM-dd'T'HH:mm:ss
     *  yyyy-MM-dd HH:mm"
     *  yyyy-MM-dd
     *  yyyy-MM
     *  MM-dd HH:mm
     *  MM-dd
     *  yyyyMM
     *  yyyy
     *  MM
     *  HH:mm
     *  yyyy.MM.dd
     *  yyyy/MM/dd
     *  MM/dd
     *  yyyy年MM月dd日
     *  yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 毫秒时间戳
     */
    fun format(timestamp: Long?, pattern: String): String = when {
        timestamp == null || timestamp <= 0 -> ""
        else -> SimpleDateFormat(pattern, Locale.CHINA).format(Date(timestamp))

    }

    /**
     * 将这样的字符串 yyyy-MM-dd HH:mm 解析为 毫秒时间戳
     */
    fun parse(timestamp: String?, pattern: String): Long = when {
        timestamp.isNullOrEmpty() -> 0L
        else -> {
            val date = try {
                SimpleDateFormat(pattern, Locale.CHINA).parse(timestamp)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            date?.time ?: 0L
        }
    }

    fun calculateDays(start: Long, end: Long): Int {
        return ((end - start) / (DateUtils.DAY_IN_MILLIS * 1f)).roundToInt()
    }

    fun calculateDays2(start: Long, end: Long): Int {
        val d = ((end - start) / (DateUtils.DAY_IN_MILLIS * 1f)).toDouble()
        return ceil(d).roundToInt()
    }

    /**
     * 限时优惠活动剩余时间计算
     */
    fun calculateTime(start: Long, end: Long): String {
        val timeInterval = end - start
        return if (timeInterval > DateUtils.DAY_IN_MILLIS) {
            val d = (timeInterval / (DateUtils.DAY_IN_MILLIS * 1f)).toDouble()
            val hourMillis = (timeInterval % (DateUtils.DAY_IN_MILLIS * 1f)).toDouble()
            if (hourMillis > DateUtils.HOUR_IN_MILLIS * 23) {
                "${ceil(d).roundToInt()}天"
            } else {
                val h = hourMillis / (DateUtils.HOUR_IN_MILLIS * 1f).toDouble()
                "${floor(d).roundToInt()}天${ceil(h).roundToInt()}小时"
            }
        } else {
            val h = (timeInterval / (DateUtils.HOUR_IN_MILLIS * 1f)).toDouble()
            "${ceil(h).roundToInt()}小时"
        }
    }

    /**
     * 是否今年
     */
    fun isYear(timestamp: Long?): Boolean {
        if (timestamp == null) return false
        val calendar = GregorianCalendar()
        calendar.timeInMillis = timestamp
        //显示时间
        val year = calendar.get(Calendar.YEAR)
        //当前时间
        calendar.timeInMillis = System.currentTimeMillis()
        return year == calendar.get(Calendar.YEAR)
    }

    /**
     * @param month 往前推几个月
     */
    fun beforeMonth(month: Int): Long {
        val ca = Calendar.getInstance()
        val date = Date(System.currentTimeMillis())
        ca.time = date
        ca.add(Calendar.MONTH, -month)
        return ca.timeInMillis / 1000
    }
}