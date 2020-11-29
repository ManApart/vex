package level

import GameMode
import input.Controller
import input.ControllerDebugger
import player.Player
import worldMap.initializeRender

class LevelManager(val level: LevelTemplate = Levels.levels[0]!!, exitId: Int = 0) : GameMode {
    val map = LevelMapBuilder().createMap(level.fileName)
    val player = Player(map)

    init {
        map.spawnPlayer(player, exitId)
    }

    private val mapRenderer = LevelMapRenderer(map, player)


    override fun init(){
        initializeRender()
    }

    override fun processInput(deltaTime: Float){
        Controller.update(deltaTime)
        ControllerDebugger.update()
    }

    override fun render(){
        mapRenderer.render()
    }

    override fun afterRender(deltaTime: Float){
        player.update(deltaTime)
    }

}