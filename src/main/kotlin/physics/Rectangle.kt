package physics

data class Rectangle(var x: Float = 0f, var y: Float = 0f, val width: Float = 0f, val height: Float = 0f) {
    operator fun times(magnitude: Float): Rectangle {
        return Rectangle(x * magnitude, y * magnitude, width * magnitude, height * magnitude)
    }
    operator fun plus(point: Vector): Rectangle {
        return Rectangle(x + point.x, y + point.y, width, height)
    }
}