package cicadas.mixtape.rendertest.font

import cicadas.mixtape.rendertest.api.JSON
import cicadas.mixtape.rendertest.api.beginQuads
import cicadas.mixtape.rendertest.api.mc
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
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
            val id = id.withPrefix("fonts/")
            val json = mc.resourceManager.open(id.withSuffix(".json"))
            val texture = mc.textureManager.getTexture(id.withSuffix(".png"))
            val data = JSON.decodeFromString<MsdfData>(json.readAllBytes().decodeToString())
            val glyphs = data.glyphs.associate { it.unicode to MsdfGlyph(it, data.atlas.width, data.atlas.height) }
            return MsdfFont(texture, data.atlas, data.metrics, glyphs)
        }
    }

    fun applyGlyphs(text: Component, size: Float, x: Float, y: Float, color: Color): BufferBuilder {
        val builder = Tesselator.getInstance().beginQuads(DefaultVertexFormat.POSITION_TEX_COLOR)
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

            offsetX += glyph.apply(builder, size, offsetX, offsetY, current) + getThickness(size)

            true
        }

        return builder
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