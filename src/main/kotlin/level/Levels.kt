package level

object Levels {
    val levels = buildLevels()

    private fun buildLevels(): Map<Int, LevelTemplate> {
        return listOf(
                LevelTemplate(0, "Test Level", "test-level.png"),
                LevelTemplate(1, "Test Level 2", "small-level.png"),
                LevelTemplate(2, "Test Level 3", "small-level.png")
        ).associateBy { it.id }
    }
}