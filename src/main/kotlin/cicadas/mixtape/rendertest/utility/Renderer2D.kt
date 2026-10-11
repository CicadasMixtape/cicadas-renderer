package cicadas.mixtape.rendertest.utility

import cicadas.mixtape.rendertest.shader.buffer.CustomBuffer
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.uniform.RectUniform
import cicadas.mixtape.rendertest.api.addVertex
import cicadas.mixtape.rendertest.api.defaultRenderPass
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.screenSize
import cicadas.mixtape.rendertest.blur.BlurPass
import cicadas.mixtape.rendertest.fonts.Font
import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.shader.uniform.FontUniform
import cicadas.mixtape.rendertest.shader.uniform.ScreenUniform
import cicadas.mixtape.rendertest.shader.uniform.SquircleUniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import java.awt.Color

object Renderer2D {
    fun drawRoundedRect(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color) {
        val builder = RenderUtility.defaultBegin(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, color.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, color.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, -1)
        builder.addVertex(x,         y,          width, height, 0f, 0f, -1)

        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val rectData = RectUniform.update(radius)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.RECT_PIPELINE) {
            it.setUniform("RectData", rectData)
            buffer.drawIndexed(it)
        }

        data.close()
    }

    fun drawRoundedBlur(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color) {
        val builder = RenderUtility.defaultBegin(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, color.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, color.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, -1)
        builder.addVertex(x,         y,          width, height, 0f, 0f, -1)

        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val rectData = RectUniform.update(radius)
        val screenData = ScreenUniform.update(mc.mainRenderTarget.screenSize)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.BLUR_RECT_PIPELINE) {
            it.bindTexture("sampler0", BlurPass.texture(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
            it.setUniform("RectData", rectData)
            it.setUniform("ScreenData", screenData)
            buffer.drawIndexed(it)
        }

        data.close()
    }

    fun drawSquircle(x: Float, y: Float, width: Float, height: Float, radius: Float, squirt: Float, color: Color) {
        val builder = RenderUtility.defaultBegin(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, color.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, color.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, -1)
        builder.addVertex(x,         y,          width, height, 0f, 0f, -1)

        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val rectData = RectUniform.update(radius)
        val squircleData = SquircleUniform.update(squirt)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.SQUIRCLE_PIPELINE) {
            it.setUniform("RectData", rectData)
            it.setUniform("SquircleData", squircleData)
            buffer.drawIndexed(it)
        }

        data.close()
    }

    fun drawRoundedTexture(id: Identifier, x: Float, y: Float, width: Float, height: Float, radius: Float, color: Color) {
        val texture = mc.textureManager.getTexture(id)
        val builder = RenderUtility.defaultBegin(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, color.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, color.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, color.rgb)
        builder.addVertex(x,         y,          width, height, 0f, 0f, color.rgb)

        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val rectData = RectUniform.update(radius)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.TEXTURE_PIPELINE) {
            it.bindTexture("sampler0", texture.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))
            it.setUniform("RectData", rectData)
            buffer.drawIndexed(it)
        }

        data.close()
    }

    fun drawText(font: Font, text: Component, x: Float, y: Float, color: Color) {
        val msdf = font.msdf
        val size = font.size

        val builder = msdf.applyGlyphs(RenderUtility.defaultBegin(DefaultVertexFormat.POSITION_TEX_COLOR), text, size, x, y,  color)
        val data = builder.buildOrThrow()
        val buffer = CustomBuffer.create(data)
        val fontData = FontUniform.update(msdf.atlas.distanceRange, msdf.thickness)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.MSDF_PIPELINE) {
            it.bindTexture("sampler0", msdf.texture.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))
            it.setUniform("FontData", fontData)
            buffer.drawIndexed(it)
        }

        data.close()
    }
}