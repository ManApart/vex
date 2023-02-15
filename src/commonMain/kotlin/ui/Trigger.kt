package ui

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.Rectangle

class Trigger(
    parent: Container,
    rect: Rectangle,
    private val onContactStart: () -> Unit = {},
    private val onContactEnd: () -> Unit = {},
    display: Boolean = false,
    color: RGBA = Colors.GREEN
) : Container() {
    private val contactedViews = mutableListOf<View>()
    var isCollided = false

    init {
        parent.addChild(this)
        val collisionRect = solidRect(rect.width, rect.height, color) {
            xy(rect.x, rect.y)
            if (!display) alpha = 0.0
        }

        onCollision(filter = {
            it is CollidableRect && !contactedViews.contains(it)
        }) { other ->
            contactedViews.add(other)
            if (display) collisionRect.color = Colors.WHITE
            isCollided = true
            onContactStart()
        }
        onCollisionExit {
            contactedViews.remove(it)
            if (contactedViews.isEmpty()) {
                if (display) collisionRect.color = color
                isCollided = false
                onContactEnd()
            }
        }
    }

}