package com.elevenetc.android.grid.utils

//import android.graphics.*
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.elevenetc.android.grid.dynamic.PointF
import com.elevenetc.android.grid.math.GeomMath
import com.elevenetc.android.grid.math.Segment

fun drawRect(bottomX: Float,
             bottomY: Float,
             width: Float,
             sideAngle: Float,
             path: Path,
             paint: Paint,
             canvas: Canvas) {

    val halfWidth = width / 2
    val halfSideAngle = sideAngle / 2
    val bottomAngle = (360 - sideAngle * 2) / 2
    val bottomHalfAngle = bottomAngle / 2

    val halfHeight = getTriangleSide(bottomHalfAngle, halfWidth, halfSideAngle)

    val topX = bottomX
    val topY = bottomY - halfHeight * 2

    drawRect(
            topX, topY,
            bottomX, bottomY,
            bottomAngle,
            path,
            paint,
            canvas
    )
}

fun drawRect(farX: Float,
             farY: Float,
             closeX: Float,
             closeY: Float,
             bottomAngle: Float,
             path: Path,
             paint: Paint,
             canvas: Canvas) {

    val halfAngle = bottomAngle / 2

    val testSegmentLength = Math.abs(farX - closeX) + Math.abs(farY + closeY)

    val topLeftSegment = Segment(farX, farY, farX, farY + testSegmentLength)
    val topRightSegment = Segment(farX, farY, farX, farY + testSegmentLength)

    val bottomLeftSegment = Segment(closeX, closeY, closeX, closeY - testSegmentLength)
    val bottomRightSegment = Segment(closeX, closeY, closeX, closeY - testSegmentLength)

    val math = GeomMath()

    math.rotateTo(topLeftSegment, halfAngle)
    math.rotateTo(topRightSegment, -halfAngle)
    math.rotateTo(bottomLeftSegment, -halfAngle)
    math.rotateTo(bottomRightSegment, halfAngle)

    val leftPoint = math.getIntersection(topLeftSegment, bottomLeftSegment)
    val rightPoint = math.getIntersection(topRightSegment, bottomRightSegment)


//    drawRect(farX, farY, closeX, closeY, leftPoint, rightPoint, path, paint, canvas)
}

fun lighter(color: Int, mult: Float): Int {
    var red = Color.red(color)
    var green = Color.green(color)
    var blue = Color.blue(color)

    red = (red + red * mult).toInt()
    green = (green + green * mult).toInt()
    blue = (blue + blue * mult).toInt()
    return Color.argb(255, red, green, blue)
}

fun drawRect(topX: Float, topY: Float, bottomX: Float, bottomY: Float, leftPoint: PointF, rightPoint: PointF, path: Path, paint: Paint, canvas: Canvas) {

//    path.reset()
//    path.moveTo(topX, topY)
//    path.lineTo(rightPoint.x, rightPoint.y)
//    path.lineTo(bottomX, bottomY)
//    path.lineTo(leftPoint.x, leftPoint.y)
//    path.close()
//    canvas.drawPath(path, paint)

    val initColor = paint.color
    val lighter = lighter(initColor, 0.1f)
    val darker = lighter(initColor, -0.1f)

    val height = 50
    //val height = (Math.random() * 100 + 50).toInt()

    //left side
    path.reset()
    path.moveTo(bottomX, bottomY)
    path.lineTo(leftPoint.x, leftPoint.y)

    path.lineTo(leftPoint.x, leftPoint.y - height)
    path.lineTo(bottomX, bottomY - height)
    path.close()
    canvas.drawPath(path, paint)

    //right side
    path.reset()
    path.moveTo(bottomX, bottomY)
    path.lineTo(rightPoint.x, rightPoint.y)
    path.lineTo(rightPoint.x, rightPoint.y - height)
    path.lineTo(bottomX, bottomY - height)
    path.close()
    canvas.drawPath(path, paint.apply { color = lighter })

    //top side
    path.reset()
    path.moveTo(bottomX, bottomY - height)
    path.lineTo(leftPoint.x, leftPoint.y - height)
    path.lineTo(topX, topY - height)
    path.lineTo(rightPoint.x, rightPoint.y - height)
    path.close()
    canvas.drawPath(path, paint.apply { color = darker })

    paint.color = initColor


//    drawSegment(topLeftSegment, canvas)
//    drawSegment(topRightSegment, canvas)
//    drawSegment(bottomLeftSegment, canvas)
//    drawSegment(bottomRightSegment, canvas)
//
//    drawPoint(leftPoint, canvas)
//    drawPoint(rightPoint, canvas)
}

val segmentPaint = Paint().apply {
    this.color = Color.BLACK
    this.style = Paint.Style.STROKE
    this.strokeWidth = 3f
}

val pointPaint = Paint().apply {
    this.color = Color.CYAN
    this.style = Paint.Style.STROKE
    this.strokeWidth = 3f
}

fun drawSegment(segment: Segment, canvas: Canvas) {
    canvas.drawLine(
            segment.x1, segment.y1,
            segment.x2, segment.y2,
            segmentPaint
    )
}

fun drawPoint(point: PointF, canvas: Canvas) {
    canvas.drawCircle(point.x, point.y, 10f, pointPaint)
}

/**
 *   sideA     sideB
 *  ------- = -------
 *   sin(a)    sin(a)
 */
fun getTriangleSide(angleA: Float, sideA: Float, angleB: Float): Float {
    val radA = Math.toRadians(angleA.toDouble())
    val radB = Math.toRadians(angleB.toDouble())
    val sideB = Math.sin(radB) * (sideA / Math.sin(radA))
    return sideB.toFloat()
}