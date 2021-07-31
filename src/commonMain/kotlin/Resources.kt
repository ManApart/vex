import com.soywiz.korge.tiled.TiledMap
import com.soywiz.korge.tiled.readTiledMap
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import level.LevelTemplate

object Resources {
    private val levels = mutableMapOf<Int, Bitmap>()
    private val maps = mutableMapOf<Int, TiledMap>()
    private val images = mutableMapOf<String, Bitmap>()

    suspend fun getLevel(template: LevelTemplate): Bitmap {
        return levels.getOrPut(template.id) {
            resourcesVfs["levels/${template.fileName}.png"].readBitmap()
        }
    }

    suspend fun getImage(path: String): Bitmap {
        return images.getOrPut(path) {
            resourcesVfs[path].readBitmap()
        }
    }

    suspend fun getMap(template: LevelTemplate): TiledMap {
        return maps.getOrPut(template.id) {
            resourcesVfs["levels/${template.fileName}.tmx"].readTiledMap()
        }
    }

}