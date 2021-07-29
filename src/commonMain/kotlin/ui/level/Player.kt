package ui.level

import clamp
import com.soywiz.klock.TimeSpan
import com.soywiz.korev.GameButton
import com.soywiz.korev.GameStick
import com.soywiz.korev.Key
import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.input.gamepad
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import level.LevelMap
import level.Tile
import level.TileType
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyType
import physics.Rectangle
import player.PlayerAnimator
import player.PlayerState
import ui.Trigger
import kotlin.math.abs

const val MAX_X_VEL = 2f
const val MAX_X_AIR_VEL = 6.0f
const val MAX_Y_VEL = 10.0f
private const val ACCELERATION = .2f
private const val FRICTION = 1.0

private const val JUMP_VELOCITY = 3f
private const val WALL_JUMP_KICKOFF_VELOCITY = 5f
private const val WALL_JUMP_KICKOFF_VELOCITY_Y = 6f
private const val JUMP_TIME = 300

private const val DASH_VELOCITY = 10f
private const val DASH_TIME = 150.0

class Player(private val map: LevelMap, private val exitLevel: (Int, Int) -> Unit) : Container() {
    private lateinit var rigidBody: Body
    private var state = PlayerState.FALLING
    private var stateTime = 0.0
    private lateinit var sprite: Sprite
    private lateinit var animator: PlayerAnimator

    private var goingRight = true
    private var hasDoubleJump = false
    private var hasDash = true
    private var grounded = false
    private var touchingWallLeft = false
    private var touchingWallRight = false
    private var jumpHeld = false

    suspend fun init(spawnTile: Tile) {
        position(spawnTile.x * TILE_SIZE, spawnTile.y * TILE_SIZE)

        buildSprite()
        addTriggers()

        registerBodyWithFixture(
            type = BodyType.DYNAMIC,
            density = 2,
            friction = FRICTION,
            fixedRotation = true,
            shape = CircleShape(0.225),
            restitution = 0
        )
        this@Player.rigidBody = this.body!!

        setupControls()
        addOnUpdate()
    }

    private fun addTriggers() {
        val groundRect = Rectangle(-0.9f * TILE_SIZE / 2, TILE_SIZE / 2f, 0.9f * TILE_SIZE, .3f * TILE_SIZE)
        Trigger(this, groundRect, map, ::onGroundContact, ::onLeaveGround, true)

        Trigger(
            this,
            Rectangle(TILE_SIZE / 2.7f, TILE_SIZE / 3f, TILE_SIZE / 1.5f, .3f * TILE_SIZE),
            map,
            { touchingWallRight = true },
            { touchingWallRight = false },
            true,
            Colors.RED
        )
        Trigger(
            this,
            Rectangle(-TILE_SIZE * 1f, TILE_SIZE / 3f, TILE_SIZE / 1.5f, .3f * TILE_SIZE),
            map,
            { touchingWallLeft = true },
            { touchingWallLeft = false },
            true,
            Colors.YELLOWGREEN
        )
    }

    private suspend fun buildSprite() {
        val image = Resources.getImage("character.png")
        sprite = sprite()
        sprite.smoothing = false
        sprite.scale = 0.8
        animator = PlayerAnimator(image, sprite)
        sprite.xy(0, 10)
        sprite.anchor(Anchor.BOTTOM_CENTER)

        animator.evaluate(state)
    }

    private fun setupControls() {

        gamepad {
            down(0, GameButton.BUTTON0) { jump(); jumpHeld = true }
            up(0, GameButton.BUTTON0) { jumpHeld = false }
            down(0, GameButton.L1) { dash(false) }
            down(0, GameButton.R1) { dash(true) }
            down(0, GameButton.BUTTON2) { interact() }
        }

        keys {
            down(Key.SPACE) { jump(); jumpHeld = true }
            up(Key.SPACE) { jumpHeld = false }
            justDown(Key.Z) { dash(false) }
            justDown(Key.X) { dash(true) }
            justDown(Key.ENTER) { interact() }
        }

        addUpdaterWithViews { views: Views, dt: TimeSpan ->
            var dx = 0f
            val scale = dt.milliseconds.toFloat() / 20
            with(views.input) {
                val buttons = connectedGamepads.firstOrNull()
                val stickAmount = buttons?.get(GameStick.LEFT)?.x ?: 0.0
                when {
                    abs(stickAmount) > .1f -> dx = (stickAmount * ACCELERATION * scale).toFloat()
                    keys[Key.RIGHT] -> dx = ACCELERATION * scale
                    keys[Key.LEFT] -> dx = -ACCELERATION * scale
                }
            }
            if (dx != 0f) {
                goingRight = dx > 0
                animator.setFacing(goingRight)
            }
            rigidBody.linearVelocityX = clamp(rigidBody.linearVelocityX + dx, -MAX_X_VEL, MAX_X_VEL)
        }
    }

    private fun addOnUpdate() {
        addUpdater { dt ->
            stateTime += dt.milliseconds
            if (grounded) hasDash = true

            when (state) {
                PlayerState.DASHING -> {
                    if (stateTime > DASH_TIME) {
                        val newState = if (grounded) PlayerState.RUNNING else PlayerState.FALLING
                        setPlayerState(newState)
                    } else {
                        rigidBody.linearVelocityX = if (goingRight) DASH_VELOCITY else -DASH_VELOCITY
                    }
                }
                PlayerState.JUMPING -> {
                    if (stateTime < JUMP_TIME && jumpHeld) {
                        rigidBody.linearVelocityY = -JUMP_VELOCITY
                    } else {
                        setPlayerState(PlayerState.FALLING)
                    }
                }
                PlayerState.RUNNING -> {
                    if (abs(rigidBody.linearVelocityX) < .1) {
                        setPlayerState(PlayerState.IDLE)
                    }
                }
                PlayerState.IDLE -> {
                    if (abs(rigidBody.linearVelocityX) > .1) {
                        setPlayerState(PlayerState.RUNNING)
                    }
                }
            }
            if (state != PlayerState.DASHING) {
                if (grounded) {
                    rigidBody.linearVelocityX = clamp(rigidBody.linearVelocityX, -MAX_X_VEL, MAX_X_VEL)
                } else {
                    rigidBody.linearVelocityX = clamp(rigidBody.linearVelocityX, -MAX_X_AIR_VEL, MAX_X_AIR_VEL)
                }
            }
            rigidBody.linearVelocityY = clamp(rigidBody.linearVelocityY, -MAX_Y_VEL, MAX_Y_VEL)
        }
    }

    private fun jump() {
        if (jumpHeld) return
        when {
            grounded -> {
                rigidBody.linearVelocityY = -JUMP_VELOCITY
                setPlayerState(PlayerState.JUMPING)
            }
            touchingWallRight -> {
                rigidBody.linearVelocityX = -WALL_JUMP_KICKOFF_VELOCITY
                rigidBody.linearVelocityY = -WALL_JUMP_KICKOFF_VELOCITY_Y
                setPlayerState(PlayerState.JUMPING)
            }
            touchingWallLeft -> {
                rigidBody.linearVelocityX = WALL_JUMP_KICKOFF_VELOCITY
                rigidBody.linearVelocityY = -WALL_JUMP_KICKOFF_VELOCITY_Y
                setPlayerState(PlayerState.JUMPING)
            }
            hasDoubleJump -> {
                hasDoubleJump = false
                rigidBody.linearVelocityY = -JUMP_VELOCITY
                setPlayerState(PlayerState.JUMPING)
            }
        }
    }

    private fun dash(right: Boolean = true) {
        if (state == PlayerState.DASHING || !hasDash) return
        hasDash = false
        if (right) {
            goingRight = true
            rigidBody.linearVelocityX = DASH_VELOCITY
        } else {
            goingRight = false
            rigidBody.linearVelocityX = -DASH_VELOCITY
        }
        animator.setFacing(right)
        setPlayerState(PlayerState.DASHING)
    }

    private fun setPlayerState(state: PlayerState) {
        if (this.state != state) {
            val right = if (goingRight) "right" else "left"
            println("${this.state} -> $state $right")
            animator.evaluate(state)
            this.state = state
            this.stateTime = 0.0
        }
    }

    private fun interact() {
        val tile = map.getTile((pos.x / TILE_SIZE).toInt(), (pos.y / TILE_SIZE).toInt())
        if (tile.type == TileType.EXIT) {
            exitLevel(map.id, tile.id)
        }
    }

    private fun onLeaveGround() {
        grounded = false
    }

    private fun onGroundContact() {
        grounded = true
        hasDoubleJump = true
        hasDash = true
        if (state == PlayerState.FALLING) setPlayerState(PlayerState.RUNNING)
    }

}