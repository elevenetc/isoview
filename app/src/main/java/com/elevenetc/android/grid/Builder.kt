package com.elevenetc.android.grid

import com.elevenetc.android.grid.dynamic.PointF
import com.elevenetc.android.grid.dynamic.Utils

class Builder(val unitWidth: Float, val unitHeight: Float) {

    var transX: Float = 0f
    var transY: Float = 0f
    val views = mutableListOf<UnitView>()
    var id = 0

    private fun getId() = (id++).toString()

    fun addSquare(x: Int, y: Int, view: UnitView, id: String? = null): Builder {


        val model = UnitModel(id ?: getId())

        view.model = model
        view.id = model.id

        model.view = view
        model.unitWidth = unitWidth
        model.unitHeight = unitHeight
        model.isoX = x.toFloat()
        model.isoY = y.toFloat()
        model.isoXSize = 1f
        model.isoYSize = 1f

        if (id.equals("moving")) {
            //model.isoY = 1.3f
        }

        Utils.layout(model)

        views.add(view)

        return this
    }

    fun addRect(x: Int, y: Int, xSize: Int, ySize: Int, view: UnitView, id: String? = null): Builder {

        val model = UnitModel(id ?: getId())

        view.model = model
        view.id = model.id

        model.view = view
        model.unitWidth = unitWidth
        model.unitHeight = unitHeight
        model.isoX = x.toFloat()
        model.isoY = y.toFloat()
        model.isoXSize = xSize.toFloat()
        model.isoYSize = ySize.toFloat()


        val topX = isoToScreenX(x, y)
        val topY = isoToScreenY(x, y)
        model.topPoint = PointF(topX, topY)

        val leftX = x
        val leftY = y + ySize
        model.leftPoint = PointF(isoToScreenX(leftX, leftY), isoToScreenY(leftX, leftY))

        val rightX = x + xSize
        val rightY = y
        model.rightPoint = PointF(isoToScreenX(rightX, rightY), isoToScreenY(rightX, rightY))

        val bottomX = x + xSize
        val bottomY = y + ySize
        model.bottomPoint = PointF(isoToScreenX(bottomX, bottomY), isoToScreenY(bottomX, bottomY))


        //Utils.layout(model)

        views.add(view)

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

    fun build(): List<UnitView> {
        return views
    }

    private fun transPoint(point: PointF) {
        point.offset(transX, transY)
    }

    private fun toIso(point: PointF): PointF {
        return PointF(point.x * (unitWidth / 2), point.y * (unitHeight / 2))
    }
}