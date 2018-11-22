package com.elevenetc.android.grid.views

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.elevenetc.android.grid.GridCell
import com.elevenetc.android.grid.StaticItem
import com.elevenetc.android.grid.StaticMap

class Cube(val height: Float, id:String) : StaticItem(id) {

    val path = Path()

    val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    override fun draw(cell: GridCell, canvas: Canvas, originX:Float, originY:Float, callValues: StaticMap.CellValues) {

        val size = 10f//width

        val r = cell.r
        val c = cell.c
        val centerX = originX
        val centerY = originY
        val cellWidth = size
        val cellHeight = cellWidth / 2

        val cellX = centerX + (r * cellWidth * -1) + (c * cellWidth)
        val cellY = centerY + (r + c) * cellHeight


        //left
        path.reset()
        path.moveTo(cellX, cellY + cellHeight)
        path.rLineTo(0f, -height)
        path.rLineTo(-cellWidth, -cellHeight)
        path.rLineTo(0f, height)
        path.rLineTo(cellWidth, cellHeight)
        path.close()

        paint.color = Color.parseColor("#ff0000")
        canvas.drawPath(path, paint)

        //right
        path.reset()
        path.moveTo(cellX, cellY + cellHeight)
        path.rLineTo(0f, -height)
        path.rLineTo(cellWidth, -cellHeight)
        path.rLineTo(0f, height)
        path.rLineTo(-cellWidth, cellHeight)
        path.close()

        paint.color = Color.parseColor("#C30007")
        canvas.drawPath(path, paint)

        //top
        path.reset()
        path.moveTo(cellX, cellY - height)
        path.rLineTo(0f, -cellHeight)//top
        path.rLineTo(cellWidth, cellHeight)//right
        path.rLineTo(-cellWidth, cellHeight)//bottom
        path.rLineTo(-cellWidth, -cellHeight)//left
        path.rLineTo(cellWidth, -cellHeight)//top
        path.close()

        paint.color = Color.parseColor("#960208")
        canvas.drawPath(path, paint)
    }

}