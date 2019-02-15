package com.elevenetc.android.grid

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.elevenetc.android.grid.dynamic.Box
import com.elevenetc.android.grid.dynamic.DynamicMap
import com.elevenetc.android.grid.dynamic.Utils
import com.elevenetc.android.grid.views.Cube

class MainActivity : AppCompatActivity() {

    lateinit var moving: UnitView
    lateinit var map: DynamicMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRestartActivity()

        val gridView = findViewById<GridMatrixView>(R.id.grid_view)

        gridView.init(initDynamicMap())

        val set = mulripleSteps(gridView)
//        val set = singleAnimatedStep(gridView)

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

        //set.start()
    }

    private fun mulripleSteps(gridView: GridMatrixView): AnimatorSet {
        val a = animate(0, 6, true, gridView)
        val b = animate(1, 5, false, gridView)
        val c = animate(6, 0, true, gridView)
        val d = animate(5, 1, false, gridView)
        return AnimatorSet().apply {
            playSequentially(a, b, c, d)

        }
    }

    private fun singleAnimatedStep(gridView: GridMatrixView): AnimatorSet {
        val set = AnimatorSet().apply {
            playSequentially(
                    animate(1, 2, false, gridView),
                    animate(2, 1, false, gridView)
            )
        }
        return set
    }

    private fun setRestartActivity() {
        findViewById<View>(R.id.btn_restart).setOnClickListener {
            val i = intent
            finish()
            startActivity(i)
        }
    }

    fun animate(from: Int, to: Int, xAxis: Boolean, grid: View): ValueAnimator {
        return ValueAnimator.ofFloat(from.toFloat(), to.toFloat()).apply {
            duration = 3000L
            addUpdateListener {

                if (xAxis) {
                    moving.model.isoX = it.animatedValue as Float
                } else {
                    moving.model.isoY = it.animatedValue as Float
                }

                Utils.layout(moving.model)
                grid.invalidate()
            }
        }
    }

    val movingId = "moving"

    private fun initDynamicMap(): DynamicMap {
        map = DynamicMap()

        val builder = Builder(200f, 100f)

        //val views = justTwo(builder)
        val views = justSeven(builder)

        views.forEach {
            map.addItem(it)
        }

        moving = views.first { v -> v.id == movingId }

        return map
    }

    private fun justSeven(builder: Builder): List<UnitView> {
        val views = builder
                .addSquare(0, 1, Box(Color.YELLOW), movingId)

                .addSquare(0, 0, Box(Color.GREEN), "green")
//                .addSquare(1, 0, Box(Color.RED), "red")
//                .addSquare(2, 0, Box(Color.CYAN), "CYAN")

//                .addSquare(1, 2, Box(Color.MAGENTA), "mag")

                .addRect(1, 2, 5, 1, Box(Color.MAGENTA), "long-magenta")
//                .addRect(1, 3, 4, 1, Box(Color.DKGRAY), "long-dkgray")
//                .addRect(1, 4, 3, 1, Box(Color.GREEN), "long-green")

                .translate(400f, 400f)
                .build()
        return views
    }

    private fun justTwo(builder: Builder): List<UnitView> {
        val views = builder
                .addSquare(0, 1, Box(Color.YELLOW), movingId)
                .addSquare(0, 0, Box(Color.GREEN), "green")
                .translate(400f, 400f)
                .build()
        return views
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
//        model.addItem(Square("0"), 0, 0)
//        model.addItem(Square("1"), 0, 1)
//        model.addItem(Square("2"), 1, 1)
//        model.addItem(Square("3"), 1, 0)
        return model
    }
}