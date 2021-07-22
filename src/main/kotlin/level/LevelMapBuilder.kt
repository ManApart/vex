package level

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

private const val levelDir = "/data/levels/"

class LevelMapBuilder {
    fun createMap(template: LevelTemplate): LevelMap {
        return LevelMap(loadBinary(levelDir + template.fileName), template.id)
    }

    private fun loadBinary(fileName: String): Array<Array<Tile>> {
        val image: BufferedImage = ImageIO.read(this::class.java.getResourceAsStream(fileName))

        val map = Array(image.width) { Array(image.height) { DEFAULT_TILE } }
        for (x in 0 until image.width) {
            val maxY = map[x].size
            for (y in 0 until image.height) {
//                val type = fromInt(image.getRGB(x, y))
                val tileY = maxY - 1 - y
                map[x][tileY] = buildTile(x, tileY, image.getRGB(x, y))
            }
        }
        return map
    }

    private fun buildTile(x: Int, y: Int, color: Int) :Tile {
        val red: Int = color and 0x00ff0000 shr 16
        val green: Int = color and 0x0000ff00 shr 8
        val blue: Int = color and 0x000000ff
        val type = fromInt(red)

        return Tile(type, x, y, blue)
    }

}