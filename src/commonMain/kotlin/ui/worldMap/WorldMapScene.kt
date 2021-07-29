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

class WorldMapScene(private val spawn: Exit) : Scene() {
    var player: Player by Delegates.notNull()

    override suspend fun Container.sceneInit() {
        val exits = paint(WorldMapManager.worldMap)
        player = Player(spawn, exits, ::enterLevel).also { it.init(); addChild(it) }

    }

    private fun enterLevel(exit: Exit) {
        println("Entering Level ${exit.level.id} ${exit.level.name} at ${exit.id}")
        launchImmediately {
            sceneContainer.changeTo<LevelScene>(
                exit,
                transition = AlphaTransition,
                time = TimeSpan(500.0)
            )
        }
    }

}