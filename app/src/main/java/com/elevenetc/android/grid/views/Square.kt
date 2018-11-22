package com.elevenetc.android.grid.views

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.elevenetc.android.grid.GridCell
import com.elevenetc.android.grid.StaticItem
import com.elevenetc.android.grid.StaticMap
import com.elevenetc.android.grid.utils.drawRect

class Square(id: String) : StaticItem(id) {

    val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    val path = Path()

    override fun draw(cell: GridCell, canvas: Canvas, originX: Float, originY: Float, cellValues: StaticMap.CellValues) {
        val r = cell.r
        val c = cell.c

        val bottomX = originX + (r * cellValues.halfWidth * -1) + (c * cellValues.halfWidth)
        val bottomY = originY + (r + c) * cellValues.halfHeight

        drawRect(
                bottomX, bottomY - cellValues.height,
                bottomX, bottomY,
                cellValues.bottomAngle,
                path,
                paint,
                canvas
        )

//        paint.reset()
//        path.moveTo(bottomX, bottomY)
//        path.rLineTo(0f, -cellHeight)//top
//        path.rLineTo(cellWidth, cellHeight)//right
//        path.rLineTo(-cellWidth, cellHeight)//bottom
//        path.rLineTo(-cellWidth, -cellHeight)//left
//        path.rLineTo(cellWidth, -cellHeight)//top
//        path.close()
//
//        canvas.drawPath(path, paint)
    }
}