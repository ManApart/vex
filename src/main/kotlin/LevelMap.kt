import player.Player
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

const val TILE = -1
const val SPACE = -16777216
const val SPAWN = -10252883

class LevelMap {
    private val fileName = "/data/test-level.png"
    private val tiles = loadBinary(fileName)

    private fun loadBinary(fileName: String): Array<Array<Int>> {
        val image: BufferedImage = ImageIO.read(this::class.java.getResourceAsStream(fileName))

        val map = Array(image.width) { Array(image.height) { 0 } }
        for (x in 0 until image.width) {
            val maxY = map[x].size
            for (y in 0 until image.height) {
                map[x][maxY - 1 - y] = image.getRGB(x, y)
            }
        }
        return map
    }

    fun getTile(x: Float, y: Float): Int {
        return getTile(x.toInt(), y.toInt())
    }

    fun getTile(x: Int, y: Int): Int {
        if (x < 0 || x >= tiles.size || y < 0 || y >= tiles[x].size) {
            return 0
        }
        return tiles[x][y]
    }

    fun getSize(): Int {
        return tiles.size
    }

    fun spawnPlayer(player: Player) {
        for (x in tiles.indices) {
            for (y in tiles[x].indices) {
                if (tiles[x][y] == SPAWN) {
                    player.body.bounds.x = x.toFloat()
                    player.body.bounds.y = y.toFloat()
                }
            }
        }
    }
}