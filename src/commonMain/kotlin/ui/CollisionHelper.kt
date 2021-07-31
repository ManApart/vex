package ui

import com.soywiz.korge.box2d.body
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
        solidRect(rect.width, rect.height, color) {
            xy(rect.x, rect.y)
            if (!display) alpha = 0.0
        }

        onCollision(filter = {
            it is SolidRect
                    && !contactedViews.contains(it)
                    && it.body != null
        }) { other ->
            contactedViews.add(other)
            onContactStart()
        }
        onCollisionExit {
            contactedViews.remove(it)
            if (contactedViews.isEmpty()) onContactEnd()
        }
    }

}