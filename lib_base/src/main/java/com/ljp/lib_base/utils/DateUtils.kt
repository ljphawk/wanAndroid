package com.ljp.lib_base.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


/*
 *@创建者       L_jp
 *@创建时间     2020/4/14 15:49.
 *@描述
 */
object DateUtils {

    const val ONE_SECOND: Long = 1000
    const val ONE_MINUTE = 60 * ONE_SECOND
    const val ONE_HOUR = 60 * ONE_MINUTE
    const val ONE_DAY = 24 * ONE_HOUR
    const val ONE_WEEK = 7 * ONE_DAY
    const val ONE_MONTH = 30 * ONE_DAY

    const val YMDHMS1 = "yyyy-MM-dd HH:mm:ss"
    const val YMDHMS2 = "yyyy-MM-dd"
    const val YMDHMS3 = "yyyy-MM-dd HH:mm"
    const val YMDHMS4 = "MM-dd HH:mm"
    const val YMDHMS5 = "yyyy-MM"

    /**
     * 获取当前的时间戳
     */
    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis() / 1000
    }


    /**
     * 获取当前的时间浩渺值
     */
    fun getCurrentCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    /**
     *  @param timeStamp 秒(10位)
     * @param dateFormat $YMDHMS
     * @return 默认返回固定的 yyyy-MM-dd
     */
    fun getDateFormat(
        timeStamp: Long = getCurrentTimestamp(),
        dateFormat: String = YMDHMS2
    ): String {
        return try {
            val sdr = SimpleDateFormat(dateFormat)
            sdr.format(Date(timeStamp * 1000))
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 返回时间戳
     */
    fun getTimestamp(time: String?, sdfForm: String): String {
        var times = ""
        try {
            val sdf = SimpleDateFormat(sdfForm)
            val date = sdf.parse(time)
            times = (date.time / 1000).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return times
    }


    /**
     * 时间格式化  秒 转00:00 || 00：00：00
     * @param seconds 秒!
     */
    fun convertSecondToFormatString(seconds: Long): String {
        var min = seconds / 60
        val sec = seconds % 60
        val hour = min / 60
        val timeSb = StringBuffer()
        if (hour > 0) {
            timeSb.append(if (hour >= 10) "" + hour else "0$hour")
            timeSb.append(":")
            min %= 60
        }

        timeSb.append(if (min >= 10) "" + min else "0$min")
        timeSb.append(":")
        timeSb.append(if (sec >= 10) "" + sec else "0$sec")
        return timeSb.toString()
    }


    fun formatElapsedTime(timestamp: Long): String {
        val diffMills = System.currentTimeMillis() - timestamp
        return when {
            diffMills < 20L * ONE_SECOND -> {
                "刚刚 "
            }
            diffMills < ONE_MINUTE -> {
                "${diffMills / ONE_SECOND}秒前 "
            }
            diffMills < ONE_HOUR -> {
                "${diffMills / ONE_MINUTE}分钟前 "
            }
            diffMills < ONE_DAY -> {
                "${diffMills / ONE_HOUR}小时前 "
            }
            diffMills < ONE_MONTH -> {
                "${diffMills / ONE_DAY}天前 "
            }
            isSameYear(timestamp) -> {
                getDateFormat(timestamp, YMDHMS4)
            }
            else -> {
                getDateFormat(timestamp, YMDHMS2)
            }
        }

    }


    private fun isSameYear(timestamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val year = calendar.get(Calendar.YEAR)

        calendar.timeInMillis = System.currentTimeMillis()
        val currentYear = calendar.get(Calendar.YEAR)

        return year == currentYear
    }

    /**
     * @return true  是否是同一天
     */
    fun isSameDate(timeStamp1: String, timeStamp2: String): Boolean {
        return try {
            //data2-data1
            abs(differentDays(timeStamp1, timeStamp2)) == 0
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }


    /**
     * date2比date1多的天数
     * @param timeStamp1
     * @param timeStamp2
     * @return
     */
    fun differentDays(timeStamp1: String, timeStamp2: String): Int {
        return try {
            val cal1 = Calendar.getInstance(Locale.CHINA)
            cal1.timeInMillis = (timeStamp1 + "000").toLong()
            val cal2 = Calendar.getInstance(Locale.CHINA)
            cal2.timeInMillis = (timeStamp2 + "000").toLong()
            val day1 = cal1[Calendar.DAY_OF_YEAR]
            val day2 = cal2[Calendar.DAY_OF_YEAR]
            val year1 = cal1[Calendar.YEAR]
            val year2 = cal2[Calendar.YEAR]
            if (year1 != year2) { //同一年
                val min = year1.coerceAtMost(year2)
                val max = year1.coerceAtLeast(year2)
                var timeDistance = 0
                for (i in min until max) {
                    if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {    //闰年
                        if (year2 > year1) {
                            timeDistance += 366
                        } else {
                            timeDistance -= 366
                        }
                    } else {    //不是闰年
                        if (year2 > year1) {
                            timeDistance += 365
                        } else {
                            timeDistance -= 365
                        }
                    }
                }
                timeDistance + (day2 - day1)
            } else {  //不同年
                day2 - day1
            }
        } catch (e: Exception) {
            0
        }
    }


}