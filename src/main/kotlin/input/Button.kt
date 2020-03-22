package input

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_REPEAT

class Button(val name: String, inputKeys: List<Int> = listOf(), private val inputButtons: List<Int> = listOf()) : Input{
    constructor(name: String, vararg inputKeys: Int) : this(name, inputKeys.toList())

    var isPressed = false
    var isFirstFrameSinceChanged = false
    var stateTime = 0

    private val pressedKeys = inputKeys.map { Pair(it, false) }.toMap().toMutableMap()

    override fun keyPressed(key: Int, action: Int) {
        if (key == GLFW.GLFW_KEY_UNKNOWN) return

        if (pressedKeys.containsKey(key)) {
            pressedKeys[key] = (action == GLFW_PRESS || action == GLFW_REPEAT)
        }
    }


    override fun update() {
        val pressed = isKeyboardPressed() || isGamepadPressed()

        if (pressed != isPressed) {
            isPressed = pressed
            isFirstFrameSinceChanged = true
            stateTime = 0
        } else {
            isFirstFrameSinceChanged = false
            stateTime++
        }
    }

    fun isFirstPressed(): Boolean {
        return isPressed && isFirstFrameSinceChanged
    }

    private fun isKeyboardPressed(): Boolean {
        return pressedKeys.values.any { it }
    }

    private fun isGamepadPressed(): Boolean {
        return inputButtons.any {
            Controller.state.buttons(it).toInt() == GLFW_PRESS
        }
    }

}