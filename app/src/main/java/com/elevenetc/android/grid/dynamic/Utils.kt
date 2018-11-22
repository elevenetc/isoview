package com.elevenetc.android.grid.dynamic

class Utils {
    companion object {

        fun layout(square: Square) {

            val unitWidth = square.unitWidth
            val unitHeight = square.unitHeight
            val x = square.isoX / 100
            val y = square.isoY / 100

            val isoX = x * (unitWidth / 2) - y * (unitWidth / 2)
            val isoY = (x + y) * (unitHeight / 2)

            square.topPoint = PointF(isoX, isoY)
            square.bottomPoint = PointF(isoX, isoY + unitHeight)
            square.leftPoint = PointF(isoX - unitWidth / 2, isoY + unitHeight / 2)
            square.rightPoint = PointF(isoX + unitWidth / 2, isoY + unitHeight / 2)



        }

        fun sort(units: List<DynamicItem>): MutableList<DynamicItem> {
            arrange(units)

            val result = mutableListOf<DynamicItem>()
            val u = mutableListOf(*units.toTypedArray())

            while (!u.isEmpty()) {
                val unit = u.removeAt(0)
                sort(unit, result)
                if (!result.contains(unit)) {
                    u.add(unit)
                }
            }
            return result
        }

        fun sort(current: DynamicItem, result: MutableList<DynamicItem>) {


            if (current.behind.isEmpty()) {

                if (!result.contains(current))
                    result.add(current)

                for (frontNode in current.infont) {
                    frontNode.behind.remove(current)
                }

            } else {

                current.behind.forEach { behind ->
                    sort(behind, result)
                }
            }
        }

        fun doOverlap(i1: DynamicItem, i2: DynamicItem): Boolean {
            return doOverlapIn(DynamicMap.Axis.X, i1, i2)
                    && doOverlapIn(DynamicMap.Axis.Y, i1, i2)
        }

        fun doOverlapIn(a: DynamicMap.Axis, i1: DynamicItem, i2: DynamicItem): Boolean {
            if (a == DynamicMap.Axis.X) {
                return doOverlap(i1.isoX, i1.isoX + i1.isoXSize, i2.isoX, i2.isoX + i2.isoXSize)
            } else if (a == DynamicMap.Axis.Y) {
                return doOverlap(i1.isoY, i1.isoY + i1.isoYSize, i2.isoY, i2.isoY + i2.isoYSize)
            } else {
                return false
            }
        }

        fun doOverlap(minA: Int, maxA: Int, minB: Int, maxB: Int): Boolean {

            if (minA == minB && maxA == maxB) return true

            val overlapFirst = maxA >= minB && minA <= maxB
            val overlapSecond = maxB >= minA && minB <= maxA
            return overlapFirst || overlapSecond
        }


        fun getFront(a: DynamicItem, b: DynamicItem): DynamicItem? {


            val doOverlapInX = doOverlapIn(DynamicMap.Axis.X, a, b)
            val doOverlapInY = doOverlapIn(DynamicMap.Axis.Y, a, b)

            return if (doOverlapInX && doOverlapInY) {

                when {
                    a.isoX == b.isoX + b.isoXSize -> a
                    b.isoX == a.isoX + a.isoXSize -> b
                    a.isoY == b.isoY + b.isoYSize -> a
                    b.isoY == a.isoY + a.isoYSize -> b
                    else -> null
                }


            } else if (doOverlapInX) {
                if (a.isoX < b.isoX) b else a
            } else if (doOverlapInY) {
                if (a.isoY < b.isoY) b else a
            } else {
                null
            }
        }

        fun arrange(items: List<DynamicItem>) {
            items.forEach {
                it.infont.clear()
                it.behind.clear()
            }

            for (i in 0 until items.size) {
                val a = items[i]
                for (k in i + 1 until items.size) {
                    val b = items[k]

                    val doOverlap = doOverlap(a, b)

                    if (!doOverlap) continue

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