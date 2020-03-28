import java.awt.image.BufferedImage
import javax.imageio.ImageIO

const val TILE = -1

class LevelMap {
    private val fileName = "/data/test-level.png"
    private val tiles = loadBinary(fileName)
//    private val tiles = listOf(
//            listOf(0,0,0),
//            listOf(0,0,1),
//            listOf(1,1,1)
//    )

    private fun loadBinary(fileName: String): Array<Array<Int>> {
        val image: BufferedImage = ImageIO.read(this::class.java.getResourceAsStream(fileName))
//        val image: BufferedImage = ImageIO.read(File(fileName))

        val map = Array(image.width) { Array(image.height) { 0 } }
        for (x in 0 until image.width) {
            val maxY = map[x].size
            for (y in 0 until image.height) {
                map[x][y] = image.getRGB(x, y)
//                map[x][ maxY - 1 - y] = image.getRGB(x, y)
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
}