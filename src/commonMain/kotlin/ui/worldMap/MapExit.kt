package ui.worldMap

import com.soywiz.korge.view.View
import com.soywiz.korim.tiles.tiled.TiledMap
import worldMap.Connection
import worldMap.Exit

data class MapExit(val exit: Exit, val obj: TiledMap.Object, val view: View) {
    var unlocked = false
    val connections = mutableListOf<Connection>()

    override fun toString(): String {
        return exit.toString()
    }

    fun unlock(){
        unlocked = true
        connections.filter { it.source == this }.forEach { it.unlock() }
    }
}