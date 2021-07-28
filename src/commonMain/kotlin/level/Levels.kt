package level

val levels = buildLevels()

private fun buildLevels(): Map<Int, LevelTemplate> {
    return listOf(
        LevelTemplate(0, "Test Level", "test-level"),
        LevelTemplate(1, "Test Level 2", "small-level"),
        LevelTemplate(2, "Test Level 3", "small-level")
    ).associateBy { it.id }
}