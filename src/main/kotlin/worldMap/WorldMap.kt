package worldMap

class WorldMap(val exits: List<Exit>, connections: List<Connection>) {
    val connections = connections + buildLevelConnections()

    private fun buildLevelConnections(): List<Connection> {
        return exits
                .groupBy { it.level }
                .filter { it.value.size > 1 }
                .map { group ->
                    val source = group.value.first()
                    group.value.subList(1, group.value.size).map {
                        Connection(source, it)
                    }
                }.flatten()
    }
}