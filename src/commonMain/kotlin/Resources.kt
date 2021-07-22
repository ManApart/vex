import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import level.LevelTemplate

object Resources {
    private val levels = mutableMapOf<Int, Bitmap>()

    suspend fun getLevel(template: LevelTemplate): Bitmap {
        return levels.getOrPut(template.id) {
            resourcesVfs["levels/${template.fileName}.png"].readBitmap()
        }
    }
}