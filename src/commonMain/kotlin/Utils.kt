import com.soywiz.korge.view.View
import com.soywiz.korma.geom.*

fun clamp(x: Float, min: Float, max: Float): Float {
    return when {
        x < min -> min
        x > max -> max
        else -> x
    }
}

fun clamp(x: Double, min: Double, max: Double): Double {
    return when {
        x < min -> min
        x > max -> max
        else -> x
    }
}

fun View.center(): Point {
    return Point(x + width / 2, y + height / 2)
}

fun Point.toAngle(): Angle {
    return Angle.between(0.0, 0.0, x, y)
}

fun Angle.toPoint(): Point {
    val x = cosine
    val y = sine
    return Point(x, y)
}