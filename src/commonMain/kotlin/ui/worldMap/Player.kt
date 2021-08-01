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
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.abs
import com.soywiz.korma.geom.degrees
import player.PlayerAnimator
import player.PlayerState
import ui.level.TILE_SIZE
import worldMap.Exit

class Player(origin: Exit, private val exits: List<MapExit>, private val enterLevel: (Exit) -> Unit) : Container() {
    private var currentExit = exits.firstOrNull { it.exit.level.id == origin.level.id && it.exit.id == origin.id }!!
    private var goalExit = currentExit
    private var startMoving = false
    private lateinit var animator: PlayerAnimator

    suspend fun init() {
        position(currentExit.view.x, currentExit.view.y)
        solidRect(0.9 * TILE_SIZE, 1.5 * TILE_SIZE) {
            alpha = 0.0
            onCollision({ view ->
                view is Circle && goalExit.view == view
            }) { view ->
                currentExit = exits.first { it.view == view }
                animator.evaluate(PlayerState.IDLE)
            }
        }
        setupControls()
        buildSprite()
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

    private suspend fun buildSprite() {
        val image = Resources.getImage("character.png")
        val sprite = sprite()
        sprite.smoothing = false
        sprite.scale = 0.8
        animator = PlayerAnimator(image, sprite)
        sprite.xy(5, 0)
        sprite.anchor(Anchor.MIDDLE_CENTER)

        animator.evaluate(PlayerState.IDLE)
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
                        val right = exit.view.x > currentExit.view.x
                        goalExit = exit
                        startMoving = true
                        animator.setFacing(right)
                        animator.evaluate(PlayerState.RUNNING)
                    }
                }
            }
            if (startMoving && currentExit != goalExit) {
                startMoving = false
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