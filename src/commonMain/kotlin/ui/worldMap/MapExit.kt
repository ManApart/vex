package ui.worldMap

import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.view.SolidRect
import com.soywiz.korge.view.View
import worldMap.Exit

data class MapExit(val exit: Exit, val view: SolidRect) {
    override fun toString(): String {
        return view.pos.toString()
    }
}

data class MapExit2(val exit: Exit, val obj: TiledMap.Object, val view: View) {
    override fun toString(): String {
        return view.pos.toString()
    }
}