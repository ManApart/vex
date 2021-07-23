package ui.level

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.fixedSizeContainer
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
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


        fixedSizeContainer(VIRTUAL_SIZE, VIRTUAL_SIZE, clip = false) {
            solidRect(VIRTUAL_SIZE, VIRTUAL_SIZE, Colors.BLUE)
            paint(map)
            addChild(player)
            player.init(map.getSpawnTile(spawnExitId)!!)
        }

        addFixedUpdater(30.timesPerSecond) {
            tick()
        }

    }

    private fun tick() {
//        processInput(deltaTime)
//        render()
//        gameMode.afterRender(deltaTime)
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