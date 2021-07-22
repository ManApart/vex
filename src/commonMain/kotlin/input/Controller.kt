//package input
//
//import Vex.window
//import getAngle
//import org.lwjgl.glfw.GLFW.*
//import org.lwjgl.glfw.GLFWGamepadState
//import org.lwjgl.glfw.GLFWKeyCallback
//import toDegrees
//
//
//object Controller {
//    private val id = (GLFW_JOYSTICK_1..GLFW_JOYSTICK_LAST).firstOrNull {
//        glfwJoystickPresent(it) && glfwJoystickIsGamepad(it)
//    }
//    val state = GLFWGamepadState.create()
//    val name = id?.let { glfwGetGamepadName(id) } ?: "None"
//    val gamePadEnabled = id != null
//    const val deadZone = 0.3f
//
//    init {
//        id?.let { glfwGetGamepadState(id, state) }
//    }
//
//    val xAxis = Axis("X Axis", GLFW_GAMEPAD_AXIS_LEFT_X, positiveInputKey = GLFW_KEY_RIGHT, negativeInputKey = GLFW_KEY_LEFT)
//    val yAxis = Axis("Y Axis", GLFW_GAMEPAD_AXIS_LEFT_Y, positiveInputKey = GLFW_KEY_UP, negativeInputKey = GLFW_KEY_DOWN)
//    val xAim = Axis("X Aim", GLFW_GAMEPAD_AXIS_RIGHT_X, positiveInputKey = GLFW_KEY_D, negativeInputKey = GLFW_KEY_A)
//    val yAim = Axis("Y Aim", GLFW_GAMEPAD_AXIS_RIGHT_Y, positiveInputKey = GLFW_KEY_W, negativeInputKey = GLFW_KEY_S)
//    val grapple = Axis("Grapple", GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER, shiftValues = true, positiveInputKey = GLFW_KEY_LEFT_SHIFT)
//    val ascend = Axis("Ascend", GLFW_GAMEPAD_AXIS_LEFT_TRIGGER, shiftValues = true, positiveInputKey = GLFW_KEY_RIGHT_SHIFT)
//
//    val jump = Button("Jump", listOf(GLFW_KEY_SPACE), listOf(GLFW_GAMEPAD_BUTTON_A))
//    val interact = Button("Interact", listOf(GLFW_KEY_ENTER), listOf(GLFW_GAMEPAD_BUTTON_X))
//    val dashLeft = Button("Dash Left", listOf(GLFW_KEY_Z), listOf(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER))
//    val dashRight = Button("Dash Right", listOf(GLFW_KEY_X), listOf(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER))
//
//    val inputs = listOf(
//            xAxis,
//            yAxis,
//            xAim,
//            yAim,
//            grapple,
//            ascend,
//            jump,
//            interact,
//            dashLeft,
//            dashRight
//    )
//
//    init {
//        glfwSetKeyCallback(window, object : GLFWKeyCallback() {
//            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
//                inputs.forEach { it.keyPressed(key, action) }
//            }
//        })
//
//    }
//
//    fun update(deltaTime: Float) {
//        id?.let { glfwGetGamepadState(id, state) }
//        inputs.forEach { it.update(deltaTime) }
//    }
//
//    fun getLeftStickAngle(): Double {
//        return getAngle(xAxis.value, yAxis.value).toDegrees()
//    }
//
//    fun getAimAngle(): Double {
//        return getAngle(xAim.value, yAim.value).toDegrees()
//    }
//
//}