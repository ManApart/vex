package ui.level

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import level.LevelMap
import level.TileType

const val TILE_SIZE = 8

fun Container.paint(map: LevelMap) : Container{
    return container {
        scale = 2.0
        (0 until map.size).flatMap { x ->
            (0 until map.size).map { y ->
                paint(map, x, y)
            }
        }
    }
}

private fun Container.paint(map: LevelMap, x: Int, y: Int) {
    val tile = map.getTile(x, y)
    solidRect(TILE_SIZE, TILE_SIZE, tile.type.color) {
        position(x * TILE_SIZE, y * TILE_SIZE)
        if (tile.type == TileType.TILE) {
            //TODO - collision
        }
    }
}