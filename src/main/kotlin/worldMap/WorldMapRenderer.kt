package worldMap

import Color
import physics.Vector
import player.WorldMapPlayer

class WorldMapRenderer(private val map: WorldMap) {
    private val levelScale = 5f
    private val levelColor = Color(0f, 1f, 0f)
    private val playerColor = Color(1f, 0f, 0f)
    private val connectionColor = Color(1f, 1f, 1f)
    private val levelOffset = Vector(20, 230)
    private val connectionOffset = Vector(20f, 230- levelScale)

    fun render(player: WorldMapPlayer) {
//        drawBackground()
        drawConnections()
        drawLevels()
        drawPlayer(player)
    }

    private fun drawConnections() {
        map.connections.filter { it.unlocked }.forEach { connection ->
            val source = (connection.source.bounds.center() * levelScale) + connectionOffset
            val destination = (connection.destination.bounds.center() * levelScale) + connectionOffset
            drawLine(source, destination, connectionColor)
        }
    }


    private fun drawLevels() {
        map.exits.filter { it.unlocked }.forEach { exit ->
            drawRectangle((exit.bounds * levelScale) + levelOffset, levelColor)
        }
    }

    private fun drawPlayer(player: WorldMapPlayer) {
        drawRectangle((player.bounds * levelScale) + levelOffset, playerColor)
    }

}