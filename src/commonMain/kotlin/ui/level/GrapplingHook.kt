package ui.level

import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.degrees
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.dynamics.BodyType
import toPoint
import toVector

private const val magnitude = 1.0f

class GrapplingHook(private val player: Player, angle: Angle) : Container() {
    init {
        println("Angle: ${angle.degrees}")
        player.parent?.addChild(this)
        solidRect(TILE_SIZE, TILE_SIZE, Colors.RED)
        centerOn(player)
//        xy(player.x, player.y - 40)

        registerBodyWithFixture(
            type = BodyType.DYNAMIC,
            density = 2,
            gravityScale = 0f,
            fixedRotation = true,
            shape = CircleShape(0.225),
            restitution = 0,
            bullet = true
        )
        val initialVelocity = angle.toPoint().toVector()

        addUpdater {
            body!!._linearVelocity.x = initialVelocity.x * magnitude
            body!!._linearVelocity.y = initialVelocity.y * magnitude
        }
    }


}