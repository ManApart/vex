import level.DEFAULT_TILE
import level.LevelMap
import level.Tile
import level.TileType
import physics.RigidBody
import physics.RigidBodyStubbedOwner
import physics.Vector

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
    return LevelMap(map)
}

fun createMapFromTemplates(plan: List<List<TileTemplateType>>): LevelMap {
    return createMap(plan.map { outer -> outer.map { it.solid } })
}

fun createAndMoveBody(plan: List<List<TileTemplateType>>): RigidBody {
    var start = Tile()
    var goal = Tile()

    val map = createMapFromTemplates(plan)

    plan.indices.forEach { y ->
        plan[y].indices.forEach { x ->
            val planType = plan[y][x]
            if (planType == TileTemplateType.S) {
                start = map.getTile(x, plan.size - 1 - y)
            }
            if (planType == TileTemplateType.G || planType == TileTemplateType.GG) {
                goal = map.getTile(x, plan.size - 1 - y)
            }
        }
    }

    val owner = RigidBodyStubbedOwner()
    val body = RigidBody(map, owner, 1f, 1f)
    body.bounds.x = start.x.toFloat()
    body.bounds.y = start.y.toFloat()

    val velocity = goal.vector - start.vector
    body.velocity.x = velocity.x
    body.velocity.y = velocity.y

    //prevent acceleration dampening
    body.velocity.x += 1f
    body.acceleration.x = -1f

    body.update(1f)

    return body
}

fun horizontalPoints(size: Int, x: Int = 0, y: Int = 0): List<Vector> {
    return (0 until size).map { Vector(x + it, y) }
}

fun verticalPoints(size: Int, x: Int = 0, y: Int = 0): List<Vector> {
    return (0 until size).map { Vector(x, y + it) }
}