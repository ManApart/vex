import physics.Vector

val DEFAULT_TILE = Tile(TileType.SPACE, 0, 0)

data class Tile(val type: TileType = TileType.SPACE, val x: Int = 0, val y: Int = 0) {
    val vector by lazy { Vector(x, y) }
}