package com.elevenetc.android.grid

import com.elevenetc.android.grid.dynamic.Utils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SortingTests {

    /**
     * ----------
     * |
     * |        0
     * |    00000
     * |    000
     * |
     */
    @Test
    fun test() {
        val builder = Builder(100f, 100f)

        val square = "moving"
        val longMid = "magenta"
        val longFront = "gray"

        val views = builder
                .addSquare(5, 1, StubView(), square)
                .addRect(1, 2, 5, 1, StubView(), longMid)
                .addRect(1, 3, 4, 1, StubView(), longFront)
                .build()

        val models = views.map { v -> v.model }

        Utils.sort(models)

        assertThat(models[0].behind).isEmpty()

        assertThat(models[0].id).isEqualTo(square)
        assertThat(models[1].id).isEqualTo(longMid)
        assertThat(models[2].id).isEqualTo(longFront)
    }

    /**
     * ----------
     * | 0
     * | 0
     * |  000000
     * |
     */
    @Test
    fun test2() {
        val builder = Builder(100f, 100f)

        val top = "top"
        val mid = "mid"
        val bottom = "bottom-long"

        val views = builder

                .addSquare(0, 1, StubView(), mid)

                .addSquare(0, 0, StubView(), top)

                .addRect(1, 2, 5, 1, StubView(), bottom)
                .build()

        val models = views.map { v -> v.model }

        Utils.sort(models)

        assertThat(models[0].behind).isEmpty()

        assertThat(models[0].id).isEqualTo(top)
        assertThat(models[1].id).isEqualTo(mid)
        assertThat(models[2].id).isEqualTo(bottom)
    }

    class StubView : UnitView()
}