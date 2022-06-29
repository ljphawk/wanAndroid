package com.qszx.utils.extensions


/*
 *@创建者       L_jp
 *@创建时间     2020/9/9 3:22 PM.
 *@描述
 */


fun String?.toDouble2(default: Double = 0.0): Double {
    return if (!this.contentHasValue()) {
        default
    } else {
        try {
            this?.toDouble() ?: default
        } catch (e: Exception) {
            default
        }

    }
}

fun String?.toInt2(default: Int = 0): Int {
    return if (!this.contentHasValue()) {
        default
    } else {
        try {
            this?.toInt() ?: default
        } catch (e: Exception) {
            default
        }

    }
}

fun String?.toLong2(default: Long = 0): Long {
    return if (!this.contentHasValue()) {
        default
    } else {
        try {
            this?.toLong() ?: default
        } catch (e: Exception) {
            default
        }
    }
}