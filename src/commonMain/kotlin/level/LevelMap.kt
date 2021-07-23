package level

import physics.Vector

/**
 * (0,0) is the bottom left of a map
 * Is this still true?
 */
class LevelMap(val id: Int = 0, private val tiles: Array<Array<Tile>>) {
    val size = tiles.size

    fun getTile(x: Float, y: Float): Tile {
        return getTile(x.toInt(), y.toInt())
    }

    fun getTile(x: Int, y: Int): Tile {
        if (x < 0 || x >= tiles.size || y < 0 || y >= tiles[x].size) {
            return DEFAULT_TILE
        }
        return tiles[x][y]
    }

    fun getSpawnTile(exitId: Int): Tile? {
        return tiles.flatten().firstOrNull { it.type == TileType.EXIT && it.id == exitId }
    }

    fun getFirstCollision(ray: List<Vector>): Tile? {
        val vector = ray.firstOrNull { getTile(it.x, it.y).type == TileType.TILE }
        return if (vector != null) {
            getTile(vector.x, vector.y)
        } else {
            null
        }
    }


}