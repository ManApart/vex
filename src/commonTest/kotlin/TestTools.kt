import level.DEFAULT_TILE
import level.LevelMap
import level.Tile
import level.TileType
import org.jbox2d.common.Vec2

/**
0 = space
1 = tile
 */
fun createMap(plan: List<List<Int>>): LevelMap {
    val map = Array(plan.size) { Array(plan[0].size) { DEFAULT_TILE } }
    plan.indices.forEach { y ->
        plan[y].indices.forEach { x ->
            val planType = plan[y][x]
            val type = if (planType == 1) {
                TileType.TILE
            } else {
                TileType.SPACE
            }
            map[x][plan.size-1 - y] = Tile(type, x, plan.size-1 - y)
        }
    }
    return LevelMap(0, map)
}



fun horizontalPoints(size: Int, x: Int = 0, y: Int = 0): List<Vec2> {
    return (0 until size).map { Vec2(x + it, y) }
}

fun verticalPoints(size: Int, x: Int = 0, y: Int = 0): List<Vec2> {
    return (0 until size).map { Vec2(x, y + it) }
}