package ui

import com.soywiz.korge.scene.Module
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.*
import level.LevelTemplate
import ui.level.LevelScene

const val WINDOW_WIDTH = 1000
const val WINDOW_HEIGHT = 800
const val VIRTUAL_SIZE = 640

object MainModule : Module() {
    override val mainScene = LevelScene::class
    override val title: String = "Vex"
    override val size: SizeInt = SizeInt(Size(WINDOW_WIDTH, WINDOW_HEIGHT))
    override val windowSize = size
    override val icon: String = "levels/small-level.png"
    override val scaleMode: ScaleMode = ScaleMode.NO_SCALE
    override val clipBorders: Boolean = false
    override val scaleAnchor: Anchor = Anchor.TOP_LEFT

    override suspend fun AsyncInjector.configure() {
        mapPrototype { LevelTemplate(0, "Start", "test-level") }
        mapPrototype { LevelScene(get()) }
    }
}