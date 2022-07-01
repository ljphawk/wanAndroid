package com.hjq.shape.listener

import com.hjq.shape.builder.ShapeDrawableBuilder
import com.hjq.shape.builder.TextColorBuilder


/*
 *@创建者       L_jp
 *@创建时间     2022/7/1 09:12.
 *@描述
 */
interface ShapeViewAgent {

    fun getShapeDrawableBuilder(): ShapeDrawableBuilder

    fun getTextColorBuilder(): TextColorBuilder

    fun setSolidColor(color: Int) {
        getShapeDrawableBuilder().solidColor = color
        getShapeDrawableBuilder().intoBackground()
    }

    fun setRadius(radius: Float) {
        getShapeDrawableBuilder().setRadius(radius)
        getShapeDrawableBuilder().intoBackground()
    }


    fun setTopLeftRadius(radius: Float) {
        getShapeDrawableBuilder().topLeftRadius = radius
        getShapeDrawableBuilder().intoBackground()
    }

    fun setTopRightRadius(radius: Float) {
        getShapeDrawableBuilder().topRightRadius = radius
        getShapeDrawableBuilder().intoBackground()
    }


    fun setBottomLeftRadius(radius: Float) {
        getShapeDrawableBuilder().bottomLeftRadius = radius
        getShapeDrawableBuilder().intoBackground()
    }


    fun setBottomRightRadius(radius: Float) {
        getShapeDrawableBuilder().bottomRightRadius = radius
        getShapeDrawableBuilder().intoBackground()
    }

    fun setStrokeColor(color: Int) {
        getShapeDrawableBuilder().strokeColor = color
        getShapeDrawableBuilder().intoBackground()
    }

    fun setStrokeWidth(width: Int) {
        getShapeDrawableBuilder().strokeWidth = width
        getShapeDrawableBuilder().intoBackground()
    }

}