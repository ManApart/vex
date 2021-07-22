package worldMap

import GameMode
import input.Controller
import input.ControllerDebugger
import player.WorldMapPlayer

class WorldMapManager : GameMode {
    val worldMap = WorldMapBuilder.world1
    val renderer = WorldMapRenderer(worldMap)
    val start = worldMap.exits.first().also { it.unlocked = true }
    val player = WorldMapPlayer(start)

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