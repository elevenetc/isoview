package com.elevenetc.android.grid.dynamic

import android.graphics.Canvas
import com.elevenetc.android.grid.Bounds
import java.util.concurrent.ConcurrentLinkedQueue

abstract class DynamicItem(val id: String) {

    var isoX: Float = 0f
    var isoY: Float = 0f
    var isoXSize: Float = 0f
    var isoYSize: Float = 0f

    val infont = ConcurrentLinkedQueue<DynamicItem>()
    val behind = ConcurrentLinkedQueue<DynamicItem>()

    abstract fun getBounds(): Bounds
    abstract fun draw(canvas: Canvas, angle: Float)
    abstract fun layout(angle: Float)
}