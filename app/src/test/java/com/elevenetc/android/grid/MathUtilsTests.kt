package com.elevenetc.android.grid

import com.elevenetc.android.grid.dynamic.Utils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MathUtilsTests {

    @Test
    fun doOverlap() {
        //a: ---
        //b:  ---
        assertThat(Utils.doOverlap(0f, 1f, 0.5f, 1.5f)).isTrue()

        //a: ---
        //b: ---
        assertThat(Utils.doOverlap(0f, 1f, 0f, 1f)).isTrue()

        //a:  ---
        //b: ---
        assertThat(Utils.doOverlap(0.5f, 1.5f, 0f, 1f)).isTrue()

        //a: -----
        //b:  ---
        assertThat(Utils.doOverlap(0f, 2f, 0.5f, 1.5f)).isTrue()

        //a:  ---
        //b: -----
        assertThat(Utils.doOverlap(0.5f, 1.5f, 0f, 2f)).isTrue()
    }

    @Test
    fun doNotOverlap() {
        //a: ---
        //b:     ---
        assertThat(Utils.doOverlap(0f, 1f, 1.5f, 2.5f)).isFalse()

        //a:     ---
        //b: ---
        assertThat(Utils.doOverlap(2f, 3f, 0f, 1f)).isFalse()
    }


}
