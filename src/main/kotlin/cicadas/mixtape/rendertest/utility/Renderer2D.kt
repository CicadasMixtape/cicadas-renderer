package cicadas.mixtape.rendertest.utility

import cicadas.mixtape.rendertest.shader.buffer.CustomBuffer
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.uniform.RectUniform
import cicadas.mixtape.rendertest.api.addVertex
import cicadas.mixtape.rendertest.api.defaultRenderPass
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.shader.uniform.FontUniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import java.awt.Color

object Renderer2D {
    fun drawRoundedRect(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color) {
        val builder = RenderUtility.defaultBegin()

        builder.addVertex(x,         y + height, 0f, 1f, width, height, color.rgb)
        builder.addVertex(x + width, y + height, 1f, 1f, width, height, color.rgb)
        builder.addVertex(x + width, y,          1f, 0f, width, height, -1)
        builder.addVertex(x,         y,          0f, 0f, width, height, -1)

        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val rect = RectUniform.update(radius)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.RECT_PIPELINE) {
            it.setUniform("RectData", rect)
            it.setVertexBuffer(0, buffer.vertex())
            it.setIndexBuffer(buffer.index(), buffer.type())
            it.drawIndexed(0, 0, buffer.count(), 1)
        }

        data.close()
    }

    fun drawRoundedTexture(id: Identifier, x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color) {
        val texture = mc.textureManager.getTexture(id)
        val builder = RenderUtility.defaultBegin()

        builder.addVertex(x,         y + height, 0f, 1f, width, height, color.rgb)
        builder.addVertex(x + width, y + height, 1f, 1f, width, height, color.rgb)
        builder.addVertex(x + width, y,          1f, 0f, width, height, color.rgb)
        builder.addVertex(x,         y,          0f, 0f, width, height, color.rgb)

        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val rectData = RectUniform.update(radius)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.TEXTURE_PIPELINE) {
            it.bindTexture("sampler0", texture.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
            it.setUniform("RectData", rectData)
            it.setVertexBuffer(0, buffer.vertex())
            it.setIndexBuffer(buffer.index(), buffer.type())
            it.drawIndexed(0, 0, buffer.count(), 1)
        }

        data.close()
    }

    fun drawFont(text: Component, x: Float, y: Float, size: Float, color: Color) {
        val font = FontUtility.FONT
        val builder = font.applyGlyphs(RenderUtility.defaultBegin(), text, size, x, y,  color)
        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val fontData = FontUniform.update(font.atlas.distanceRange, FontUtility.THICKNESS)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.MSDF_PIPELINE) {
            it.bindTexture("sampler0", font.texture.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
            it.setUniform("FontData", fontData)
            it.setVertexBuffer(0, buffer.vertex())
            it.setIndexBuffer(buffer.index(), buffer.type())
            it.drawIndexed(0, 0, buffer.count(), 1)
        }

        data.close()
    }
}