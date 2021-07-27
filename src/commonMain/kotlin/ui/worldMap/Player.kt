package ui.worldMap

import com.soywiz.klock.seconds
import com.soywiz.korev.GameStick
import com.soywiz.korge.animate.animateSequence
import com.soywiz.korge.animate.launchAnimate
import com.soywiz.korge.tween.V2
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.degrees
import org.jbox2d.common.MathUtils.Companion.distance
import ui.level.TILE_SIZE
import worldMap.Exit

class Player(origin: Exit, private val exits: List<MapExit>) : Container() {
    private var currentExit = exits.firstOrNull { it.exit == origin }!!
    private var goalExit = currentExit
    private var startAnimating = false
    private lateinit var rect: SolidRect

    suspend fun init() {
        position(currentExit.view.x, currentExit.view.y)
        rect = solidRect(0.9 * TILE_SIZE, 1.5 * TILE_SIZE, Colors.PINK)

        setupControls()
        addOnUpdate()
    }

    private fun setupControls() {

    }

    private suspend fun addOnUpdate() {
        addUpdaterWithViews { views, dt ->
            if (currentExit == goalExit) {
                val stick = views.input.connectedGamepads.firstOrNull()?.get(GameStick.LEFT)
                val stickAmount = stick?.magnitude ?: 0.0
                val stickAngle = stick?.let {
                    Angle.between(0.0, 0.0, it.x, it.y)
                } ?: Angle.ZERO

                if (stickAmount > 0.2) {
//                    println("Angle: $stickAngle")
                    val exit = exits.filter { it != currentExit }.minByOrNull { Angle.shortDistanceTo(stickAngle, Angle.between(pos, it.view.pos)) }
                    if (exit != null && Angle.between(pos, exit.view.pos).degrees < 45.0){
                        println("New Goal: $exit")
                        goalExit = exit
                        startAnimating = true
                    }
                }
            }
            if (startAnimating && currentExit != goalExit) {
                startAnimating = false
//            tween(V2(pos, goalExit.view.pos, time = 1.seconds)
                stage?.launchImmediately {
                    rect.tween(rect::x[rect.pos.x, goalExit.view.x], time = 1.seconds)
                }

            }
        }


    }
}