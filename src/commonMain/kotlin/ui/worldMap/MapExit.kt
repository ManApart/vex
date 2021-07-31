package ui.worldMap

import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.view.SolidRect
import com.soywiz.korge.view.View
import worldMap.Connection
import worldMap.Exit

data class MapExit(val exit: Exit, val obj: TiledMap.Object, val view: View) {
    val connections = mutableListOf<Connection>()
    override fun toString(): String {
        return view.pos.toString()
    }
}