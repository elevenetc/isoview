package com.elevenetc.android.grid.dynamic

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.elevenetc.android.grid.Bounds
import com.elevenetc.android.grid.utils.drawRect

class Square(
        id: String
) : DynamicItem(id) {

    val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    val path = Path()
    lateinit var topPoint: PointF
    lateinit var bottomPoint: PointF
    lateinit var leftPoint: PointF
    lateinit var rightPoint: PointF

    var screenWidth: Float = 0f
    var screenHeight: Float = 0f

    var unitHeight: Float = 0f
    var unitWidth: Float = 0f

//    var isoX = 0f
//    var isoY = 0f
//    var isoXsize = 0
//    var isoYsize = 0
//
//    var farX: Float = 0f
//    var farY: Float = 0f
//    var closeX: Float = 0f
//    var closeY: Float = 0f

    override fun toString(): String {
        return "{$id}"
    }

    override fun getBounds(): Bounds {
        return Bounds(
                leftPoint.x, rightPoint.x,
                topPoint.y, bottomPoint.y
        )
    }

    override fun layout(angle: Float) {


//        val halfAngle = angle / 2
//
//        val testSegmentLength = Math.abs(farX - closeX) + Math.abs(farY + closeY)
//
//        val topLeftSegment = Segment(farX, farY, farX, farY + testSegmentLength)
//        val topRightSegment = Segment(farX, farY, farX, farY + testSegmentLength)
//
//        val bottomLeftSegment = Segment(closeX, closeY, closeX, closeY - testSegmentLength)
//        val bottomRightSegment = Segment(closeX, closeY, closeX, closeY - testSegmentLength)
//
//        val math = GeomMath()
//
//        math.rotateTo(topLeftSegment, halfAngle)
//        math.rotateTo(topRightSegment, -halfAngle)
//        math.rotateTo(bottomLeftSegment, -halfAngle)
//        math.rotateTo(bottomRightSegment, halfAngle)
//
//        leftPoint = math.getIntersection(topLeftSegment, bottomLeftSegment)
//        rightPoint = math.getIntersection(topRightSegment, bottomRightSegment)
    }

    override fun draw(canvas: Canvas, angle: Float) {

        canvas.save()
        canvas.translate(500f, 500f)
        drawRect(topPoint.x, topPoint.y, bottomPoint.x, bottomPoint.y, leftPoint, rightPoint, path, paint, canvas)
        canvas.restore()

        //drawRect(farX, farY, closeX, closeY, leftPoint, rightPoint, path, paint, canvas)
//        drawRect(farX, farY, closeX, closeY, angle, path, paint, canvas)
    }


    class Builder(val unitWidth: Float, val unitHeight: Float) {

        var transX: Float = 0f
        var transY: Float = 0f

        val units = mutableListOf<Square>()
        var id = 0
        private fun getId() = (id++).toString()

        fun addSquare(x: Int, y: Int, color: Int, id: String? = null): Builder {

            val square = Square(id ?: getId())
            square.paint.color = color
            square.unitWidth = unitWidth
            square.unitHeight = unitHeight
            square.isoX = x.toFloat()
            square.isoY = y.toFloat()
            square.isoXSize = 1f
            square.isoYSize = 1f

            Utils.layout(square, false)

            units.add(square)

            return this
        }

        fun addRect(x: Int, y: Int, xSize: Int, ySize: Int, color: Int, id: String? = null): Builder {

            val square = Square(id ?: getId())

            val topX = isoToScreenX(x, y)
            val topY = isoToScreenY(x, y)
            square.topPoint = PointF(topX, topY)


            val leftX = x
            val leftY = y + ySize
            square.leftPoint = PointF(isoToScreenX(leftX, leftY), isoToScreenY(leftX, leftY))

            val rightX = x + xSize
            val rightY = y
            square.rightPoint = PointF(isoToScreenX(rightX, rightY), isoToScreenY(rightX, rightY))

            val bottomX = x + xSize
            val bottomY = y + ySize
            square.bottomPoint = PointF(isoToScreenX(bottomX, bottomY), isoToScreenY(bottomX, bottomY))

            square.paint.color = color

            square.isoX = x.toFloat()
            square.isoY = y.toFloat()
            square.isoXSize = xSize.toFloat()
            square.isoYSize = ySize.toFloat()

            units.add(square)

            return this
        }

        private fun isoToScreenX(x: Int, y: Int): Float {
            return x * (unitWidth / 2) - y * (unitWidth / 2)
        }

        private fun isoToScreenY(x: Int, y: Int): Float {
            return (x + y) * (unitHeight / 2)
        }

        fun translate(transX: Float, transY: Float): Builder {
            this.transX = transX
            this.transY = transY
            return this
        }

        fun build(): List<Square> {
            units.forEach {
                it.screenWidth = unitWidth
                it.screenHeight = unitHeight

//                transPoint(it.leftPoint)
//                transPoint(it.rightPoint)
//                transPoint(it.bottomPoint)
//                transPoint(it.topPoint)
            }
            return units
        }

        private fun transPoint(point: PointF) {
            point.offset(transX, transY)
        }

        private fun toIso(point: PointF): PointF {
            return PointF(point.x * (unitWidth / 2), point.y * (unitHeight / 2))
        }
    }
}