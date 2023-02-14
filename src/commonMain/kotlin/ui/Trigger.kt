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

    init {
        parent.addChild(this)
        val collisionRect = solidRect(rect.width, rect.height, color) {
            xy(rect.x, rect.y)
            if (!display) alpha = 0.0
        }

        onCollision(filter = {
            it is CollidableRect
                    && !contactedViews.contains(it)
//                    && it.body != null
            //TODO - need to detect solid?
        }) { other ->
            contactedViews.add(other)
            collisionRect.color = Colors.WHITE
            onContactStart()
        }
        onCollisionExit {
            contactedViews.remove(it)
            if (contactedViews.isEmpty()) {
                collisionRect.color = color
                onContactEnd()
            }

        }
    }

}