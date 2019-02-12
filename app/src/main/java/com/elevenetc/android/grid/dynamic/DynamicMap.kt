package com.elevenetc.android.grid.dynamic

import android.graphics.Canvas
import com.elevenetc.android.grid.Map
import com.elevenetc.android.grid.UnitModel
import com.elevenetc.android.grid.UnitView

class DynamicMap : Map {

    val views = mutableListOf<UnitView>()
    val models = mutableListOf<UnitModel>()
    val viewModels = mutableMapOf<String, UnitView>()

    fun addItem(view: UnitView) {
        val model = view.model
        views.add(view)
        models.add(model)

        viewModels[model.id] = view
    }

    override fun draw(canvas: Canvas) {
        val drawList = sort()
        drawList.forEach {
            viewModels[it.id]!!.draw(canvas)
        }
    }

    fun sort(): List<UnitModel> {
        val sort = Utils.sort(models)
        return sort
    }


}