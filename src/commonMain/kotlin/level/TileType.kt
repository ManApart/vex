package level

import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

enum class TileType(val colorKey: Int, val color: RGBA) {
    TILE(255, Colors.WHITE),
    SPACE(0, Colors.BLACK),
    EXIT( 100, Colors.GREEN)
}

fun from(value: Int): TileType {
    return TileType.values().firstOrNull { it.colorKey == value } ?: TileType.SPACE
}