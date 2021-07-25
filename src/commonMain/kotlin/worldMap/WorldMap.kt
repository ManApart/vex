package worldMap

//class WorldMap(val exits: List<Exit>, connections: List<Connection>) {
//    val connections = connections + buildLevelConnections()
//
//    private fun buildLevelConnections(): List<Connection> {
//        return exits
//                .groupBy { it.level }
//                .filter { it.value.size > 1 }
//                .map { group ->
//                    val source = group.value.first()
//                    group.value.subList(1, group.value.size).map {
//                        Connection(source, it)
//                    }
//                }.flatten()
//    }
//
//    fun unlockNeighbors(source: Exit) {
//        if (source.exitId != 0) {
//            this.connections.filter { it.source == source || it.destination == source }.forEach {
//                it.unlocked = true
//                it.source.unlocked = true
//                it.destination.unlocked = true
//            }
//        }
//    }
//}