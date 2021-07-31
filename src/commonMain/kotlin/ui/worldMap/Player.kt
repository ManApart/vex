package ui.worldMap

import com.soywiz.klock.seconds
import com.soywiz.korev.GameButton
import com.soywiz.korev.GameStick
import com.soywiz.korev.Key
import com.soywiz.korge.animate.animateParallel
import com.soywiz.korge.input.gamepad
import com.soywiz.korge.input.keys
import com.soywiz.korge.tween.get
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.abs
import com.soywiz.korma.geom.degrees
import ui.level.TILE_SIZE
import worldMap.Exit

class Player(origin: Exit, private val exits: List<MapExit>, private val enterLevel: (Exit) -> Unit) : Container() {
    private var currentExit = exits.firstOrNull { it.exit == origin }!!
    private var goalExit = currentExit
    private var startAnimating = false

    suspend fun init() {
        position(currentExit.view.x, currentExit.view.y)
        solidRect(0.9 * TILE_SIZE, 1.5 * TILE_SIZE, Colors.PINK) {
            onCollision({ view ->
                view is SolidRect && exits.any { it.view == view }
            }) { view ->
                currentExit = exits.first { it.view == view }
            }
        }
        setupControls()
        addOnUpdate()
    }

    private fun setupControls() {
        gamepad {
            down(0, GameButton.BUTTON0) { enterLevel(currentExit.exit) }
        }
        keys {
            justDown(Key.SPACE){ enterLevel(currentExit.exit)}
        }
    }

    private suspend fun addOnUpdate() {
        addUpdaterWithViews { views, _ ->
            if (currentExit == goalExit) {
                val stick = views.input.connectedGamepads.firstOrNull()?.get(GameStick.LEFT)
                val stickAmount = stick?.magnitude ?: 0.0
                val stickAngle = stick?.let {
                    Angle.between(0.0, 0.0, it.x, -it.y)
                } ?: Angle.ZERO

                if (stickAmount > 0.2) {
                    val possibleExits = currentExit.connections
                        .filter { it.unlocked }
                        .map { it.getOther(currentExit) }
                    val exit = exits
                        .filter { possibleExits.contains(it) }
                        .minByOrNull { angleTowards(stickAngle, it) }

                    if (exit != null && angleTowards(stickAngle, exit) < 45.0) {
                        println("New Goal: $exit")
                        goalExit = exit
                        startAnimating = true
                    }
                }
            }
            if (startAnimating && currentExit != goalExit) {
                startAnimating = false
                stage?.launchImmediately {
                    animateParallel {
                        tween(::x[pos.x, goalExit.view.x], time = 1.seconds)
                        tween(::y[pos.y, goalExit.view.y], time = 1.seconds)
                    }
                }

            }
        }


    }

    private fun angleTowards(stickAngle: Angle, exit: MapExit): Double {
        return abs(Angle.shortDistanceTo(stickAngle, Angle.between(pos, exit.view.pos))).degrees
    }
}