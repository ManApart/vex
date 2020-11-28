package player

import physics.Rectangle
import worldMap.LevelWrapper

class WorldMapPlayer(originLevel: LevelWrapper) {
    val currentLevel = originLevel
    val goalLevel = originLevel
    val bounds = Rectangle(originLevel.bounds.x, originLevel.bounds.y, 0.6f, 0.8f)
}