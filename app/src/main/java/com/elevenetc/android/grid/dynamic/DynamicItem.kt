package com.elevenetc.android.grid.dynamic

import android.graphics.Canvas
import com.elevenetc.android.grid.Bounds
import java.util.concurrent.ConcurrentLinkedQueue

abstract class DynamicItem(val id: String) {

    var isoX: Int = 0
    var isoY: Int = 0
    var isoXSize: Int = 0
    var isoYSize: Int = 0

    val infont = ConcurrentLinkedQueue<DynamicItem>()
    val behind = ConcurrentLinkedQueue<DynamicItem>()

    abstract fun getBounds(): Bounds
    abstract fun draw(canvas: Canvas, angle: Float)
    abstract fun layout(angle: Float)
}