package ui

import clamp
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.timesPerSecond
import com.soywiz.korev.Key
import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Point
import level.LevelMap
import level.Tile
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.BodyType
import ui.level.TILE_SIZE
import kotlin.math.abs
import kotlin.math.min

const val MAX_X_VEL = 2f
const val MAX_X_AIR_VEL = 6.0
const val MAX_Y_VEL = 10.0
const val GRAVITY = 20.0
private const val ACCELERATION = .2f
private const val FRICTION = 1.0

private const val JUMP_VELOCITY = 10
private const val WALL_JUMP_KICKOFF_VELOCITY = 5
private const val WALL_JUMP_KICKOFF_VELOCITY_Y = 6
private const val JUMP_TIME = .1
private const val WALL_JUMP_TIME = .1

private const val DASH_VELOCITY = 20
private const val DASH_TIME = .15

class Player(private val map: LevelMap) : Container() {

    fun init(spawnTile: Tile) {
        solidRect(0.9 * TILE_SIZE, 1.5 * TILE_SIZE, Colors.PINK)
        position(spawnTile.x * TILE_SIZE, spawnTile.y * TILE_SIZE)

        registerBodyWithFixture(type = BodyType.DYNAMIC, density = 2, friction = 1, fixedRotation = true)
        setupControls()
    }

    private fun setupControls() {

        keys {
            up(Key.SPACE) {
                body?.let { it.linearVelocityY += 30 }
            }
        }
        addUpdaterWithViews { views: Views, dt: TimeSpan ->
            var dx = 0f
            val scale = dt.milliseconds.toFloat() / 20
            with(views.input) {
                when {
                    keys[Key.RIGHT] -> dx = ACCELERATION * scale
                    keys[Key.LEFT] -> dx = -ACCELERATION * scale
                }

            }
            body?.let { it.linearVelocityX = clamp(it.linearVelocityX + dx, -MAX_X_VEL, MAX_X_VEL) }
        }
    }

}