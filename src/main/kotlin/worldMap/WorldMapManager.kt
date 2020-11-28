package worldMap

import GameMode

class WorldMapManager : GameMode {
    val worldMap = WorldMapBuilder.world1
    val renderer = WorldMapRenderer(worldMap)

    override fun init() {
        initializeRender()
    }

    override fun processInput(deltaTime: Float) {
    }

    override fun render() {
        renderer.render()
    }

    override fun afterRender(deltaTime: Float) {
    }
}