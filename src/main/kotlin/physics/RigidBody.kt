package physics

import LevelMap
import Tile
import clamp
import player.MAX_X_VEL
import player.MAX_Y_VEL

const val DAMP = 0.90f

class RigidBody(private val map: LevelMap, private val owner: RigidBodyOwner, width: Float, height: Float) {
    val acceleration = Vector()
    val velocity = Vector()
    val bounds = Rectangle(0f, 0f, width, height)
    private val collided = createCollidedMap()

    private fun createCollidedMap(): MutableMap<Direction, Boolean> {
        val map = mutableMapOf<Direction, Boolean>()
        Direction.values().forEach { map[it] = false }
        return map
    }

    fun isCollidedAny(vararg directions: Direction): Boolean {
        return directions.any { isCollided(it) }
    }

    fun isCollided(direction: Direction): Boolean {
        return collided[direction]!!
    }

    private fun setNowCollided(direction: Direction) {
        val wasCollided = isCollided(direction)
        collided[direction] = true
        if (!wasCollided) {
            owner.onCollided(direction)
        }
    }

    private fun setNoLongerCollided(direction: Direction) {
        val wasCollided = isCollided(direction)
        collided[direction] = false
        if (wasCollided) {
            owner.onNoLongerCollided(direction)
        }
    }

    fun update(deltaTime: Float, maxX: Float = MAX_X_VEL, maxY: Float = MAX_Y_VEL) {
        acceleration.scale(deltaTime)
        velocity.add(acceleration)
        if (acceleration.x == 0f) velocity.x *= DAMP

        velocity.x = clamp(velocity.x, -maxX, maxX)
        velocity.y = clamp(velocity.y, -maxY, maxY)

        velocity.scale(deltaTime)
        tryMove2()
        velocity.scale(1.0f / deltaTime)
    }

    private fun tryMove() {
        moveX()
        moveY()
    }

    private fun moveX() {
        if (collides(bounds, Vector(velocity.x, 0f))) {
            if (velocity.x > 0) {
                val farEdge = (bounds.x + velocity.x + bounds.width).toInt()
                bounds.x = farEdge - bounds.width
                velocity.x = 0f
                setNowCollided(Direction.RIGHT)
                checkDirectionNoLongerCollides(Direction.LEFT)
            } else {
                bounds.x = (bounds.x + velocity.x).toInt() + 1f
                velocity.x = 0f
                setNowCollided(Direction.LEFT)
                checkDirectionNoLongerCollides(Direction.RIGHT)
            }
        } else {
            bounds.x += velocity.x
            checkDirectionNoLongerCollides(Direction.LEFT)
            checkDirectionNoLongerCollides(Direction.RIGHT)
        }
    }

    private fun moveY() {
        if (collides(bounds, Vector(0f, velocity.y))) {
            if (velocity.y > 0) {
                val farEdge = (bounds.y + velocity.y + bounds.height).toInt()
                bounds.y = farEdge - bounds.height
                velocity.y = 0f
                setNowCollided(Direction.UP)
                checkDirectionNoLongerCollides(Direction.DOWN)
            } else {
                bounds.y = (bounds.y + velocity.y).toInt() + 1f
                velocity.y = 0f
                setNowCollided(Direction.DOWN)
                checkDirectionNoLongerCollides(Direction.UP)
            }
        } else {
            bounds.y += velocity.y
            checkDirectionNoLongerCollides(Direction.UP)
            checkDirectionNoLongerCollides(Direction.DOWN)
        }
    }

    private fun tryMove2() {
        val moveAmount = moveAmount()
        val ray = bounds.source().getRayTo(bounds.source() + moveAmount)
        val collidedTile = map.getFirstCollision(ray)
        if (collidedTile == null) {
            bounds.x += velocity.x
            bounds.y += velocity.y
        } else if (velocity.x != 0f && velocity.y != 0f) {
            val xRay = bounds.source().getRayTo(bounds.source() + Vector(moveAmount.x, 0f))
            val collidedXTile = map.getFirstCollision(xRay)

            if (collidedXTile == null) {
                bounds.x += velocity.x
            } else {
                makeXAdjacentTo(collidedXTile)

                val yRay = bounds.source().getRayTo(bounds.source() + Vector(0f, moveAmount.y))
                val collidedYTile = map.getFirstCollision(yRay)
                if (collidedYTile == null){
                    bounds.y += velocity.y
                } else {
                    makeYAdjacentTo(collidedYTile)
                }
            }
        } else {
            makeXAdjacentTo(collidedTile)
            makeYAdjacentTo(collidedTile)
        }
    }

    private fun moveAmount() : Vector {
        val width = if (velocity.x > 0){
            bounds.width
        } else {
            0f
        }

        val height = if (velocity.y > 0){
            bounds.height
        } else {
            0f
        }

        return velocity + Vector(width, height)
    }

    private fun makeXAdjacentTo(collidedTile: Tile) {
        if (velocity.x != 0f) {
            if (velocity.x > 0f) {
                bounds.x = collidedTile.x - bounds.width
                setNowCollided(Direction.RIGHT)
                checkDirectionNoLongerCollides(Direction.LEFT)
            } else {
                bounds.x = collidedTile.x.toFloat() + 1f
                setNowCollided(Direction.LEFT)
                checkDirectionNoLongerCollides(Direction.RIGHT)
            }
        }
    }

    private fun makeYAdjacentTo(collidedTile: Tile) {
        if (velocity.y != 0f) {
            if (velocity.y > 0f) {
                bounds.y = collidedTile.y - bounds.height
                setNowCollided(Direction.UP)
                checkDirectionNoLongerCollides(Direction.DOWN)
            } else {
                bounds.y = collidedTile.y.toFloat() + 1f
                setNowCollided(Direction.DOWN)
                checkDirectionNoLongerCollides(Direction.UP)
            }
        }
    }


    private fun checkDirectionNoLongerCollides(direction: Direction) {
        if (!collides(direction, bounds, direction.vector * .2f)) {
            setNoLongerCollided(direction)
        }
    }

    private fun collides(current: Rectangle, vel: Vector): Boolean {
        return collidesRight(current, vel) || collidesLeft(current, vel) || collidesUp(current, vel) || collidesDown(current, vel)
    }

    private fun collides(direction: Direction, current: Rectangle, vel: Vector): Boolean {
        return when (direction) {
            Direction.RIGHT -> collidesRight(current, vel)
            Direction.LEFT -> collidesLeft(current, vel)
            Direction.UP -> collidesUp(current, vel)
            Direction.DOWN -> collidesDown(current, vel)
        }
    }

    private fun collidesRight(current: Rectangle, vel: Vector): Boolean {
        val farEdge = current.farX + vel.x - .1f
        val destTile = map.getTile(farEdge, current.y + vel.y)
        return vel.x >= 0 && destTile.type == TileType.TILE
    }

    private fun collidesLeft(current: Rectangle, vel: Vector): Boolean {
        val destTile = map.getTile(current.x + vel.x + .1f, current.y + vel.y)
        return vel.x <= 0 && destTile.type == TileType.TILE
    }

    private fun collidesUp(current: Rectangle, vel: Vector): Boolean {
        val farEdge = current.y + vel.y + current.height - .1f
        val destTile = map.getTile(current.x + vel.x, farEdge)
        return vel.y >= 0 && destTile.type == TileType.TILE
    }

    private fun collidesDown(current: Rectangle, vel: Vector): Boolean {
        val destTile = map.getTile(current.x + vel.x, current.y + vel.y + .1f)
        return vel.y >= 0 && destTile.type == TileType.TILE
    }

}