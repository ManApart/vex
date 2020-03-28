package physics

enum class Direction(val vector: Vector) {
    LEFT(Vector(-1f, 0f)),
    RIGHT(Vector(1f, 0f)),
    UP(Vector(0f, 1f)),
    DOWN(Vector(0f, -1f));

    companion object {
        fun fromNumber(number: Float) : Direction {
            return if (number > 0) {
                RIGHT
            } else {
                LEFT
            }
        }
    }
}
