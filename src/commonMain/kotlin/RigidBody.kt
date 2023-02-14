import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Rectangle
import ui.Trigger

class RigidBody(private val parent: View) {
    private val contactedViews = mutableListOf<View>()
    var linearVelocityX = 0f
    var linearVelocityY = 0f
    private var collidedRight = false
    private var collidedLeft = false
    private var collidedUp = false
    private var collidedDown = false

    fun update(deltaTime: Float) {
        if (collidedRight && linearVelocityX > 0) linearVelocityX = 0f
        if (collidedLeft && linearVelocityX < 0) linearVelocityX = 0f
        parent.xy(parent.x + linearVelocityX * deltaTime, parent.y + -linearVelocityY * deltaTime)
    }

    fun addCollision(source: Container, bounds: Rectangle) {

        val rightWall = Rectangle(bounds.x + bounds.width / 2, bounds.y - bounds.y * .2, bounds.width / 2, bounds.height * .8)
        Trigger(source, rightWall, { collidedRight = true }, { collidedRight = false }, true, Colors.RED)

        val leftWall = Rectangle(bounds.x, bounds.y - bounds.y * .2, bounds.width / 2, bounds.height * .8)
        Trigger(source, leftWall, { collidedLeft = true }, { collidedLeft = false }, true, Colors.YELLOW)

    }
}