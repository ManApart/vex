package ui.level

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.scene.AlphaTransition
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.tiles.TileMap
import com.soywiz.korge.view.xy
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launchImmediately
import level.LevelMap
import level.LevelMapBuilder
import level.LevelTemplate
import org.jbox2d.dynamics.BodyType
import ui.VIRTUAL_SIZE
import ui.worldMap.WorldMapScene
import worldMap.Exit
import worldMap.WorldMapManager
import kotlin.properties.Delegates

class LevelScene(private val exit: Exit) : Scene() {
//    var map: LevelMap by Delegates.notNull()
    var tiled: TiledMap by Delegates.notNull()
    var player: Player by Delegates.notNull()

    override suspend fun Container.sceneInit() {
        tiled = Resources.getMap(exit.level)
//        map = LevelMapBuilder().createMap(exit.level)
        player = Player(exit.level.id, ::exitLevel)

        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
            tiledMapView(tiled, smoothing = false) {
                addCollision(tiled)
                scale = 3.0
                addChild(player)
            }
            player.init()
//            player.init(map.getSpawnTile(exit.id)!!)
        }
//            .follow(player, true)

    }

    private fun Container.addCollision(tiled: TiledMap) {
        val width = tiled.tilewidth
        val layer = tiled.tileLayers.first()
        val tiles = tiled.tilesets.first().data.tilesById
        (0 until layer.width).forEach { x ->
            (0 until layer.height).forEach { y ->
                val id = layer[x, y] - 1
                if (tiles[id]?.type == "collision") {
                    solidRect(width, width, Colors.BLUE) {
                        alpha = 0.0
                        xy(x * width, y * width)
                        registerBodyWithFixture(type = BodyType.STATIC)
                    }
                }
            }
        }
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