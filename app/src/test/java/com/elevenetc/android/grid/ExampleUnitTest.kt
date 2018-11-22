package com.elevenetc.android.grid

import android.graphics.Canvas
import com.elevenetc.android.grid.dynamic.DynamicItem
import com.elevenetc.android.grid.dynamic.Square
import com.elevenetc.android.grid.dynamic.Utils.Companion.sort
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    data class Item(private val bounds: Bounds) : DynamicItem("") {

        override fun getBounds(): Bounds {
            return bounds
        }


        override fun draw(canvas: Canvas, angle: Float) {

        }

        override fun layout(angle: Float) {

        }

    }

    @Test
    fun testZ() {
        val units = Square.Builder(100f, 100f)
                .addSquare(0, 0, 1, "top")

                .addSquare(1, 1, 1, "middle")
                .addSquare(2, 2, 1, "bottom")
                .build()

        val result = sort(units)

        println(result)
    }


}
