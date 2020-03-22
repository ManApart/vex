package input

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWGamepadState


object Controller {
    private val id = (GLFW_JOYSTICK_1..GLFW_JOYSTICK_LAST).firstOrNull {
        glfwJoystickPresent(it) && glfwJoystickIsGamepad(it)
    }
    val state = GLFWGamepadState.create()
    val name = id?.let { glfwGetGamepadName(id) } ?: "None"
    val gamePadEnabled = id != null
    const val deadZone = 0.3f

    init {
        id?.let { glfwGetGamepadState(id, state) }
    }

    //    val xAxis = Axis(gamePad, "X Axis", positiveButton = Button(Input.Keys.RIGHT), negativeButton = Button(Input.Keys.LEFT))
//    val yAxis = Axis(gamePad, "Y Axis", true, positiveButton = Button(Input.Keys.UP), negativeButton = Button(Input.Keys.DOWN))
//
//    val xAim = Axis(gamePad, "X Rotation", positiveButton = Button(Input.Keys.D), negativeButton = Button(Input.Keys.A))
//    val yAim = Axis(gamePad, "Y Rotation", true, positiveButton = Button(Input.Keys.W), negativeButton = Button(Input.Keys.S))
//
//    val grapple = Axis(gamePad, "Z Axis", ignorePositive = true, positiveButton =  Button(Input.Keys.SHIFT_LEFT))
//    val ascend = Axis(gamePad, "Z Axis", ignoreNegative = true, positiveButton =  Button(Input.Keys.SHIFT_RIGHT))
//
    val jump = Button(listOf(GLFW_KEY_SPACE), state, listOf(GLFW_GAMEPAD_BUTTON_A))
//    val dashLeft = Button(listOf(Input.Keys.Z), gamePad, listOf("Button 4"))
//    val dashRight = Button(listOf(Input.Keys.X), gamePad, listOf("Button 5"))


    fun update() {
        id?.let { glfwGetGamepadState(id, state) }
//        xAxis.update()
//        yAxis.update()
//        yAim.update()
//        yAim.update()
//        grapple.update()
//        ascend.update()
        jump.update()
//        dashLeft.update()
//        dashRight.update()
    }

}