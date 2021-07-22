package level

enum class TileType(val colorKey: Int) {
    TILE(255),
    SPACE(0),
    EXIT(100)
}

fun fromInt(value: Int) : TileType {
    return TileType.values().firstOrNull { it.colorKey == value } ?: TileType.SPACE
}