package level

import com.soywiz.korim.color.RGBA

enum class TileType(val colorKey: RGBA) {
    TILE(RGBA(255, 255, 255)),
    SPACE(RGBA(0, 0, 0)),
    EXIT(RGBA(0, 255, 0))
}

fun from(value: RGBA): TileType {
    return TileType.values().firstOrNull { it.colorKey == value } ?: TileType.SPACE
}