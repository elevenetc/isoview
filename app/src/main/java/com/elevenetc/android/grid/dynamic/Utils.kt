package com.elevenetc.android.grid.dynamic

import com.elevenetc.android.grid.Axis
import com.elevenetc.android.grid.UnitModel

class Utils {
    companion object {

        fun layout(square: UnitModel) {

            val unitWidth = square.unitWidth
            val unitHeight = square.unitHeight
            val x = square.isoX
            val y = square.isoY

            val isoX = x * (unitWidth / 2) - y * (unitWidth / 2)
            val isoY = (x + y) * (unitHeight / 2)

            square.topPoint = PointF(isoX, isoY)
            square.bottomPoint = PointF(isoX, isoY + unitHeight)
            square.leftPoint = PointF(isoX - unitWidth / 2, isoY + unitHeight / 2)
            square.rightPoint = PointF(isoX + unitWidth / 2, isoY + unitHeight / 2)
        }

        fun sort(units: List<UnitModel>): MutableList<UnitModel> {
            buildTree(units)
            return sortTree(units)
        }

        private fun sortTree(units: List<UnitModel>): MutableList<UnitModel> {
            val result = mutableListOf<UnitModel>()
            val u = mutableListOf(*units.toTypedArray())

            while (!u.isEmpty()) {
                val unit = u.removeAt(0)
                sortBehindOf(unit, result)
                if (!result.contains(unit)) {
                    u.add(unit)
                }
            }
            return result
        }

        fun sortBehindOf(current: UnitModel, result: MutableList<UnitModel>) {


            if (current.behind.isEmpty()) {

                if (!result.contains(current))
                    result.add(current)

                for (frontNode in current.infont) {
                    frontNode.behind.remove(current)
                }

            } else {

                current.behind.forEach { behind ->
                    sortBehindOf(behind, result)
                }
            }
        }

        fun doOverlap(i1: UnitModel, i2: UnitModel): Boolean {
            return doOverlapIn(Axis.X, i1, i2)
                    || doOverlapIn(Axis.Y, i1, i2)
        }

        fun doOverlapIn(a: Axis, i1: UnitModel, i2: UnitModel): Boolean {
            return when (a) {
                Axis.X -> doOverlap(i1.isoX, i1.isoX + i1.isoXSize, i2.isoX, i2.isoX + i2.isoXSize)
                Axis.Y -> doOverlap(i1.isoY, i1.isoY + i1.isoYSize, i2.isoY, i2.isoY + i2.isoYSize)
                else -> false
            }
        }

        fun doOverlapView(a: UnitModel, b: UnitModel): Boolean {
            return if (a.leftPoint.x.compareTo(b.leftPoint.x) == 0) {
                true
            } else if (a.rightPoint.x.compareTo(b.rightPoint.x) == 0) {
                true
            } else if (a.rightPoint.x > b.leftPoint.x) {
                true
            } else a.rightPoint.x > b.leftPoint.x
        }

        fun doOverlap(minA: Float, maxA: Float, minB: Float, maxB: Float): Boolean {
            val overlapFirst = maxA > minB && minA < maxB
            val overlapSecond = maxB > minA && minB < maxA
            return overlapFirst || overlapSecond
        }

        fun getFront(a: UnitModel, b: UnitModel): UnitModel? {


            if (!doOverlapView(a, b)) return null

            val doOverlapInX = doOverlapIn(Axis.X, a, b)
            val doOverlapInY = doOverlapIn(Axis.Y, a, b)

            //return if ((doOverlapInX && doOverlapInY) || (!doOverlapInX && !doOverlapInY)) {
            return if ((doOverlapInX && doOverlapInY)) {

                val sumA = a.isoX + a.isoY
                val sumB = b.isoX + b.isoY

                if (sumA > sumB) a else b

            } else if (doOverlapInX) {
                if (a.isoY > b.isoY) a else b
            } else if (doOverlapInY) {
                if (a.isoX > b.isoX) a else b
            } else {
                null
            }
        }

        /**
         * Compares every unit with every unit
         * arranging items behind and infront
         */
        fun buildTree(units: List<UnitModel>) {

            units.forEach { unit ->
                unit.infont.clear()
                unit.behind.clear()
            }

            for (idxA in 0 until units.size) {

                val a = units[idxA]

                for (idxB in idxA + 1 until units.size) {
                    val b = units[idxB]

                    val front = getFront(a, b)

                    if (front != null) {
                        if (a == front) {
                            a.behind.add(b)
                            b.infont.add(a)
                        } else {
                            b.behind.add(a)
                            a.infont.add(b)
                        }
                    }
                }
            }
        }
    }
}