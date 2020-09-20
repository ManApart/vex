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
            map[x][y] = Tile(type, x, y)
        }
    }
    return LevelMap(map)
}