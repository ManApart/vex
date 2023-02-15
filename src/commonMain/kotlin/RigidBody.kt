import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Rectangle
import ui.Trigger

class RigidBody(private val parent: View) {
    var linearVelocityX = 0f
    var linearVelocityY = 0f
    private var collidedRight: Trigger? = null
    private var collidedLeft: Trigger? = null
    private var collidedUp: Trigger? = null
    private var collidedDown: Trigger? = null
    private var source: Container? = null

    fun update(deltaTime: Float) {
        if (collidedRight?.isCollided == true && linearVelocityX > 0) linearVelocityX = 0f
        if (collidedLeft?.isCollided == true && linearVelocityX < 0) linearVelocityX = 0f
        if (collidedUp?.isCollided == true && linearVelocityY > 0) linearVelocityY = 0f
        if (collidedDown?.isCollided == true && linearVelocityY < 0) linearVelocityY = 0f
        parent.xy(parent.x + linearVelocityX * deltaTime, parent.y + -linearVelocityY * deltaTime)
    }

    fun addCollision(source: Container, bounds: Rectangle) {
        this.source = source
        val halfWidth = bounds.width / 2
        val floorHeight = bounds.width / 2
        val floorOffset = bounds.width / 4
        val wallHeight = bounds.height * .8
        val wallOffset = bounds.height * .1
        val display = true

        val rightWall = Rectangle(bounds.x + halfWidth, bounds.y + wallOffset, halfWidth, wallHeight)
        collidedRight = Trigger(source, rightWall, display = display, color = Colors.RED)

        val leftWall = Rectangle(bounds.x, bounds.y + wallOffset, halfWidth, wallHeight)
        collidedLeft = Trigger(source, leftWall, display = display, color = Colors.YELLOW)

        val highFloor = Rectangle(bounds.x + floorOffset, bounds.y, halfWidth, floorHeight)
        collidedUp = Trigger(source, highFloor, display = display, color = Colors.AQUA)

        val lowFloor = Rectangle(bounds.x + floorOffset, bounds.y + bounds.height - floorHeight, halfWidth, floorHeight)
        collidedDown = Trigger(source, lowFloor, display = display, color = Colors.PERU)

    }
}