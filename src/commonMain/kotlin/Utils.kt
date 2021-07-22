fun clamp(x: Float, min: Float, max: Float) : Float {
   return when {
        x < min -> min
        x > max -> max
        else -> x
    }
}