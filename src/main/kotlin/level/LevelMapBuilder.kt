package level

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

private const val fileName = "/data/test-level.png"

class LevelMapBuilder {
    fun createMap(): LevelMap {
        return LevelMap(loadBinary(fileName))
    }

    private fun loadBinary(fileName: String): Array<Array<Tile>> {
        val image: BufferedImage = ImageIO.read(this::class.java.getResourceAsStream(fileName))

        val map = Array(image.width) { Array(image.height) { DEFAULT_TILE } }
        for (x in 0 until image.width) {
            val maxY = map[x].size
            for (y in 0 until image.height) {
                val type = fromInt(image.getRGB(x, y))
                val tileY = maxY - 1 - y
                map[x][tileY] = Tile(type, x, tileY)
            }
        }
        return map
    }
}