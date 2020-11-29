package worldMap

import GameMode
import input.Controller
import input.ControllerDebugger
import player.WorldMapPlayer

class WorldMapManager : GameMode {
    val worldMap = WorldMapBuilder.world1
    val renderer = WorldMapRenderer(worldMap)
    val player = WorldMapPlayer(worldMap.levels.values.first())

    override fun init() {
        initializeRender()
    }

    override fun processInput(deltaTime: Float) {
        Controller.update(deltaTime)
        ControllerDebugger.update()
    }

    override fun render() {
        renderer.render(player)
    }

    override fun afterRender(deltaTime: Float) {
        player.update(deltaTime)
    }
}