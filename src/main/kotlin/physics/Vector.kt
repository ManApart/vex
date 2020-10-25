package physics

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

data class Vector(var x: Float = 0f, var y: Float = 0f) {
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    override fun toString(): String {
        return "($x,$y)"
    }

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

    operator fun minus(other: Vector): Vector {
        return Vector(x - other.x, y - other.y)
    }

    fun distanceTo(other: Vector = Vector()): Int {
        val x = (x - other.x).toDouble().pow(2)
        val y = (y - other.y).toDouble().pow(2)
        return sqrt(x + y).toInt()
    }

    fun getRayTo(other: Vector): List<Vector> {
        val xDiff = other.x - x
        val yDiff = other.y - y
        val slope = yDiff / xDiff
        val quantity = distanceTo(other)

        return when (slope) {
            0f -> findHorizontalPoints(other)
            Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY -> findVerticalPoints(other)
            else -> findPointsBySlope(quantity, yDiff, xDiff, other)
        }
    }

    private fun findVerticalPoints(other: Vector): List<Vector> {
        val points = mutableListOf<Vector>()
        var previousPoint = this
        val min = min(y.toInt(), other.y.toInt())
        val max = max(y.toInt(), other.y.toInt())
        val reversed = y.toInt() == max

        if (reversed) {
            addLastPoint(previousPoint, other, points)
            previousPoint = other
        } else {
            points.add(this)
        }

        (min..max).forEach {
            val point = Vector(x.toInt(), it)
            if (point != previousPoint) {
                points.add(point)
                previousPoint = point
            }
        }
        if (reversed) {
            addLastPoint(previousPoint, this, points)
            points.reverse()
        } else {
            addLastPoint(previousPoint, other, points)
        }
        return points
    }

    private fun findHorizontalPoints(other: Vector): List<Vector> {
        val points = mutableListOf<Vector>()
        var previousPoint = this
        val min = min(x.toInt(), other.x.toInt())
        val max = max(x.toInt(), other.x.toInt())
        val reversed = x.toInt() == max

        if (reversed) {
            addLastPoint(previousPoint, other, points)
            previousPoint = other
        } else {
            points.add(this)
        }

        (min..max).forEach {
            val point = Vector(it, y.toInt())
            if (point != previousPoint) {
                points.add(point)
                previousPoint = point
            }
        }
        if (reversed) {
            addLastPoint(previousPoint, this, points)
            points.reverse()
        } else {
            addLastPoint(previousPoint, other, points)
        }
        return points
    }

    private fun findPointsBySlope(quantity: Int, yDiff: Float, xDiff: Float, other: Vector): List<Vector> {
        val points = mutableListOf(this)
        var previousPoint = this

        (0..quantity).forEach {
            val x = (xDiff * (it / quantity.toFloat())) + this.x
            val y = (yDiff * (it / quantity.toFloat())) + this.y

            val point = Vector(x.toInt(), y.toInt())
            if (point != previousPoint && !x.isNaN()) {
                points.add(point)
                previousPoint = point
            }
        }
        addLastPoint(previousPoint, other, points)
        return points
    }

    private fun addLastPoint(previousPoint: Vector, other: Vector, points: MutableList<Vector>) {
        val rounded = Vector(other.x.toInt(), other.y.toInt())
        if (previousPoint != rounded) {
            points.add(rounded)
        }
    }

}