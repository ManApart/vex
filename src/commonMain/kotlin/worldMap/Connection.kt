package worldMap

import ui.worldMap.MapExit


class Connection(val source: MapExit, val destination: MapExit) {
    var unlocked = false

    init {
        source.connections.add(this)
        destination.connections.add(this)
    }

    fun getOther(exit: MapExit): MapExit {
        return if (exit == source) destination else source
    }

    fun unlock() {
        unlocked = true
        source.unlocked = true
        destination.unlocked = true
    }


}