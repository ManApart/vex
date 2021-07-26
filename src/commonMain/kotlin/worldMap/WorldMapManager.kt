package worldMap

object WorldMapManager {
    val worldMap = WorldMapBuilder.world1
    val start = worldMap.exits.first().also { it.unlocked = true }


}