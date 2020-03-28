package input

import input.Controller.deadZone
import input.Controller.gamePadEnabled
import kotlin.math.abs

class Axis(
        val name: String,
        private val axisIndex: Int,
        private val inverted: Boolean = false,
        private val ignorePositive: Boolean = false,
        private val ignoreNegative: Boolean = false,
        private val shiftValues: Boolean = false,
        positiveInputKey: Int? = null,
        negativeInputKey: Int? = null
) : Input {
    var value = 0f

    private val positiveButton = Button(name, listOfNotNull(positiveInputKey))
    private val negativeButton = Button(name, listOfNotNull(negativeInputKey))

    override fun keyPressed(key: Int, action: Int) {
        positiveButton.keyPressed(key, action)
        negativeButton.keyPressed(key, action)
    }

    override fun update(deltaTime: Float) {
        positiveButton.update(deltaTime)
        negativeButton.update(deltaTime)

        val rawValue = Controller.state.axes(axisIndex)
        value = when {
            positiveButton.isPressed -> 1f
            negativeButton.isPressed -> -1f
            abs(rawValue) < deadZone -> 0f
            rawValue > 0 && ignorePositive -> 0f
            rawValue < 0 && ignoreNegative -> 0f
            shiftValues -> rawValue/2 + .5f
            gamePadEnabled && inverted -> -rawValue
            gamePadEnabled -> rawValue
            else -> 0f
        }
    }
}