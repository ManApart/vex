package physics

data class Rectangle(var x: Float = 0f, var y: Float = 0f, val width: Float = 0f, val height: Float = 0f) {
    constructor(x: Int = 0, y: Int = 0, width: Int = 0, height: Int = 0) : this(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())

    operator fun times(magnitude: Float): Rectangle {
        return Rectangle(x * magnitude, y * magnitude, width * magnitude, height * magnitude)
    }

    operator fun plus(point: Vector): Rectangle {
        return Rectangle(x + point.x, y + point.y, width, height)
    }

    val farX get() = x + width
    val farY get() = y + height

    val centerX get() = x + width / 2
    val centerY get() = y + height / 2

    fun source(): Vector {
        return Vector(x, y)
    }

    fun far(): Vector {
        return Vector(farX, farY)
    }

    fun center(): Vector {
        return Vector(centerX, centerY)
    }

}