package player

import input.Controller
import physics.Rectangle
import worldMap.LevelWrapper

class WorldMapPlayer(originLevel: LevelWrapper) {
    val currentLevel = originLevel
    val goalLevel = originLevel
    val bounds = Rectangle(originLevel.bounds.x, originLevel.bounds.y, 0.6f, 0.8f)


    fun update(deltaTime: Float) {
        processKeys()
//        updateState(deltaTime)
//        body.update(deltaTime, xMaxVelocity(), yMaxVelocity())

    }

    private fun processKeys() {
        if (Controller.jump.isFirstPressed()) {
            if (currentLevel == goalLevel){
                //Load Level
                // Eventually figure out which exit, maybe show all known exits on map
            }
        }
    }


}