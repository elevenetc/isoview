package com.elevenetc.android.grid

class IsoComparator : Comparator<UnitModel> {
    override fun compare(i1: UnitModel, i2: UnitModel): Int {


//        val boundsA = i1.getBounds()
//        val boundsB = i2.getBounds()
//
//        if (boundsA.minX > boundsB.maxX || boundsA.maxX < boundsB.minX) {
//            return if (boundsA.minY < boundsB.minY) {
//                1
//            } else {
//                -1
//            }
//        }

        if (i1.isoX >= (i2.isoX + i2.isoXSize)) {
            //Log.d("compare", "$i1 is infront of $i2")
            return 1
        }
        if (i2.isoX >= i1.isoX + i1.isoXSize) {
            //Log.d("compare", "$i1 is behind of $i2")
            return -1
        }

        if (i1.isoY >= (i2.isoY + i2.isoYSize)) {
            //Log.d("compare", "$i1 is infront of $i2")
            return 1
        }
        if (i2.isoY >= i1.isoY + i1.isoYSize) {
            //Log.d("compare", "$i1 is behind of $i2")
            return -1
        }

        //Log.d("compare", "$i1 is the same as $i2")

        return 0
    }
}