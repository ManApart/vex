package input

import org.lwjgl.glfw.GLFW
import kotlin.math.abs

object ControllerDebugger {
    private val ignoredButtons = listOf<Int>()
    private val ignoredAxes: List<Int> = listOf()

    fun update() {
        debugButtons()
//        debugAxis()
    }

    private fun debugButtons() {

        (0..GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT)
                .filter { !ignoredButtons.contains(it) }
                .forEach { i ->
                    if (Controller.state.buttons(i).toInt() == 1) {
                        logButton(i)
                    }
                }
    }

    private fun logButton(i: Int) {
        println("[$i] is pressed.")
    }

    private fun debugAxis() {
//        (0 until gamePad.axisCount).forEach { i ->
//            if (abs(gamePad.getAxisValue(i)) > Controller.deadZone && !ignoredAxes.contains(i)) {
//                logAxis(i)
//            }
//        }
    }

    private fun logAxis(i: Int) {
//        println("${gamePad.getAxisName(i)} [$i] is ${gamePad.getAxisValue(i)}.")
    }
}