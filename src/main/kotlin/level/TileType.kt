package level

enum class TileType(val colorKey: Int) {
    TILE(-1),
    SPACE(-16777216),
    SPAWN(-10252883)
}

fun fromInt(value: Int) : TileType {
    return TileType.values().firstOrNull { it.colorKey == value } ?: TileType.SPACE
}