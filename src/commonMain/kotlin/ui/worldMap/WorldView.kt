package ui.worldMap

import Debug
import center
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.vector.StrokeInfo
import com.soywiz.korma.geom.vector.line
import worldMap.WorldMap

private const val SCALE = 20

fun Container.paint(map: WorldMap): List<MapExit> {
    var exits = listOf<MapExit>()
    container {
        solidRect(20 * SCALE, 20 * SCALE, Colors.BEIGE)

        exits = map.exits.filter { it.unlocked || Debug.allLevelsUnlocked }.map { exit ->
            val rect = solidRect(10, 10, Colors.GREEN).xy(exit.bounds.x * SCALE, exit.bounds.y * SCALE)
            MapExit(exit, rect)
        }

        map.connections.filter { it.unlocked }.forEach { connection ->
            val source = (connection.source.bounds * SCALE.toFloat()).center()
            val destination = (connection.destination.bounds * SCALE.toFloat()).center()
            graphics {
                stroke(Colors.GREEN, StrokeInfo(thickness = 2.0)) {
                    line(source, destination)
                }
            }
        }
    }
    return exits
}