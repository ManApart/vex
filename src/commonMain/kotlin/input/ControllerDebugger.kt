//package input
//
//import org.lwjgl.glfw.GLFW
//import org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER
//import org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER
//import kotlin.math.abs
//
//object ControllerDebugger {
//    private val ignoredButtons = listOf<Int>()
//    private val ignoredAxes: List<Int> = listOf(
//            GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER,
//            GLFW_GAMEPAD_AXIS_LEFT_TRIGGER
//    )
//
//    fun update() {
////        debugAllButtons()
////        debugAllAxis()
////        debugRecognizedAxis()
////        debugPressedRecognizedButtons()
//    }
//
//    private fun debugAllButtons() {
//        (0..GLFW.GLFW_GAMEPAD_BUTTON_LAST)
//                .filter { !ignoredButtons.contains(it) }
//                .forEach { i ->
//                    if (Controller.state.buttons(i).toInt() == 1) {
//                        println("[$i] is pressed.")
//                    }
//                }
//    }
//
//    private fun debugAllAxis() {
//        (0..GLFW.GLFW_GAMEPAD_AXIS_LAST)
//                .filter { !ignoredAxes.contains(it) }
//                .forEach { i ->
//                    val raw = Controller.state.axes(i)
//                    if (abs(raw) > Controller.deadZone) {
//                        println("[$i] is ${raw}.")
//                    }
//                }
//    }
//
//    private fun debugPressedRecognizedButtons() {
//        Controller.inputs.filterIsInstance<Button>().forEach { logButtonChange(it) }
//    }
//
//    private fun debugRecognizedAxis() {
//        Controller.inputs.filterIsInstance<Axis>().forEach { logAxisChange(it) }
//    }
//
//    private fun logAxisChange(axis: Axis) {
//        if (abs(axis.value) > 0) {
//            println("${axis.name} is ${axis.value}")
//        }
//    }
//
//    private fun logButtonChange(button: Button) {
//        if (button.isFirstFrameSinceChanged) {
//            if (button.isPressed) {
//                println("${button.name} is pressed.")
//            } else {
//                println("${button.name} is released.")
//            }
//        }
//    }
//
//}