package com.elevenetc.android.grid.dynamic

class PointF(var x: Float, var y: Float) {
    fun offset(x: Float, y: Float) {
        this.x += x
        this.y += y
    }
}