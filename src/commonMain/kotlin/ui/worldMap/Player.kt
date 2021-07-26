package ui.worldMap

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import ui.level.TILE_SIZE
import worldMap.Exit

class Player (origin: Exit) : Container(){
    var currentExit = origin
    var goalExit = origin

    init {
//        parent?.addChild(this)
        position(origin.bounds.x, origin.bounds.y)
        solidRect(0.9 * TILE_SIZE, 1.5 * TILE_SIZE, Colors.PINK)

        setupControls()
        addOnUpdate()
    }

    private fun setupControls() {

    }

    private fun addOnUpdate() {

    }
}