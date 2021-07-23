package ui.level

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import level.LevelMap

const val TILE_SIZE = 10

fun Container.paint(map: LevelMap) {
    scale = 4.0
    (0 until map.size).flatMap { x ->
        (0 until map.size).map { y ->
            paint(map, x, y)
        }
    }
}

private fun Container.paint(map: LevelMap, x: Int, y: Int) {
    val tile = map.getTile(x, y)
    solidRect(TILE_SIZE, TILE_SIZE, tile.type.color) {
        position(x * TILE_SIZE, y * TILE_SIZE)
    }
}