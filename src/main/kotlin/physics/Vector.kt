package physics

import kotlin.math.max

data class Vector(var x: Float = 0f, var y: Float = 0f) {
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    fun scale(magnitude: Float) {
        this.x *= magnitude
        this.y *= magnitude
    }

    fun add(other: Vector) {
        this.x += other.x
        this.y += other.y
    }

    operator fun times(magnitude: Float): Vector {
        return Vector(x * magnitude, y * magnitude)
    }

    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y)
    }

    fun getRayTo(other: Vector): List<Vector> {
        val xs = (x.toInt()..other.x.toInt()).toList()
        val ys = (y.toInt()..other.y.toInt()).toList()
        val largestListSize = max(xs.size, ys.size)
        return (0 until largestListSize).map {
            val x = xs.getOrNull(it) ?: xs.last()
            val y = ys.getOrNull(it) ?: ys.last()
            Vector(x, y)
        }
    }

}