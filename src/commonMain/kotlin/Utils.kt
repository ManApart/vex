import com.soywiz.korge.view.View
import com.soywiz.korma.geom.*
import org.jbox2d.common.Vec2
import kotlin.math.abs

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

fun Vec2(x: Int = 0, y: Int = 0): Vec2 = Vec2(x.toFloat(), y.toFloat())

operator fun Vec2.div(scalar: Double) = Vec2((x/scalar).toFloat(), (y/scalar).toFloat())

fun View.center(): Point {
    return Point(x + width / 2, y + height / 2)
}

fun Point.toVector(): Vec2 {
    return Vec2(x.toFloat(), y.toFloat())
}

fun Point.toAngle(): Angle {
    return Angle.between(0.0, 0.0, x, y)
}

fun Angle.toPoint(): Point {
    val x = cosine
    val y = sine
    return Point(x, y)
}