package ui

import clamp
import com.soywiz.klock.TimeSpan
import com.soywiz.korev.Key
import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import level.LevelMap
import level.Tile
import level.TileType
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyType
import player.PlayerState
import ui.level.TILE_SIZE
import kotlin.math.abs

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
    private lateinit var rigidBody: Body
    private var state = PlayerState.FALLING
    private var stateTime = 0f

    //        var dir = Direction.LEFT
    private var hasDoubleJump = false
    private var grounded = false

    fun init(spawnTile: Tile) {

        position(spawnTile.x * TILE_SIZE, spawnTile.y * TILE_SIZE)
        solidRect(0.9 * TILE_SIZE, 1.5 * TILE_SIZE, Colors.PINK).xy(-TILE_SIZE / 2, -TILE_SIZE)
        registerBodyWithFixture(
            type = BodyType.DYNAMIC,
            density = 2,
            friction = 1,
            fixedRotation = true,
            shape = CircleShape(0.225)
        )
        this@Player.rigidBody = this.body!!

        setupCollision()
        setupControls()

    }

    private fun setupControls() {

        keys {
            justDown(Key.SPACE) {
                if (grounded) {
                    rigidBody.linearVelocityY = -6f
                } else if (hasDoubleJump){
                    hasDoubleJump = false
                    rigidBody.linearVelocityY = -6f
                }
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
            rigidBody.linearVelocityX = clamp(rigidBody.linearVelocityX + dx, -MAX_X_VEL, MAX_X_VEL)
        }
    }

    private fun setupCollision() {
        val previousTiles = mutableListOf<View>()
        onCollision(filter = {
            val tile = map.getTile((it.pos.x / TILE_SIZE).toInt(), (it.pos.y / TILE_SIZE).toInt())
            it is SolidRect
                    && (tile.x != 0 && tile.y != 0)
                    && !previousTiles.contains(it)
                    && tile.type != TileType.SPACE
        }) { other ->
            previousTiles.add(other)
            grounded = true
            hasDoubleJump = true
//            setPlayerState(PlayerState.IDLE)
        }
        onCollisionExit {
            previousTiles.remove(it)
            if (previousTiles.isEmpty()) grounded = false
        }
    }

    private fun setPlayerState(state: PlayerState) {
        println("${this.state} -> $state")
        this.state = state
        this.stateTime = 0f
    }

}