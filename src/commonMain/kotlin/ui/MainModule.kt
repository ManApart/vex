package ui

import com.soywiz.korge.scene.Module
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.Size
import com.soywiz.korma.geom.SizeInt
import ui.level.LevelScene
import ui.worldMap.WorldMapScene
import worldMap.Exit

const val WINDOW_SIZE = 800
const val VIRTUAL_SIZE = 640

object MainModule : Module() {
//    override val mainScene = LevelScene::class
    override val mainScene = WorldMapScene::class
    override val title: String = "Vex"
    override val size: SizeInt = SizeInt(Size(VIRTUAL_SIZE, VIRTUAL_SIZE))
    override val windowSize = SizeInt(Size(WINDOW_SIZE, WINDOW_SIZE))
    override val icon: String = "icon.png"
    override val scaleMode: ScaleMode = ScaleMode.SHOW_ALL
    override val clipBorders: Boolean = false
    override val scaleAnchor: Anchor = Anchor.TOP_LEFT

    override suspend fun AsyncInjector.configure() {
        mapPrototype { Exit(0, Resources.levelTemplates[0]!!) }
        mapPrototype { LevelScene(get()) }
        mapPrototype { WorldMapScene(get()) }
    }
}