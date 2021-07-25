package ui

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import level.LevelMap
import level.TileType
import ui.level.TILE_SIZE

class Trigger(
    private val map: LevelMap,
    private val onContactStart: () -> Unit = {},
    private val onContactEnd: () -> Unit = {},
    private val display: Boolean = false
) : Container() {
    private val contactedViews = mutableListOf<View>()

    fun init(parent: Container) {
        parent.addChild(this)
        solidRect(1.1 * TILE_SIZE, .3 * TILE_SIZE, Colors.GREEN) {
            xy(-1.1 * TILE_SIZE / 2, TILE_SIZE / 2.0)
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