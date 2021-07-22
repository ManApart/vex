class Color(val red: Float, val green: Float, val blue: Float) {
    constructor(red: Int, green: Int, blue: Int) : this(intToFloat(red), intToFloat(green), intToFloat(blue))

    val redInt = floatToInt(red)
    val greenInt = floatToInt(green)
    val blueInt = floatToInt(blue)
}

private fun intToFloat(intVal: Int): Float {
    return intVal / 255.toFloat()
}

private fun floatToInt(floatVal: Float): Int {
    return (floatVal * 255).toInt()
}