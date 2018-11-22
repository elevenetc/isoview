package com.elevenetc.android.grid

import android.graphics.Canvas
import com.elevenetc.android.grid.utils.getTriangleSide

class StaticMap(
        val rows: Int,
        val cols: Int,
        val cellWidth: Float,
        val sideAngle: Float
) : Map {

    val cellValues = CellValues(cellWidth, sideAngle)

    private val map: Array<Array<GridCell>> = Array(rows) { r -> Array(cols) { c -> GridCell(r, c) } }
    private val mapById = mutableMapOf<String, StaticItem>()


    fun occupied(r: Int, c: Int): Boolean {
        return map[r][c].view != null
    }

    fun addItem(view: StaticItem, r: Int, c: Int) {
        map[r][c].view = view
        mapById[view.id] = view
    }

    override fun draw(canvas: Canvas) {
        for (r in map.indices) {
            traverseLine(map, r, 0, canvas)
        }

        for (c in 1 until map[0].size) {
            traverseLine(map, map.size - 1, c, canvas)
        }
    }

    private fun traverseLine(grid: Array<Array<GridCell>>, r: Int, c: Int, canvas: Canvas) {
        if (r < 0 || c < 0 || r > grid.size - 1 || c > grid[0].size - 1) return
        val cell = grid[r][c]
        cell.view?.draw(cell, canvas, 400f, 400f, cellValues)
        traverseLine(grid, r - 1, c + 1, canvas)
    }

    class CellValues(
            val width: Float,
            val sideAngle: Float) {
        val halfWidth = width / 2
        val halfSideAngle = sideAngle / 2
        val bottomAngle = (360 - sideAngle * 2) / 2
        val bottomHalfAngle = bottomAngle / 2
        val halfHeight = getTriangleSide(bottomHalfAngle, halfWidth, halfSideAngle)
        val height = halfHeight * 2
    }
}