package ui.level

import com.soywiz.korge.box2d.body
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.view.*
import com.soywiz.korgw.sdl2.SDLKeyCode
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Angle
import div
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
        }) {collidedObject ->
            collided = true
            rect.color = Colors.GREEN
            registerBodyWithFixture(type = BodyType.STATIC)
            val gb = collidedObject.body!!
            val pb = player.body!!
            val jointDef = DistanceJointDef()
            jointDef.initialize(gb, pb, gb.position, pb.position)

            joint = gb.world.createJoint(jointDef)
        }
    }

    fun release() {
        removeFromParent()
        player.body!!.m_jointList = null
        joint?.let { joint -> body!!.world.destroyJoint(joint) }
        body!!.world.destroyBody(body!!)
    }

}