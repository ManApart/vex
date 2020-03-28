package physics

data class Vector(var x: Float = 0f, var y: Float = 0f) {
    fun scale(magnitude: Float) {
        this.x *= magnitude
        this.y *= magnitude
    }

    fun add(other: Vector)  {
        this.x += other.x
        this.y += other.y
    }

    operator fun times(magnitude: Float): Vector {
        return Vector(x * magnitude, y * magnitude)
    }

    operator fun plus(other: Vector) : Vector {
        return Vector(x + other.x, y + other.y)
    }
}