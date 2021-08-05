package ui.level

import center
import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Angle
import com.soywiz.korma.geom.degrees
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.joints.DistanceJointDef
import org.jbox2d.dynamics.joints.Joint
import toPoint
import toVector

private const val magnitude = 10.0f

class GrapplingHook(private val player: Player, angle: Angle) : Container() {
    private var collided = false
    private val rect: SolidRect
    private var joint: Joint? = null

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

        onCollision({
            it is SolidRect && it.body != null && it !is Player
        }) {
            collided = true
            rect.color = Colors.GREEN
            registerBodyWithFixture(type = BodyType.STATIC)
            val jointDef = DistanceJointDef()
            jointDef.initialize(body!!, player.body!!, body!!.position, player.body!!.position)
            jointDef.length = 2f
            joint = body!!.world.createJoint(jointDef)
        }
    }

    fun release() {
        removeFromParent()
        player.body!!.m_jointList = null
        joint?.let { joint -> body!!.world.destroyJoint(joint) }
        body!!.world.destroyBody(body!!)
    }

}