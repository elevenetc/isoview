package com.elevenetc.android.grid.dynamic

import android.graphics.Canvas
import com.elevenetc.android.grid.IsoComparator
import com.elevenetc.android.grid.Map

class DynamicMap(val angle: Float) : Map {

    var items = mutableListOf<DynamicItem>()
    val isoComparator = IsoComparator()

    fun addItem(item: DynamicItem) {
        items.add(item)
    }

    override fun draw(canvas: Canvas) {
        val drawList = sort()
        drawList.forEach {
            it.draw(canvas, angle)
        }
    }

    fun sort(): List<DynamicItem> {
        val sort = Utils.sort(items)
        return sort
    }

    enum class Axis {
        X, Y, NonAxis
    }
}