package ui

import clamp
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.timesPerSecond
import com.soywiz.korev.Key
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Point
import level.LevelMap
import level.Tile
import org.jbox2d.dynamics.BodyType
import ui.level.TILE_SIZE
import kotlin.math.abs
import kotlin.math.min

const val MAX_X_VEL = 6.0
const val MAX_X_AIR_VEL = 6.0
const val MAX_Y_VEL = 10.0
const val GRAVITY = 20.0
private const val ACCELERATION = .2
private const val FRICTION = 1.0

private const val JUMP_VELOCITY = 10
private const val WALL_JUMP_KICKOFF_VELOCITY = 5
private const val WALL_JUMP_KICKOFF_VELOCITY_Y = 6
private const val JUMP_TIME = .1
private const val WALL_JUMP_TIME = .1

private const val DASH_VELOCITY = 20
private const val DASH_TIME = .15

class Player(private val map: LevelMap) : Container() {
    var velocity = Point()

    fun init(spawnTile: Tile) {
        solidRect(TILE_SIZE, 2 * TILE_SIZE, Colors.PINK)
        position(spawnTile.x * TILE_SIZE, spawnTile.y * TILE_SIZE)

        registerBodyWithFixture(type = BodyType.DYNAMIC, density = 2, friction = 0.01, gravityScale = 0.0)
//            registerBodyWithFixture(type = BodyType.DYNAMIC, density = 2, friction = 0.01)
        setupControls()
        addFixedUpdater(30.timesPerSecond) {
            position(pos.x + velocity.x, pos.y + velocity.y)
        }
    }

    private fun setupControls() {
        var dx = 0.0
        var dy = 0.0

        keys {

        }
        addUpdaterWithViews { views: Views, dt: TimeSpan ->
            val scale = dt.milliseconds / 1000
            with(views.input) {
                when {
                    keys[Key.RIGHT] -> dx += ACCELERATION * scale
                    keys[Key.LEFT] -> dx -= ACCELERATION * scale
                }

            }
            velocity = Point(clamp(velocity.x + dx, -MAX_X_VEL, MAX_X_VEL), 0.0)
        }
    }

}