package com.elevenetc.android.grid

import android.graphics.Canvas

abstract class StaticItem(val id: String) {
    abstract fun draw(cell: GridCell, canvas: Canvas, originX: Float, originY: Float, cellValues: StaticMap.CellValues)
}