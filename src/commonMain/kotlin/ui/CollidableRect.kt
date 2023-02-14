package ui

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.SolidRect
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

fun Container.collidableRect(width: Int, height: Int, customizer: SolidRect.() -> Unit = {}): CollidableRect {
    return CollidableRect(width.toDouble(), height.toDouble(), customizer = customizer).also {
        addChild(it)
    }
}

fun Container.collidableRect(width: Double, height: Double, color: RGBA = Colors.WHITE, customizer: SolidRect.() -> Unit = {}): CollidableRect {
    return CollidableRect(width, height, color, customizer).also {
        addChild(it)
    }
}

class CollidableRect(width: Double, height: Double, color: RGBA = Colors.WHITE, customizer: SolidRect.() -> Unit = {}) : Container() {
    val rect = solidRect(width, height, color).apply(customizer)
    var color
        get() = rect.color;
        set(value) {
            rect.color = value
        }
}