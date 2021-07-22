package level

import Color
import physics.Rectangle
import physics.Vector
import player.Player


class LevelMapRenderer(private val map: LevelMap, private val player: Player) {
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
        for (x in 0 until map.getSize()) {
            for (y in 0 until map.getSize()) {
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