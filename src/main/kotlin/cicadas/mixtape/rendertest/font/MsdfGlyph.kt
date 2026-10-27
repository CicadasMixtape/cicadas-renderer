package cicadas.mixtape.rendertest.font

import com.mojang.blaze3d.vertex.BufferBuilder
import org.joml.Matrix4f
import java.awt.Color

class MsdfGlyph(
    data: MsdfData.GlyphData,
    atlasWidth: Float,
    atlasHeight: Float
) {
    var minU = 0f
    var maxU = 0f
    var minV = 0f
    var maxV = 0f
    var width = 0f
    var height = 0f
    var topPosition = 0f
    val advance = data.advance

    init {
        data.atlasBounds?.also {
            minU = it.left / atlasWidth
            maxU = it.right / atlasWidth
            minV = 1f - it.top / atlasHeight
            maxV = 1f - it.bottom / atlasHeight
        }

        data.planeBounds?.also {
            width = it.right - it.left
            height = it.top - it.bottom
            topPosition = it.top
        }
    }

    fun apply(
        builder: BufferBuilder,
        matrix4f: Matrix4f,
        size: Float,
        x: Float,
        y: Float,
        color: Color
    ): Float {
        val width = width * size
        val height = height * size
        val y = y - topPosition * size

        builder.addVertex(matrix4f, x,         y,          0f).setUv(minU, minV).setColor(color.rgb)
        builder.addVertex(matrix4f, x,         y + height, 0f).setUv(minU, maxV).setColor(color.rgb)
        builder.addVertex(matrix4f, x + width, y + height, 0f).setUv(maxU, maxV).setColor(color.rgb)
        builder.addVertex(matrix4f, x + width, y,          0f).setUv(maxU, minV).setColor(color.rgb)

        return advance * size
    }
}