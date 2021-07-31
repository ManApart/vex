package ui.worldMap

import com.soywiz.kds.iterators.fastForEach
import com.soywiz.klock.TimeSpan
import com.soywiz.korge.scene.AlphaTransition
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.tiledMapView
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korim.color.Colors
import com.soywiz.korim.vector.StrokeInfo
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.vector.line
import ui.VIRTUAL_SIZE
import ui.level.LevelScene
import ui.worldMap.MapManager.exits
import worldMap.Connection
import worldMap.Exit

class WorldMapScene(private val spawn: Exit) : Scene() {
    private lateinit var tiled: TiledMap
    private lateinit var player: Player

    override suspend fun Container.sceneInit() {
        tiled = Resources.getOverworld()
        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
            tiledMapView(tiled, smoothing = false, showShapes = false) {
                setupTiledMap(tiled)
                scale = 3.0
                val mapSpawn = exits.firstOrNull { it.exit.level.id == spawn.level.id && it.exit.id == spawn.id }!!
                mapSpawn.unlock()
                player = Player(spawn, exits, ::enterLevel).also { it.init(); addChild(it) }
                redraw()
            }
        }.follow(player, true)

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

    private fun Container.setupTiledMap(tiled: TiledMap) {
        if (exits.isEmpty()) {
            tiled.objectLayers.first().objects.fastForEach { obj ->
                val id = obj.properties["exitId"]?.int
                if (id != null) {
                    val levelId = obj.properties["levelId"]!!.int
                    val rect = circle(obj.bounds.width / 2) {
                        alpha = 0.0
                        xy(obj.bounds.x, obj.bounds.y)
                    }
                    val exit = Exit(id, Resources.levelTemplates[levelId]!!)
                    exits.add(MapExit(exit, obj, rect))
                }
            }
            exits.fastForEach { mapExit ->
                createConnection(mapExit, mapExit.obj.properties["connectA"]?.int)
                createConnection(mapExit, mapExit.obj.properties["connectB"]?.int)
                createConnection(mapExit, mapExit.obj.properties["connectC"]?.int)
            }
        }
    }

    private fun Container.redraw() {
        exits.fastForEach { mapExit ->
            mapExit.view.alpha = if (mapExit.unlocked) 0.5 else 0.0
            addChild(mapExit.view)

            mapExit.connections
                .filter { it.source == mapExit && it.unlocked }
                .fastForEach { connection ->
                    val source = mapExit.view.pos
                    val destination = connection.destination.view.pos
                    graphics {
                        stroke(Colors.ALICEBLUE, StrokeInfo(thickness = 2.0)) {
                            line(source, destination)
                        }
                    }
                }
        }
    }

    private fun createConnection(mapExit: MapExit, connectionId: Int?) {
        if (connectionId != null) {
            val other = exits.first { it.obj.id == connectionId }
            Connection(mapExit, other)
        }
    }

}