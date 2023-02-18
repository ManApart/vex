package ui.level

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.distanceTo
import toPoint
import toVector
import ui.CollidableRect

private const val MAGNITUDE = 10.0f
private const val MAX_REACH = 70.0

class GrapplingHook(private val player: Player, angle: Angle) : Container() {
    private var collided = false
    private val rect: SolidRect
    var slackLength = MAX_REACH

    init {
        player.parent?.addChild(this)
        rect = solidRect(TILE_SIZE, TILE_SIZE, Colors.RED)
        centerOn(player)

        val initialVelocity = angle.toPoint().toVector()
//        println("Angle: ${angle.degrees}, Velocity: $initialVelocity")

        addUpdater {
            if (!collided) {
                if (player.pos.distanceTo(pos) > MAX_REACH) {
                    release()
                } else {
                    position(pos.x + initialVelocity.x * MAGNITUDE, pos.y - initialVelocity.y * MAGNITUDE)
                }
            }
        }

        onCollision({
            it is CollidableRect && it !is Player
        }) { collidedObject ->
            collided = true
            rect.color = Colors.GREEN
        }
    }

    fun release() {
        player.grapple = null
        removeFromParent()
    }

}