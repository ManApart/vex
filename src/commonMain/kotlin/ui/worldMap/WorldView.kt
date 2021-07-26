package ui.worldMap

import Debug
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.vector.StrokeInfo
import com.soywiz.korma.geom.vector.line
import worldMap.WorldMap

private const val SCALE = 20

fun Container.paint(map: WorldMap): Container {
    return container {
        solidRect(20 * SCALE, 20 * SCALE, Colors.BEIGE)

        map.exits.filter { it.unlocked || Debug.allLevelsUnlocked }.forEach { exit ->
            solidRect(10, 10, Colors.GREEN).xy(exit.bounds.x * SCALE, exit.bounds.y * SCALE)
        }

        map.connections.filter { it.unlocked || Debug.allLevelsUnlocked }.forEach { connection ->
            val source = (connection.source.bounds * SCALE.toFloat()).center().toPoint()
            val destination = (connection.destination.bounds * SCALE.toFloat()).center().toPoint()
            graphics {
                stroke(Colors.GREEN, StrokeInfo(thickness = 2.0)) {
                    line(source, destination)
                }
            }
        }

    }
}