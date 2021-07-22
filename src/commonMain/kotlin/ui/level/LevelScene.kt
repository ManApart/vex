package ui.level

import GameMode
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.fixedSizeContainer
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
import level.LevelManager
import ui.VIRTUAL_SIZE
import worldMap.Exit
import worldMap.WorldMapManager

class LevelScene  : Scene() {
    private val worldManager = WorldMapManager()
    private var gameMode: GameMode = worldManager

    override suspend fun Container.sceneInit() {

        fixedSizeContainer(VIRTUAL_SIZE, VIRTUAL_SIZE, clip = false) {
          solidRect(VIRTUAL_SIZE, VIRTUAL_SIZE, Colors.BLUE)
        }

        addFixedUpdater(30.timesPerSecond){
            tick()
        }

        gameMode.init()
    }

    private fun tick() {
//        processInput(deltaTime)
//        render()
//        gameMode.afterRender(deltaTime)
    }

    fun enterLevel(exit: Exit){
        this.gameMode = LevelManager(exit.level, exit.exitId)
    }

    fun exitLevel(levelId: Int, exitId: Int){
        val exit = worldManager.worldMap.exits.first { it.level.id == levelId && it.exitId == exitId }
        worldManager.worldMap.unlockNeighbors(exit)
        worldManager.player.setPosition(exit)
        this.gameMode = worldManager
    }

}