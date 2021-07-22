package worldMap

import level.Levels
import physics.Vector

object WorldMapBuilder {
    val world1 = buildWorld()

    private fun buildWorld(): WorldMap {
        val levels = listOf(
                Exit(Levels.levels[0]!!, 0, Vector(0,0)),
                Exit(Levels.levels[0]!!, 100, Vector(1,0)),
                Exit(Levels.levels[0]!!, 255, Vector(0,1)),
                Exit(Levels.levels[1]!!, 0, Vector(10,0)),
                Exit(Levels.levels[2]!!, 0, Vector(0,10))
        )

        val connections = listOf(
                Connection(0, 100, 1,0, levels),
                Connection(0, 255, 2,0, levels)
        )

        return WorldMap(levels, connections)
    }

}