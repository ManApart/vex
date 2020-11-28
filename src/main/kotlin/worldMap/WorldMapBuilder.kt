package worldMap

import level.Levels

object WorldMapBuilder {
    val world1 = WorldMap(
            mapOf(
                    0 to Levels.testLevel,
                    1 to Levels.testLevel2
            ),
            listOf(
                    Connection(0, 255, 1,0)
            )
    )
}