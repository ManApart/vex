package worldMap

import GameMode
import player.WorldMapPlayer

class WorldMapManager : GameMode {
    val worldMap = WorldMapBuilder.world1
    val renderer = WorldMapRenderer(worldMap)
    val player = WorldMapPlayer(worldMap.levels.values.first())

    override fun init() {
        initializeRender()
    }

    override fun processInput(deltaTime: Float) {
    }

    override fun render() {
        renderer.render(player)
    }

    override fun afterRender(deltaTime: Float) {
    }
}