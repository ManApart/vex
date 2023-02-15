package ui.level

import RigidBody
import center
import clamp
import com.soywiz.klock.TimeSpan
import com.soywiz.korev.GameButton
import com.soywiz.korev.GameStick
import com.soywiz.korev.Key
import com.soywiz.korge.input.gamepad
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.plus
import com.soywiz.korma.geom.vector.StrokeInfo
import com.soywiz.korma.geom.vector.line
import player.PlayerAnimator
import player.PlayerState
import sign
import toAngle
import ui.Trigger
import kotlin.math.abs

const val MAX_X_VEL = 2f
const val MAX_X_AIR_VEL = 6.0f
const val MAX_Y_VEL = 10.0f
private const val ACCELERATION_X = .2f
private const val FRICTION = .05f
private const val GRAVITY = 1f

private const val JUMP_VELOCITY = 4f
private const val WALL_JUMP_KICKOFF_VELOCITY = 5f
private const val WALL_JUMP_KICKOFF_VELOCITY_Y = 6f
private const val JUMP_TIME = 300

private const val DASH_VELOCITY = 10f
private const val DASH_TIME = 150.0

private const val DEBUG_TIME_SCALE =  10f
private const val TIME_SCALE =  30f + DEBUG_TIME_SCALE
//It'd be nice to use a time scale that seems tied to 60 fps: Divide delta time by 60 frames a second
//private const val TIME_SCALE =  1000f * 60 + DEBUG_TIME_SCALE

class Player(private val interact: (View) -> Unit) : Container() {
    //class Player(private val map: LevelMap, private val exitLevel: (Int, Int) -> Unit) : Container() {
    private val body = RigidBody(this)
    private var state = PlayerState.FALLING
    private var stateTime = 0.0
    private lateinit var animator: PlayerAnimator
    private lateinit var interactBox: SolidRect

    private var goingRight = true
    private var hasDoubleJump = false
    private var hasDash = true
    private var grounded = false
    private var touchingWallLeft = false
    private var touchingWallRight = false
    private var jumpHeld = false
    private var grapple: GrapplingHook? = null

    suspend fun init(spawn: SolidRect) {
        centerOn(spawn)

        buildSprite()
        addTriggers()

        setupControls(spawn)
        addOnUpdate()
        paintGrapplingHook()
    }

    private fun addTriggers() {
        val groundRect = Rectangle(-0.9f * TILE_SIZE / 2, TILE_SIZE / 2f, 0.9f * TILE_SIZE, .3f * TILE_SIZE)
        Trigger(this, groundRect, ::onGroundContact, ::onLeaveGround, false)

        Trigger(
            this,
            Rectangle(TILE_SIZE / 2.7f, TILE_SIZE / 3f, TILE_SIZE / 1.5f, .3f * TILE_SIZE),
            { touchingWallRight = true },
            { touchingWallRight = false },
            false,
            Colors.RED
        )
        Trigger(
            this,
            Rectangle(-TILE_SIZE * 1f, TILE_SIZE / 3f, TILE_SIZE / 1.5f, .3f * TILE_SIZE),
            { touchingWallLeft = true },
            { touchingWallLeft = false },
            false,
            Colors.YELLOWGREEN
        )
    }

    private suspend fun buildSprite() {
        val image = Resources.getImage("character.png")
        val sprite = sprite()
        sprite.smoothing = false
        sprite.scale = 0.8
        animator = PlayerAnimator(image, sprite)
        sprite.xy(0, 5)
        sprite.anchor(Anchor.BOTTOM_CENTER)

        animator.evaluate(state)

        interactBox = solidRect(10, 22) {
            alpha = 0.0
            xy(-5, -18)
        }
        body.addCollision(Rectangle(-5,-18,10,22))
    }

    private fun setupControls(spawn: SolidRect) {

        gamepad {
            down(0, GameButton.BUTTON0) { jump(); jumpHeld = true }
            up(0, GameButton.BUTTON0) { jumpHeld = false }
            down(0, GameButton.L1) { dash(false) }
            down(0, GameButton.R1) { dash(true) }
            down(0, GameButton.BUTTON2) { interact(interactBox) }
            down(0, GameButton.XBOX_Y) { centerOn(spawn) }
        }

        keys {
            down(Key.SPACE) { jump(); jumpHeld = true }
            up(Key.SPACE) { jumpHeld = false }
            justDown(Key.Z) { dash(false) }
            justDown(Key.X) { dash(true) }
            justDown(Key.ENTER) { interact(interactBox) }
        }

        addUpdaterWithViews { views: Views, dt: TimeSpan ->
            var dx = 0f
            val scale = dt.milliseconds.toFloat() / TIME_SCALE
            with(views.input) {
                val gamepad = connectedGamepads.firstOrNull()
                val leftStick = gamepad?.get(GameStick.LEFT)
                val stickAmount = leftStick?.x ?: 0.0
                when {
                    abs(stickAmount) > .1f -> dx = (stickAmount * ACCELERATION_X * scale).toFloat()
                    keys[Key.RIGHT] -> dx = ACCELERATION_X * scale
                    keys[Key.LEFT] -> dx = -ACCELERATION_X * scale
                }
                val trigger = gamepad?.get(GameButton.R2) ?: 0.0
                if (abs(trigger) > .2) {
                    if (grapple == null) {
                        val aim = gamepad!![GameStick.RIGHT].toAngle()
                        grapple = GrapplingHook(this@Player, aim)
                        parent?.addChild(grapple!!)
                    }
                } else if (grapple != null) {
                    grapple?.release()
                    grapple = null
                }
            }
            if (dx != 0f) {
                goingRight = dx > 0
                animator.setFacing(goingRight)
            }
            body.linearVelocityX = clamp(body.linearVelocityX + dx, -MAX_X_VEL, MAX_X_VEL)
        }
    }

    private fun addOnUpdate() {
        addUpdater { dt ->
            val delta = dt.milliseconds.toFloat() / TIME_SCALE
            stateTime += dt.milliseconds
            if (grounded) hasDash = true
            body.linearVelocityY -= GRAVITY * delta
            val absX = abs(body.linearVelocityX)
            if (grounded && absX > 0){
                val frictionDelta = clamp(FRICTION, 0f, absX)
                body.linearVelocityX += body.linearVelocityX.sign() * -frictionDelta
            }

            when (state) {
                PlayerState.DASHING -> {
                    if (stateTime > DASH_TIME) {
                        val newState = if (grounded) PlayerState.RUNNING else PlayerState.FALLING
                        setPlayerState(newState)
                    } else {
                        body.linearVelocityY = 0f
                        body.linearVelocityX = if (goingRight) DASH_VELOCITY else -DASH_VELOCITY
                    }
                }

                PlayerState.JUMPING -> {
                    if (stateTime < JUMP_TIME && jumpHeld) {
                        body.linearVelocityY = JUMP_VELOCITY
                    } else {
                        setPlayerState(PlayerState.FALLING)
                    }
                }

                PlayerState.RUNNING -> {
                    if (abs(body.linearVelocityX) < .1) {
                        setPlayerState(PlayerState.IDLE)
                    }
                }

                PlayerState.IDLE -> {
                    if (abs(body.linearVelocityX) > .1) {
                        setPlayerState(PlayerState.RUNNING)
                    }
                }

                else -> {}
            }
            if (state != PlayerState.DASHING) {
                if (grounded) {
                    body.linearVelocityX = clamp(body.linearVelocityX, -MAX_X_VEL, MAX_X_VEL)
                } else {
                    body.linearVelocityX = clamp(body.linearVelocityX, -MAX_X_AIR_VEL, MAX_X_AIR_VEL)
                }
            }
            body.linearVelocityY = clamp(body.linearVelocityY, -MAX_Y_VEL, MAX_Y_VEL)
            body.update(delta)
        }
    }

    private fun Container.paintGrapplingHook() {
        var background = Container()
        parent?.addChild(background)
        addUpdater {
            background.removeFromParent()
            if (grapple != null) {
                val source = pos + Point(0, -8)
                val dest = grapple!!.center()
                background = Container()
                parent?.addChildAt(background, parent?.getChildIndex(this@Player) ?: 0)

                background.graphics {
                    stroke(Colors.GREEN, StrokeInfo(thickness = 1.0)) {
                        line(source, dest)
                    }
                }

            }
        }
    }

    private fun jump() {
        if (jumpHeld) return
        when {
            grounded -> {
                body.linearVelocityY = -JUMP_VELOCITY
                setPlayerState(PlayerState.JUMPING)
            }

            touchingWallRight -> {
                body.linearVelocityX = -WALL_JUMP_KICKOFF_VELOCITY
                body.linearVelocityY = -WALL_JUMP_KICKOFF_VELOCITY_Y
                setPlayerState(PlayerState.JUMPING)
            }

            touchingWallLeft -> {
                body.linearVelocityX = WALL_JUMP_KICKOFF_VELOCITY
                body.linearVelocityY = -WALL_JUMP_KICKOFF_VELOCITY_Y
                setPlayerState(PlayerState.JUMPING)
            }

            hasDoubleJump -> {
                hasDoubleJump = false
                body.linearVelocityY = -JUMP_VELOCITY
                setPlayerState(PlayerState.JUMPING)
            }
        }
    }

    private fun dash(right: Boolean = true) {
        if (state == PlayerState.DASHING || !hasDash) return
        hasDash = false
        if (right) {
            goingRight = true
            body.linearVelocityX = DASH_VELOCITY
        } else {
            goingRight = false
            body.linearVelocityX = -DASH_VELOCITY
        }
        animator.setFacing(right)
        setPlayerState(PlayerState.DASHING)
    }

    private fun setPlayerState(state: PlayerState) {
        if (this.state != state) {
//            val right = if (goingRight) "right" else "left"
//            println("${this.state} -> $state $right")
            animator.evaluate(state)
            this.state = state
            this.stateTime = 0.0
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