package ui.level

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.camera.cameraContainer
import level.LevelMap
import level.LevelMapBuilder
import level.LevelTemplate
import ui.Player
import ui.VIRTUAL_SIZE
import kotlin.properties.Delegates

class LevelScene(private val template: LevelTemplate, private val spawnExitId: Int = 0) : Scene() {
    var map: LevelMap by Delegates.notNull()
    var player: Player by Delegates.notNull()

    override suspend fun Container.sceneInit() {
        map = LevelMapBuilder().createMap(template)
        player = Player(map)

        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
            paint(map).addChild(player)
            player.init(map.getSpawnTile(spawnExitId)!!)
        }.follow(player, true)

    }


//    fun enterLevel(exit: Exit){
//        this.gameMode = LevelManager(exit.level, exit.exitId)
//    }

    fun exitLevel(levelId: Int, exitId: Int) {
//        val exit = worldManager.worldMap.exits.first { it.level.id == levelId && it.exitId == exitId }
//        worldManager.worldMap.unlockNeighbors(exit)
//        worldManager.player.setPosition(exit)
//        this.gameMode = worldManager
    }

}