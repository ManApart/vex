package level

import GameMode
import input.Controller
import input.ControllerDebugger
import player.Player

class LevelManager : GameMode {
    val map = LevelMapBuilder().createMap()
    val player = Player(map)

    init {
        map.spawnPlayer(player)
    }

    private val mapRenderer = MapRenderer(map, player)


    override fun init(){
        mapRenderer.init()
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