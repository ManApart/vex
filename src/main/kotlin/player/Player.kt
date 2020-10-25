package player

import LevelMap
import input.Controller
import physics.*
import kotlin.math.abs

const val MAX_X_VEL = 6f
const val MAX_X_AIR_VEL = 6f
const val MAX_Y_VEL = 10f
const val GRAVITY = 20.0f
private const val ACCELERATION = 20f

private const val JUMP_VELOCITY = 10f
private const val WALL_JUMP_KICKOFF_VELOCITY = 5f
private const val WALL_JUMP_KICKOFF_VELOCITY_Y = 6f
private const val JUMP_TIME = .1f
private const val WALL_JUMP_TIME = .1f

private const val DASH_VELOCITY = 20f
private const val DASH_TIME = .15f


class Player(map: LevelMap) : RigidBodyOwner {

    var state = PlayerState.FALLING
    var stateTime = 0f
    var dir = Direction.LEFT
    private var hasDoubleJump = false
    val body = RigidBody(map, this, 0.6f, 0.8f)


    override fun onCollided(direction: Direction) {
//        println("Collided $direction")
        when (direction) {
            Direction.UP -> {
            }
            Direction.DOWN -> {
                if (state != PlayerState.DASHING) {
                    setPlayerState(PlayerState.RUNING)
                    hasDoubleJump = true
                }
            }
            Direction.LEFT -> {
                if (state == PlayerState.DASHING) {
                    setStoppedState()
                }
            }
            Direction.RIGHT -> {
                if (state == PlayerState.DASHING) {
                    setStoppedState()
                }
            }
        }
    }

    override fun onNoLongerCollided(direction: Direction) {
//        println("No longer Collided $direction")
        if (direction == Direction.DOWN) {
            if (!state.isInState(PlayerState.JUMPING, PlayerState.DASHING)) {
                setPlayerState(PlayerState.FALLING)
            }
        }
    }

    private fun setPlayerState(state: PlayerState) {
//        println("${this.state} -> $state")
        this.state = state
        this.stateTime = 0f
    }

    fun update(deltaTime: Float) {
        processKeys()
        updateState(deltaTime)
        body.update(deltaTime, xMaxVelocity(), yMaxVelocity())

//        println("player.Player is: ${body.bounds.x}, ${body.bounds.y}")
    }

    private fun processKeys() {
        if (Controller.jump.isFirstPressed()) {
            if (state != PlayerState.JUMPING && body.isCollidedAny(Direction.DOWN, Direction.LEFT, Direction.RIGHT)) {
                if (isGrounded()) {
                    setPlayerState(PlayerState.JUMPING)
                } else {
                    if (body.isCollided(Direction.RIGHT)) {
                        body.velocity.x = -WALL_JUMP_KICKOFF_VELOCITY
                        setPlayerState(PlayerState.WALL_JUMPING)
                    } else if (body.isCollided(Direction.LEFT)) {
                        body.velocity.x = WALL_JUMP_KICKOFF_VELOCITY
                        setPlayerState(PlayerState.WALL_JUMPING)
                    }
                }
            } else if (hasDoubleJump) {
                hasDoubleJump = false
                setPlayerState(PlayerState.JUMPING)
            }
        }

        if (Controller.dashLeft.isFirstPressed()) {
            if (state != PlayerState.DASHING) {
                dir = Direction.LEFT
                setPlayerState(PlayerState.DASHING)
            }
        }

        if (Controller.dashRight.isFirstPressed()) {
            if (state != PlayerState.DASHING) {
                dir = Direction.RIGHT
                setPlayerState(PlayerState.DASHING)
            }
        }

        if (abs(Controller.xAxis.value) > 0 && state != PlayerState.WALL_JUMPING && state != PlayerState.DASHING) {
            if (isGrounded() && state == PlayerState.IDLE) {
                setPlayerState(PlayerState.RUNING)
            }
            dir = Direction.fromNumber(Controller.xAxis.value)
            body.acceleration.x = ACCELERATION * Controller.xAxis.value
        } else if (state == PlayerState.RUNING) {
            if (isGrounded()) {
                setPlayerState(PlayerState.IDLE)
            }
        }

        if (abs(Controller.yAxis.value) > 0) {
//            body.accel.y = player.ACCELERATION * Controller.yAxis.value
        }
    }

    private fun updateState(deltaTime: Float) {
        stateTime += deltaTime

        if (state.isInState(PlayerState.JUMPING)) {
            if (stateTime < JUMP_TIME) {
                body.velocity.y = JUMP_VELOCITY
            } else {
                setPlayerState(PlayerState.FALLING)
            }
        }
        if (state.isInState(PlayerState.WALL_JUMPING)) {
            if (stateTime < JUMP_TIME) {
                body.velocity.y = WALL_JUMP_KICKOFF_VELOCITY_Y
            } else {
                setPlayerState(PlayerState.FALLING)
            }
        }
        if (state == PlayerState.WALL_JUMPING) {
            if (stateTime >= WALL_JUMP_TIME) {
                setPlayerState(PlayerState.FALLING)
            } else {
                body.velocity.x = WALL_JUMP_KICKOFF_VELOCITY
            }
        }

        if (state == PlayerState.DASHING) {
            if (stateTime < DASH_TIME) {
                body.velocity.y = 0f
                body.velocity.x = dir.vector.x * DASH_VELOCITY
            } else {
                setStoppedState()
            }
        }

        if (state.isInState(PlayerState.FALLING, PlayerState.RUNING, PlayerState.IDLE)) {
            body.acceleration.y = -GRAVITY
        }

        if (state == PlayerState.IDLE) {
            body.acceleration.x = 0f
        }
    }

    private fun isGrounded() = body.isCollided(Direction.DOWN)

    private fun xMaxVelocity(): Float {
        return when {
            state.isInState(PlayerState.DASHING) -> {
                DASH_VELOCITY
            }
            state.isInState(PlayerState.JUMPING, PlayerState.FALLING) -> {
                MAX_X_AIR_VEL
            }
            state.isInState(PlayerState.WALL_JUMPING) -> {
                WALL_JUMP_KICKOFF_VELOCITY
            }
            else -> {
                MAX_X_VEL
            }
        }
    }

    private fun yMaxVelocity(): Float {
        return if (state.isInState(PlayerState.JUMPING, PlayerState.WALL_JUMPING)) {
            JUMP_VELOCITY
        } else {
            MAX_Y_VEL
        }
    }

    private fun setStoppedState() {
        if (isGrounded()) {
            setPlayerState(PlayerState.IDLE)
        } else {
            setPlayerState(PlayerState.FALLING)
        }
    }

}