package ui.worldMap

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import worldMap.Exit
import worldMap.WorldMapManager
import kotlin.properties.Delegates

class WorldMapScene(private val spawnExitId: Int = 0) : Scene() {
    var player: Player by Delegates.notNull()

    override suspend fun Container.sceneInit() {

//        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
        paint(WorldMapManager.worldMap)
        player = Player(WorldMapManager.start).also { addChild(it) }
//        }.follow(player, true)

    }


    fun enterLevel(exit: Exit) {

    }

    fun exitLevel(levelId: Int, exitId: Int) {
//        val exit = worldManager.worldMap.exits.first { it.level.id == levelId && it.exitId == exitId }
//        worldManager.worldMap.unlockNeighbors(exit)
//        worldManager.player.setPosition(exit)
//        this.gameMode = worldManager
    }

}