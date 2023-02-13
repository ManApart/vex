package ui.level

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Angle
import toPoint
import toVector

private const val magnitude = 10.0f

class GrapplingHook(private val player: Player, angle: Angle) : Container() {
    private var collided = false
    private val rect: SolidRect

    init {
        player.parent?.addChild(this)
        rect = solidRect(TILE_SIZE, TILE_SIZE, Colors.RED)
        centerOn(player)

        val initialVelocity = angle.toPoint().toVector()
//        println("Angle: ${angle.degrees}, Velocity: $initialVelocity")

        addUpdater {
            if (!collided) {
                position(pos.x + initialVelocity.x * magnitude, pos.y - initialVelocity.y * magnitude)
            }
        }

//        onCollision({
//            it is SolidRect && it.body != null && it !is Player
//        }) {collidedObject ->
//            collided = true
//            rect.color = Colors.GREEN
////            registerBodyWithFixture(type = BodyType.STATIC)
////            val gb = collidedObject.body!!
////            val pb = player.body!!
////            val jointDef = RopeJointDef().apply {
////                bodyA = gb
////                bodyB = pb
////                maxLength = 5f
////            }
////
////            joint = gb.world.createJoint(jointDef)
//        }
    }

    fun release() {
        removeFromParent()
//        player.body.m_jointList = null
//        joint?.let { joint -> body!!.world.destroyJoint(joint) }
//        body!!.world.destroyBody(body!!)
    }

}