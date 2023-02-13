import com.soywiz.korge.view.View
import com.soywiz.korge.view.xy

class RigidBody(private val parent: View) {
    var linearVelocityX = 0f
    var linearVelocityY = 0f

    fun update(deltaTime: Float) {
        parent.xy(parent.x + linearVelocityX * deltaTime, parent.y + linearVelocityY * deltaTime)
    }
}