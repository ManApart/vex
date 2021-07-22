package level

import Color
import player.PlayerOld


class LevelMapRenderer(private val map: LevelMap, private val player: PlayerOld) {
    private val tileSize = 5f

    private val tileColor = Color(1f, 1f, 1f)
    private val spawnColor = Color(0f, 1f, 0f)
    private val playerColor = Color(1f, 0f, 0f)

    fun render() {
        val offsetX = -player.body.bounds.x * tileSize + 70
        val offsetY = -player.body.bounds.y * tileSize + 230
//        drawBackground()
        drawTiles(offsetX, offsetY)
//        drawRectangle((player.body.bounds * tileSize) + Vector(offsetX, offsetY), playerColor)

    }

    private fun drawTiles(offsetX: Float, offsetY: Float) {
        for (x in 0 until map.size) {
            for (y in 0 until map.size) {
                when (map.getTile(x, y).type) {
//                    TileType.TILE -> drawRectangle(Rectangle(x * tileSize + offsetX, offsetY + y * tileSize, tileSize, tileSize), tileColor)
//                    TileType.EXIT -> drawRectangle(Rectangle(x * tileSize + offsetX, offsetY + y * tileSize, tileSize, tileSize), spawnColor)
                    else -> {
                    }
                }
            }
        }
    }


}