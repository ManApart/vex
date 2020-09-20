import player.Player
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class LevelMap(private val tiles: Array<Array<Tile>>) {

    fun getTile(x: Float, y: Float): Tile {
        return getTile(x.toInt(), y.toInt())
    }

    fun getTile(x: Int, y: Int): Tile {
        if (x < 0 || x >= tiles.size || y < 0 || y >= tiles[x].size) {
            return DEFAULT_TILE
        }
        return tiles[x][y]
    }

    fun getSize(): Int {
        return tiles.size
    }

    fun spawnPlayer(player: Player) {
        val spawnTile = tiles.flatten().firstOrNull { it.type == TileType.SPAWN }
        if (spawnTile != null) {
            player.body.bounds.x = spawnTile.x.toFloat()
            player.body.bounds.y = spawnTile.y.toFloat()
        }
    }
}