import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Rectangle
import org.jbox2d.common.Vec2

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

fun Vec2(x: Int, y: Int): Vec2 = Vec2(x.toFloat(), y.toFloat())

fun Rectangle.center(): Point {
    return Point(x + width / 2, y + height / 2)
}