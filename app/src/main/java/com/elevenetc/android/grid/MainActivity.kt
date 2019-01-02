package com.elevenetc.android.grid

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.elevenetc.android.grid.dynamic.DynamicMap
import com.elevenetc.android.grid.dynamic.Utils
import com.elevenetc.android.grid.views.Cube
import com.elevenetc.android.grid.views.Square

class MainActivity : AppCompatActivity() {

    lateinit var moving: com.elevenetc.android.grid.dynamic.Square
    lateinit var map: DynamicMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val gridView = findViewById<GridMatrixView>(R.id.grid_view)

        gridView.init(initDynamicMap())

        val a = animate(0, 6, true, gridView)
        val b = animate(1, 5, false, gridView)
        val c = animate(6, 0, true, gridView)
        val d = animate(5, 1, false, gridView)
//        val set = AnimatorSet().apply {
//            playSequentially(a, b, c, d)
//        }

        val set = AnimatorSet().apply {
            playSequentially(
                    animate(1, 2, false, gridView),
                    animate(2, 1, false, gridView)
            )
        }

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                //set.start()
            }

        })

        set.start()
    }

    fun animate(from: Int, to: Int, xAxis: Boolean, grid: View): ValueAnimator {
        return ValueAnimator.ofFloat(from.toFloat(), to.toFloat()).apply {
            duration = 1000L
            addUpdateListener {

                if (xAxis) {
                    moving.isoX = it.animatedValue as Float
                } else {
                    moving.isoY = it.animatedValue as Float
                }

                Utils.layout(moving, false)
                grid.invalidate()
            }
        }
    }

    private fun initDynamicMap(): DynamicMap {
        map = DynamicMap(130f)

        val builder = com.elevenetc.android.grid.dynamic.Square.Builder(200f, 100f)
        val movingId = "moving"
        val units = builder

                .addRect(1, 2, 5, 1, Color.RED, "long-red")
                .addSquare(4, 0, Color.CYAN, "cyan")
                .addSquare(3, 3, Color.BLUE, "blue")
                .addSquare(1, 3, Color.YELLOW, "yellow")
                .addSquare(0, 0, Color.GREEN, "green")
                .addRect(2, 3, 1, 2, Color.GRAY, "gray")
                .addSquare(1, 0, Color.BLUE, "blue-2")
                .addSquare(2, 0, Color.MAGENTA, "magenta")
                .addSquare(3, 0, Color.BLACK, "black")
                .addSquare(5, 0, Color.LTGRAY, "lt-gray")

                .addSquare(0, 1, Color.YELLOW, movingId)
                .translate(400f, 400f)
                .build()

        units.forEach {
            map.addItem(it)
        }

        moving = units.first { it.id == movingId }

        return map
    }

    private fun initRandom(): StaticMap {
        val rows = 100
        val cols = 100
        val model = StaticMap(rows, cols, 5f, 45f)

        var count = 200

        while (count > 0) {
            val r = (Math.random() * rows).toInt()
            val c = (Math.random() * cols).toInt()

            if (!model.occupied(r, c)) {
                val height = Math.random() * 50
                model.addItem(Cube(height.toFloat(), ""), r, c)
                count--
            }
        }

        return model
    }

    private fun initSmall(): StaticMap {
        val model = StaticMap(5, 5, 300f, 45f)
        model.addItem(Cube(150f, "0"), 0, 0)
        model.addItem(Cube(200f, "1"), 0, 1)
        model.addItem(Cube(80f, "2"), 0, 2)
        model.addItem(Cube(130f, "3"), 2, 2)
        return model
    }

    private fun initSmallRects(): StaticMap {
        val model = StaticMap(5, 5, 200f, 25f)
        model.addItem(Square("0"), 0, 0)
        model.addItem(Square("1"), 0, 1)
        model.addItem(Square("2"), 1, 1)
        model.addItem(Square("3"), 1, 0)
        return model
    }
}