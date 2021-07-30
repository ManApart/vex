package level

import Vec2

val DEFAULT_TILE = Tile(TileType.SPACE, 0, 0)

data class Tile(val type: TileType = TileType.SPACE, val x: Int = 0, val y: Int = 0, val id: Int = 0) {
    val vector by lazy { Vec2(x, y) }
}