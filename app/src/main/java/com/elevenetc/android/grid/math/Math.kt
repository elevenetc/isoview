package com.elevenetc.android.grid.math

import android.graphics.PointF

class GeomMath {
    fun getIntersection(
            x1: Float, y1: Float, x2: Float, y2: Float,
            x3: Float, y3: Float, x4: Float, y4: Float): PointF {
        val d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)

        val x = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d
        val y = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d

        return PointF(x, y)
    }

    fun getIntersection(a: Segment, b: Segment): PointF {

        val x1 = a.x1
        val y1 = a.y1
        val x2 = a.x2
        val y2 = a.y2

        val x3 = b.x1
        val y3 = b.y1
        val x4 = b.x2
        val y4 = b.y2

        return getIntersection(x1, y1, x2, y2, x3, y3, x4, y4)
    }

    fun rotateTo(segment: Segment, degrees: Float): Segment {
        val rads = Math.toRadians(degrees.toDouble()).toFloat()
        val cos = Math.cos(rads.toDouble()).toFloat()
        val sin = Math.sin(rads.toDouble()).toFloat()

        //move to origin
        segment.x2 -= segment.x1
        segment.y2 -= segment.y1

        //rotate
        val x = segment.x2 * cos - segment.y2 * sin
        val y = segment.x2 * sin + segment.y2 * cos

        //move back
        segment.x2 = x + segment.x1
        segment.y2 = y + segment.y1

        return segment
    }
}