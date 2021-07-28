package worldMap

import level.levels
import physics.Vector

object WorldMapBuilder {
    val world1 = buildWorld()

    private fun buildWorld(): WorldMap {
        val levels = listOf(
                Exit(levels[0]!!, 0, Vector(0,0)),
                Exit(levels[0]!!, 100, Vector(2,0)),
                Exit(levels[0]!!, 255, Vector(0,2)),
                Exit(levels[1]!!, 0, Vector(10,3)),
                Exit(levels[2]!!, 0, Vector(3,10))
        )

        val connections = listOf(
                Connection(0, 100, 1,0, levels),
                Connection(0, 255, 2,0, levels)
        )

        return WorldMap(levels, connections)
    }

}