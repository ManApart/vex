package ui.worldMap

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.scene.AlphaTransition
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korio.async.launchImmediately
import ui.level.LevelScene
import worldMap.Exit
import worldMap.WorldMapManager
import kotlin.properties.Delegates

class WorldMapScene(private val spawnExitId: Int = 0) : Scene() {
    var player: Player by Delegates.notNull()

    override suspend fun Container.sceneInit() {
        val start = WorldMapManager.worldMap.exits.first { spawnExitId == it.id }.also { it.unlocked = true }
        val exits = paint(WorldMapManager.worldMap)
        player = Player(start, exits, ::enterLevel).also { it.init(); addChild(it) }

    }

    private fun enterLevel(exit: Exit) {
        launchImmediately {
            sceneContainer.changeTo<LevelScene>(
                exit.level,
                transition = AlphaTransition,
                time = TimeSpan(500.0)
            )
        }
    }

}