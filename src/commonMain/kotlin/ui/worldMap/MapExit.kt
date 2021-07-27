package ui.worldMap

import com.soywiz.korge.view.SolidRect
import worldMap.Exit

data class MapExit(val exit: Exit, val view: SolidRect) {
    override fun toString(): String {
        return view.pos.toString()
    }
}