package input

import Vex.window
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_REPEAT
import org.lwjgl.glfw.GLFWGamepadState
import org.lwjgl.glfw.GLFWKeyCallback

class Button(private val inputKeys: List<Int> = listOf(), private val gamePadState: GLFWGamepadState? = null, private val inputButtons: List<Int> = listOf()) {
    constructor(vararg inputKeys: Int) : this(inputKeys.toList())

    var isPressed = false
    var isFirstFrameSinceChanged = false
    var stateTime = 0

    private val pressedKeys = inputKeys.map { Pair(it, false) }.toMap().toMutableMap()


    init {
        GLFW.glfwSetKeyCallback(window, object : GLFWKeyCallback() {
            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
                if (key == GLFW.GLFW_KEY_UNKNOWN) return

                if (pressedKeys.containsKey(key)){
                    pressedKeys[key] = (action == GLFW_PRESS || action == GLFW_REPEAT)
                }
            }
        })

    }

    fun update() {
        val pressed = isKeyboardPressed() || isGamepadPressed()

        if (pressed != isPressed) {
            isPressed = pressed
            isFirstFrameSinceChanged = true
            stateTime = 0
        } else {
            isFirstFrameSinceChanged = false
            stateTime++
        }
        logAllButtons()
    }

    fun isFirstPressed(): Boolean {
        return isPressed && isFirstFrameSinceChanged
    }

    private fun isKeyboardPressed() : Boolean {
       return pressedKeys.values.any{ it }
    }

    private fun isGamepadPressed(): Boolean {
        return inputButtons.any {
            gamePadState?.buttons(it)?.toInt() == GLFW_PRESS
        }
    }


    private fun logAllButtons() {

    }

}