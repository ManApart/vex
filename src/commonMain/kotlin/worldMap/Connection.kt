package worldMap

import ui.worldMap.MapExit


class Connection(val source: MapExit, val destination: MapExit) {
    var unlocked = true

    init {
        source.connections.add(this)
        destination.connections.add(this)
    }

    fun getOther(exit: MapExit): MapExit {
        return if (exit == source) destination else source
    }


}