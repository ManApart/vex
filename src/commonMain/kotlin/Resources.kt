import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import level.LevelTemplate

object Resources {
    private val levels = mutableMapOf<Int, TiledMap>()
    private val worlds = mutableMapOf<Int, TiledMap>()
    private val images = mutableMapOf<String, Bitmap>()

    val levelTemplates = listOf(
        LevelTemplate(0, "Test Level", "test"),
        LevelTemplate(1, "Test Level 2", "small"),
        LevelTemplate(2, "Test Level 3", "flat")
    ).associateBy { it.id }

    suspend fun getImage(path: String): Bitmap {
        return images.getOrPut(path) {
            resourcesVfs[path].readBitmap()
        }
    }

    suspend fun getLevel(id: Int): TiledMap {
        return getLevel(levelTemplates[id]!!)
    }

    suspend fun getLevel(template: LevelTemplate): TiledMap {
        return levels.getOrPut(template.id) {
            resourcesVfs["levels/${template.fileName}.tmx"].readTiledMap()
        }
    }

    suspend fun getOverworld(id: Int = 0): TiledMap {
        return worlds.getOrPut(id) {
            resourcesVfs["overworld.tmx"].readTiledMap()
        }
    }

}