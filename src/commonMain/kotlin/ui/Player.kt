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
import ui.level.TILE_SIZE

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
    private lateinit var body2: Body

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
            this@Player.body2 = this.body!!

            setupCollision()
            setupControls()

    }

    private fun setupControls() {

        keys {
            justDown(Key.SPACE) {
                body2.linearVelocityY = -6f
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
            body2.linearVelocityX = clamp(body2.linearVelocityX + dx, -MAX_X_VEL, MAX_X_VEL)
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
            val tile = map.getTile((other.pos.x / TILE_SIZE).toInt(), (other.pos.y / TILE_SIZE).toInt())
            previousTiles.add(other)
        }
        onCollisionExit {
            previousTiles.remove(it)
        }
    }

}