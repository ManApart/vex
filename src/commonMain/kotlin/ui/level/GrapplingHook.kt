package ui.level

import com.soywiz.korge.box2d.BoxShape
import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.degrees
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.dynamics.BodyType
import toPoint
import toVector

private const val magnitude = 10.0f

class GrapplingHook(private val player: Player, angle: Angle) : Container() {
    private var collided = false
    private val rect: SolidRect

    init {
        println("Angle: ${angle.degrees}")
        player.parent?.addChild(this)
        rect = solidRect(TILE_SIZE, TILE_SIZE, Colors.RED)
        centerOn(player)

        val initialVelocity = angle.toPoint().toVector()
        println("Velocity: $initialVelocity")

        addUpdater {
            if (!collided) {
                position(pos.x + initialVelocity.x * magnitude, pos.y - initialVelocity.y * magnitude)
            }
        }

        onCollision({
            it is SolidRect && it.body != null && it !is Player
        }) {
            collided = true
            rect.color = Colors.GREEN
        }
    }


}