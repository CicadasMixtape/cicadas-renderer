package cicadas.mixtape.renderer.font

import cicadas.mixtape.renderer.api.mc
import com.mojang.blaze3d.vertex.BufferBuilder
import kotlinx.serialization.json.Json
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.joml.Matrix4f
import java.awt.Color

class MsdfFont private constructor(
    val texture: AbstractTexture,
    val atlas: MsdfData.AtlasData,
    val metrics: MsdfData.MetricsData,
    val glyphs: Map<Int, MsdfGlyph>
) {
    val thickness = 0.05f

    companion object {
        fun build(id: Identifier): MsdfFont {
            val json = Json { ignoreUnknownKeys = true }
            val id = id.withPrefix("fonts/")
            val stream = mc.resourceManager.open(id.withSuffix(".json"))
            val texture = mc.textureManager.getTexture(id.withSuffix(".png"))
            val data = json.decodeFromString<MsdfData>(stream.readAllBytes().decodeToString())
            val glyphs = data.glyphs.associate { it.unicode to MsdfGlyph(it, data.atlas.width, data.atlas.height) }
            return MsdfFont(texture, data.atlas, data.metrics, glyphs)
        }
    }

    fun applyGlyphs(
        builder: BufferBuilder,
        matrix4f: Matrix4f,
        text: Component,
        size: Float,
        x: Float,
        y: Float,
        color: Color
    ): Int {
        var count = 0
        var offsetX = x
        var offsetY = y

        text.visualOrderText.accept { _, style, code ->
            if (code == '\n'.code) {
                offsetX = x
                offsetY += getHeight(size) + (size * 0.1f)
                return@accept true
            }

            val glyph = glyphs[code]
            val current = if (style.isEmpty) {
                color
            } else {
                Color(style.color?.value ?: color.rgb)
            }

            if (glyph == null) return@accept true

            offsetX += glyph.apply(builder, matrix4f, size, offsetX, offsetY, current) + getThickness(size)
            count += 1

            true
        }

        return count
    }

    fun getFont(size: Float) = Font(this, size)

    fun getWidth(text: Component, size: Float): Float {
        var width = 0f

        text.visualOrderText.accept { _, _, code ->
            val glyph = glyphs[code] ?: return@accept true
            width += (glyph.advance * size) + getThickness(size)
            true
        }

        return width
    }

    fun getHeight(size: Float): Float {
        return (metrics.lineHeight + metrics.descender - 0.2f) * size
    }

    private fun getThickness(size: Float) = thickness * 0.5f * size
}