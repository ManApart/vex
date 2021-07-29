package ui.level

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.scene.AlphaTransition
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.async.launchImmediately
import level.LevelMap
import level.LevelMapBuilder
import level.LevelTemplate
import ui.VIRTUAL_SIZE
import ui.worldMap.WorldMapScene
import worldMap.Exit
import worldMap.WorldMapManager
import kotlin.properties.Delegates

class LevelScene(private val exit: Exit) : Scene() {
    var map: LevelMap by Delegates.notNull()
    var player: Player by Delegates.notNull()

    override suspend fun Container.sceneInit() {
        map = LevelMapBuilder().createMap(exit.level)
        player = Player(map, ::exitLevel)

        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
            paint(map).addChild(player)
            player.init(map.getSpawnTile(exit.id)!!)
        }.follow(player, true)

    }

    private fun exitLevel(levelId: Int, exitId: Int) {
        val exit = WorldMapManager.worldMap.exits.first { it.level.id == levelId && it.id == exitId }
        WorldMapManager.worldMap.unlockNeighbors(exit)
        println("Exiting Level $levelId at $exitId")
        launchImmediately {
            sceneContainer.changeTo<WorldMapScene>(
                exit,
                transition = AlphaTransition,
                time = TimeSpan(500.0)
            )
        }
    }

}