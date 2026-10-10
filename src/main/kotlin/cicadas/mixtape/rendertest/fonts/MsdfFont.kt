package cicadas.mixtape.rendertest.fonts

import cicadas.mixtape.rendertest.utility.FontUtility
import com.mojang.blaze3d.vertex.BufferBuilder
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import java.awt.Color

class MsdfFont(
    val texture: AbstractTexture,
    val atlas: MsdfData.AtlasData,
    val metrics: MsdfData.MetricsData,
    val glyphs: Map<Int, MsdfGlyph>
) {
    fun applyGlyphs(builder: BufferBuilder, text: Component, size: Float, x: Float, y: Float, color: Color): BufferBuilder {
        var offsetX = x
        var offsetY = y

        text.visualOrderText.accept { _, style, code ->
            if (code == '\n'.code) {
                offsetX = x
                offsetY += FontUtility.getHeight(size) + 1f
                return@accept true
            }

            val glyph = glyphs[code]
            val current = if (style.isEmpty) {
                color
            } else {
                val rgb = style.color?.value ?: color.rgb
                Color((rgb shr 16) and 0xFF, (rgb shr 8) and 0xFF, rgb and 0xFF)
            }

            if (glyph == null) return@accept true

            offsetX += glyph.apply(builder, size, offsetX, offsetY, current) + FontUtility.getThickness(size)

            true
        }

        return builder
    }

    fun getWidth(text: Component, size: Float): Float {
        var width = 0f

        text.visualOrderText.accept { _, _, code ->
            val glyph = glyphs[code] ?: return@accept true
            width += (glyph.advance * size) + FontUtility.getThickness(size)
            true
        }

        return width
    }

    fun getWidth(text: String, size: Float): Float {
        return getWidth(Component.literal(text), size)
    }

    fun getHeight(size: Float): Float {
        return (metrics.lineHeight + metrics.descender - 0.2f) * size
    }
}