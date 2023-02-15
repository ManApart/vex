import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Rectangle
import ui.Trigger

class RigidBody(private val parent: View) {
    var linearVelocityX = 0f
    var linearVelocityY = 0f
    private var collidedRight = false
    private var collidedLeft = false
    private var collidedUp = false
    private var collidedDown = false
    private var source: Container? = null

    fun update(deltaTime: Float) {
        if (collidedRight && linearVelocityX > 0) linearVelocityX = 0f
        if (collidedLeft && linearVelocityX < 0) linearVelocityX = 0f
        if (collidedUp && linearVelocityY > 0) linearVelocityY = 0f
        if (collidedDown && linearVelocityY < 0) linearVelocityY = 0f
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
        Trigger(source, rightWall, { collidedRight = true }, { collidedRight = false }, display, Colors.RED)

        val leftWall = Rectangle(bounds.x, bounds.y + wallOffset, halfWidth, wallHeight)
        Trigger(source, leftWall, { collidedLeft = true }, { collidedLeft = false }, display, Colors.YELLOW)

        val highFloor = Rectangle(bounds.x + floorOffset, bounds.y, halfWidth, floorHeight)
        Trigger(source, highFloor, { collidedUp = true }, { collidedUp = false }, display, Colors.AQUA)

        val lowFloor = Rectangle(bounds.x + floorOffset, bounds.y + bounds.height - floorHeight, halfWidth, floorHeight)
        Trigger(source, lowFloor, { collidedDown = true }, { collidedDown = false }, display, Colors.PERU)

    }
}