package worldMap

class Connection(
        levelIdA: Int,
        val exitIdA: Int,
        levelIdB: Int,
        val exitIdB: Int,
        levels: Map<Int, LevelWrapper>
) {
    val source = levels[levelIdA]!!
    val destination = levels[levelIdB]!!

}