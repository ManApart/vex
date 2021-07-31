package worldMap

import Vec2
import level.levels

object WorldMapBuilder {
    val world1 = buildWorld()

    private fun buildWorld(): WorldMap {
        val levels = listOf(
                Exit(0, levels[0]!!, Vec2(0,0)),
                Exit(1, levels[0]!!, Vec2(2,0)),
                Exit(2, levels[0]!!, Vec2(0,2)),
                Exit(0, levels[1]!!, Vec2(10,3)),
                Exit(0, levels[2]!!, Vec2(3,10))
        )

        val connections = listOf(
                Connection(0, 1, 1,0, levels),
                Connection(0, 2, 2,0, levels)
        )

        return WorldMap(levels, connections)
    }

}