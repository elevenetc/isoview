package com.elevenetc.android.grid

import com.elevenetc.android.grid.dynamic.PointF
import java.util.concurrent.ConcurrentLinkedQueue

class UnitModel(val id: String) {

    var isoX: Float = 0f
    var isoY: Float = 0f
    var isoXSize: Float = 0f
    var isoYSize: Float = 0f

    val infont = ConcurrentLinkedQueue<UnitModel>()
    val behind = ConcurrentLinkedQueue<UnitModel>()

    lateinit var topPoint: PointF
    lateinit var bottomPoint: PointF
    lateinit var leftPoint: PointF
    lateinit var rightPoint: PointF

    var view: UnitView? = null

    var screenWidth: Float = 0f
    var screenHeight: Float = 0f

    var unitHeight: Float = 0f
    var unitWidth: Float = 0f


    fun getBounds(): Bounds {
        return Bounds(
                leftPoint.x, rightPoint.x,
                topPoint.y, bottomPoint.y
        )
    }

    override fun toString(): String {
        return "$id"
    }
}

enum class Axis {
    X, Y, NonAxis
}