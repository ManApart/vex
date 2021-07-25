package ui

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.shape.Shape2d
import level.LevelMap
import level.TileType
import physics.Rectangle
import ui.level.TILE_SIZE

class Trigger(
    parent: Container,
    rect: Rectangle,
    private val map: LevelMap,
    private val onContactStart: () -> Unit = {},
    private val onContactEnd: () -> Unit = {},
    display: Boolean = false,
    color: RGBA = Colors.GREEN
) : Container() {
    private val contactedViews = mutableListOf<View>()

    init {
        parent.addChild(this)
        solidRect(rect.width.toDouble(), rect.height.toDouble(), color) {
            xy(rect.x, rect.y)
            if (!display) alpha = 0.0
        }


        onCollision(filter = {
            val tile = map.getTile((it.pos.x / TILE_SIZE).toInt(), (it.pos.y / TILE_SIZE).toInt())
            it is SolidRect
                    && (tile.x != 0 && tile.y != 0)
                    && !contactedViews.contains(it)
                    && tile.type != TileType.SPACE
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