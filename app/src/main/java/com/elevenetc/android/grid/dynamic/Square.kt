package com.elevenetc.android.grid.dynamic

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.elevenetc.android.grid.UnitView
import com.elevenetc.android.grid.utils.drawRect

class Square(color: Int) : UnitView() {


    val paint = Paint().apply {
        this.style = Paint.Style.FILL
        this.color = color
    }

    val path = Path()

    override fun toString(): String {
        return "{$id}"
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.translate(500f, 500f)
        drawRect(model.topPoint.x, model.topPoint.y, model.bottomPoint.x, model.bottomPoint.y, model.leftPoint, model.rightPoint, path, paint, canvas)
        canvas.restore()
    }

}