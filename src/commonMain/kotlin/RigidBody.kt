import com.soywiz.korge.view.Container
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.distanceTo
import ui.Trigger
import ui.level.GrapplingHook
import ui.level.TILE_SIZE
import kotlin.math.roundToInt

class RigidBody(private val parent: Container) {
    var linearVelocityX = 0f
    var linearVelocityY = 0f
    private var collidedRight: Trigger? = null
    private var collidedLeft: Trigger? = null
    private var collidedUp: Trigger? = null
    private var collidedDown: Trigger? = null


    fun update(deltaTime: Float, hook: GrapplingHook? = null) {
        val distance = hook?.let { h -> parent.distanceTo(h.pos) } ?: 0.0
        if (hook != null && distance >= hook.slackLength){
            update(deltaTime, hook, distance)
        } else {
            update(deltaTime)
        }
    }

    private fun update(deltaTime: Float, hook: GrapplingHook, distance: Double) {
        //TODO
        //Invert tan if swinging opposite direction
        //Apply gravity if not close to bottom

        val angleHookToPlayer = 1
        val angleHookTan = 1

        var x = linearVelocityX * deltaTime
        var y = linearVelocityY * deltaTime
        val parentMag = 1
        //multiply by magnitu
        //x = parentMag * angleHookTan

        if (collidedRight?.isCollided == true && x > 0) x = 0f
        if (collidedLeft?.isCollided == true && x < 0) x = 0f
        if (collidedUp?.isCollided == true && y > 0) y = 0f
        if (collidedDown?.isCollided == true && y < 0) y = 0f

        if (collidedDown?.isCollided == true && linearVelocityY < 0) {
            parent.y = (parent.y / TILE_SIZE).roundToInt().toDouble() * TILE_SIZE- (TILE_SIZE/4)
        }
        parent.xy(parent.x + x, parent.y - y)
        val newDistance = parent.distanceTo(hook.pos)
        if (newDistance > hook.slackLength){
            val newAngleHookToPlayer = 1
//            x = sinOrSomthing(newAngle)
//            y = sinOrSomthing(newAngle)
//            parent.xy(x, y)
        }
    }


    fun update(deltaTime: Float) {
        var x = linearVelocityX * deltaTime
        var y = linearVelocityY * deltaTime
        if (collidedRight?.isCollided == true && x > 0) x = 0f
        if (collidedLeft?.isCollided == true && x < 0) x = 0f
        if (collidedUp?.isCollided == true && y > 0) y = 0f
        if (collidedDown?.isCollided == true && y < 0) y = 0f

        if (collidedDown?.isCollided == true && linearVelocityY < 0) {
            parent.y = (parent.y / TILE_SIZE).roundToInt().toDouble() * TILE_SIZE- (TILE_SIZE/4)
        }
TILE_SIZE
        parent.xy(parent.x + x, parent.y - y)
    }

    fun addCollision(bounds: Rectangle) {
        val halfWidth = bounds.width / 2
        val floorHeight = bounds.width / 2
        val floorOffset = bounds.width / 4
        val wallHeight = bounds.height * .8
        val wallOffset = bounds.height * .1
        val display = false

        val rightWall = Rectangle(bounds.x + halfWidth, bounds.y + wallOffset, halfWidth, wallHeight)
        collidedRight = Trigger(parent, rightWall, display = display, color = Colors.RED)

        val leftWall = Rectangle(bounds.x, bounds.y + wallOffset, halfWidth, wallHeight)
        collidedLeft = Trigger(parent, leftWall, display = display, color = Colors.YELLOW)

        val highFloor = Rectangle(bounds.x + floorOffset, bounds.y, halfWidth, floorHeight)
        collidedUp = Trigger(parent, highFloor, display = display, color = Colors.AQUA)

        val lowFloor = Rectangle(bounds.x + floorOffset, bounds.y + bounds.height - floorHeight, halfWidth, floorHeight)
        collidedDown = Trigger(parent, lowFloor, display = display, color = Colors.PERU)

    }
}