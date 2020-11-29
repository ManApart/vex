package worldMap

class Connection(
        levelIdA: Int,
        exitIdA: Int,
        levelIdB: Int,
        exitIdB: Int,
        levels: List<Exit>
) {
    val source = levels.first { it.level.id == levelIdA && it.exitId == exitIdA }
    val destination = levels.first { it.level.id == levelIdB && it.exitId == exitIdB }
    var unlocked = false

}