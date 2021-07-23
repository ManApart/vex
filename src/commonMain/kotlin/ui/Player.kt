package ui

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
import level.LevelMap
import level.Tile
import ui.level.TILE_SIZE

class Player(private val map: LevelMap) : Container() {
    fun init(spawnTile: Tile) {
        solidRect(TILE_SIZE, 2* TILE_SIZE, Colors.PINK)
        position(spawnTile.x * TILE_SIZE, spawnTile.y * TILE_SIZE)
//            registerBodyWithFixture(type = BodyType.DYNAMIC, density = 2, friction = 0.01)
    }

}