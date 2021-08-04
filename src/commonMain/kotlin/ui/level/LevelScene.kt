package ui.level

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.box2d.registerBodyWithFixture
import com.soywiz.korge.scene.AlphaTransition
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.async.launchImmediately
import org.jbox2d.dynamics.BodyType
import ui.VIRTUAL_SIZE
import ui.worldMap.WorldMapScene
import worldMap.Exit

class LevelScene(private val spawn: Exit) : Scene() {
    private lateinit var tiled: TiledMap
    private lateinit var player: Player
    private val exits = mutableListOf<ExitView>()

    override suspend fun Container.sceneInit() {
        tiled = Resources.getLevel(spawn.level)
        player = Player(::interact)

        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
            tiledMapView(tiled, smoothing = false, showShapes = false) {
                setupTiledMap(tiled)
                scale = 3.0
                addChild(player)
            }
            player.init(findSpawn())
        }.follow(player, true)

    }

    private fun findSpawn(): SolidRect {
        return (exits.firstOrNull { it.id == spawn.id } ?: exits.first()).view
    }

    private fun Container.setupTiledMap(tiled: TiledMap) {
        val width = tiled.tilewidth
        val layer = tiled.tileLayers.first()
        val tiles = tiled.tilesets.first().data.tilesById
        (0 until layer.width).forEach { x ->
            (0 until layer.height).forEach { y ->
                val id = layer[x, y] - 1
                if (tiles[id]?.type == "collision") {
                    solidRect(width, width) {
                        alpha = 0.0
                        xy(x * width, y * width)
                        registerBodyWithFixture(type = BodyType.STATIC)
                    }
                }
            }
        }
        tiled.objectLayers.first().objects.fastForEach { obj ->
            val id = obj.properties["exitId"]?.int
            if (id != null) {
                val rect = solidRect(obj.bounds.width, obj.bounds.height) {
                    alpha = 0.0
                    xy(obj.bounds.x, obj.bounds.y)
                }
                exits.add(ExitView(id, rect))
            }

        }
        tiled.objectLayers.first().objects.fastForEach { obj ->
            val isBounds = obj.properties["bounds"]?.bool ?: false
            if (isBounds) {
                solidRect(obj.bounds.width, obj.bounds.height) {
                    alpha = 0.0
                    xy(obj.bounds.x, obj.bounds.y)
                    registerBodyWithFixture(type = BodyType.STATIC)
                }
            }
        }
    }

    private fun interact(view: View) {
        val exit = exits.firstOrNull { it.view.collidesWith(view) }
        if (exit != null) {
            exitLevel(spawn.level.id, exit.id)
        }
    }

    private fun exitLevel(levelId: Int, exitId: Int) {
        println("Exiting Level $levelId at $exitId")
        val exit = Exit(exitId, Resources.levelTemplates[levelId]!!)
        launchImmediately {
            sceneContainer.changeTo<WorldMapScene>(
                exit,
                transition = AlphaTransition,
                time = TimeSpan(500.0)
            )
        }
    }

}