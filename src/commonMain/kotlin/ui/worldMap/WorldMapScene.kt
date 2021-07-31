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
import toVector
import ui.VIRTUAL_SIZE
import ui.level.LevelScene
import worldMap.Connection
import worldMap.Exit

class WorldMapScene(private val spawn: Exit) : Scene() {
    private lateinit var tiled: TiledMap
    private lateinit var player: Player
    private val exits = mutableListOf<MapExit2>()

    override suspend fun Container.sceneInit() {
        tiled = Resources.getOverworld()
//        val exits = paint(WorldMapManager.worldMap)
        cameraContainer(VIRTUAL_SIZE.toDouble(), VIRTUAL_SIZE.toDouble(), clip = true) {
            tiledMapView(tiled, smoothing = false, showShapes = false) {
                setupTiledMap(tiled)
                scale = 1.0
//            addChild(player)
            }
        }
//        player = Player(spawn, exits, ::enterLevel).also { it.init(); addChild(it) }

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
        tiled.objectLayers.first().objects.fastForEach { obj ->
            val id = obj.properties["exitId"]?.int
            if (id != null) {
                val levelId = obj.properties["levelId"]!!.int
                val rect = circle(obj.bounds.width / 2) {
                    alpha = 0.5
                    xy(obj.bounds.x, obj.bounds.y)
                }
                val exit = Exit(id, Resources.levelTemplates[levelId]!!, obj.bounds.position.toVector())
                exits.add(MapExit2(exit, obj, rect))
            }
        }

        exits.fastForEach { mapExit ->
            createConnection(mapExit, mapExit.obj.properties["connectA"]?.int)
            createConnection(mapExit, mapExit.obj.properties["connectB"]?.int)
            createConnection(mapExit, mapExit.obj.properties["connectC"]?.int)
        }

        exits.fastForEach { mapExit ->
            mapExit.exit.connections.filter { it.source == mapExit.exit }.fastForEach { connection ->
                val source = mapExit.view.pos
                val destination = 
                graphics {
                    stroke(Colors.GREEN, StrokeInfo(thickness = 2.0)) {
                        line(source, destination)
                    }
                }
            }
        }


    }

    private fun createConnection(mapExit: MapExit2, connectionId: Int?) {
        if (connectionId != null) {
            val other = exits.first { it.obj.id == connectionId }
            Connection(mapExit.exit, other.exit)
        }
    }

}