package com.elevenetc.android.grid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GridMatrixView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    lateinit var map: Map

    init {

    }

    fun init(map: Map) {
        this.map = map
    }

    val gridPaint = Paint().apply {
        this.color = Color.GREEN
        this.style = Paint.Style.STROKE
        this.strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas) {
        map.draw(canvas)
//        drawDebugMatrix(canvas)
    }

    private fun drawDebugMatrix(canvas: Canvas) {
        for (xk in 0..100) {
            val x = xk * 100
            canvas.drawLine(x.toFloat(), 0f, x.toFloat(), canvas.height.toFloat(), gridPaint)
        }

        for (yk in 0..100) {
            val y = yk * 100
            canvas.drawLine(0.toFloat(), y.toFloat(), canvas.width.toFloat(), y.toFloat(), gridPaint)
        }
    }
}