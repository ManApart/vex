package worldMap

import Color
import physics.Rectangle
import physics.Vector

class WorldMapRenderer(private val map: WorldMap) {
    private val levelScale = 5f
    private val levelColor = Color(0f, 1f, 0f)
    private val playerColor = Color(1f, 0f, 0f)
    private val connectionColor = Color(1f, 1f, 1f)
    private val offset = Vector(20, 230)

    fun render() {
//        drawBackground()
        drawConnections()
        drawLevels()
//        drawRectangle((player.body.bounds * tileSize) + Vector(offsetX, offsetY), playerColor)
    }

    private fun drawLevels() {
        map.levels.forEach { level ->
            drawRectangle((level.bounds * levelScale) + offset, levelColor)
        }
    }

    private fun drawConnections() {
        map.connections.forEach { connection ->
            //draw line
        }
    }
}