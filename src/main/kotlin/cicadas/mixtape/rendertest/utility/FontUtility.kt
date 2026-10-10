package cicadas.mixtape.rendertest.utility

import cicadas.mixtape.rendertest.RenderTest
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.fonts.MsdfData
import cicadas.mixtape.rendertest.fonts.MsdfFont
import cicadas.mixtape.rendertest.fonts.MsdfGlyph
import kotlinx.serialization.json.Json
import net.minecraft.network.chat.Component

object FontUtility {
    @JvmField
    val FONT: MsdfFont
    const val THICKNESS = 0.05f

    val stream = RenderTest.stream("fonts/font.json")
    val texture = mc.textureManager.getTexture(RenderTest.id("fonts/font.png"))

    init {
        val json = Json { ignoreUnknownKeys = true }
        val data = json.decodeFromString<MsdfData>(stream.readAllBytes().decodeToString())
        val glyphs = data.glyphs.associate { it.unicode to MsdfGlyph(it, data.atlas.width, data.atlas.height) }
        FONT = MsdfFont(texture, data.atlas, data.metrics, glyphs)
    }

    fun getWidth(text: Component, size: Float) = FONT.getWidth(text, size)
    fun getWidth(text: String, size: Float) = FONT.getWidth(text, size)
    fun getHeight(size: Float) = FONT.getHeight(size)
    fun getThickness(size: Float) = THICKNESS * 0.5f * size
}