package worldMap

import level.LevelTemplate

class WorldMap(val levels: Map<Int, LevelTemplate>, val connections: List<Connection>) {
}