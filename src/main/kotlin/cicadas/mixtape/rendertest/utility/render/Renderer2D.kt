package cicadas.mixtape.rendertest.utility.render

import cicadas.mixtape.rendertest.api.addVertex
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.api.mainTarget
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.screenSize
import cicadas.mixtape.rendertest.api.window
import cicadas.mixtape.rendertest.batching.Batched
import cicadas.mixtape.rendertest.batching.Batching
import cicadas.mixtape.rendertest.pass.KawasePass
import cicadas.mixtape.rendertest.font.Font
import cicadas.mixtape.rendertest.objects.Gradient
import cicadas.mixtape.rendertest.objects.Radius
import cicadas.mixtape.rendertest.shader.sampler.CustomSampler
import cicadas.mixtape.rendertest.shader.uniform.LiquidUniform
import cicadas.mixtape.rendertest.shader.uniform.MsdfUniform
import cicadas.mixtape.rendertest.shader.uniform.ScreenUniform
import cicadas.mixtape.rendertest.shader.uniform.SquircleUniform
import com.mojang.blaze3d.ProjectionType
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import java.awt.Color

object Renderer2D {
    private val batching = Batching()
    private val projection = CachedOrthoProjectionMatrixBuffer("rendertest-projection", -1000f, 1000f, true)

    fun drawRoundedRect(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient,
        stack: PoseStack = PoseStack()
    ) {
        batching.add(
            Batched.builder()
                .pipeline(Pipelines.RECT_PIPELINE)
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedTexture(id: Identifier, x: Float, y: Float, width: Float, height: Float, radius: Radius = Radius.EMPTY, gradient: Gradient = Gradient.EMPTY) {
        drawRoundedTexture(mc.textureManager.getTexture(id), x, y, width, height, radius, gradient)
    }

    fun drawRoundedTexture(
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY,
        stack: PoseStack = PoseStack()
    ) {
        batching.add(
            Batched.builder()
                .pipeline(Pipelines.TEXTURE_PIPELINE)
                .sampler(CustomSampler("Sampler0", texture.textureView, FilterMode.NEAREST))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)
                    1
                }
                .build()
        )
    }

    fun drawSquircle(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        squirt: Float,
        radius: Radius,
        gradient: Gradient,
        stack: PoseStack = PoseStack()
    ) {
        batching.add(
            Batched.builder()
                .pipeline(Pipelines.SQUIRCLE_PIPELINE)
                .slice(SquircleUniform.update(squirt))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedBlur(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY,
        stack: PoseStack = PoseStack()
    ) {
        batching.add(
            Batched.builder()
                .pipeline(Pipelines.BLUR_PIPELINE)
                .sampler(CustomSampler("Sampler0", KawasePass.texture(), FilterMode.LINEAR))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)
                    1
                }
                .build()
        )
    }

    fun drawLiquidGlass(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        power: Float,
        clean: Boolean,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY,
        stack: PoseStack = PoseStack()
    ) {
        batching.add(
            Batched.builder()
                .pipeline(Pipelines.LIQUID_GLASS)
                .sampler(CustomSampler("Sampler0", if (clean) mainTarget.colorTextureView else KawasePass.texture(), FilterMode.LINEAR))
                .slice(LiquidUniform.update(power))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)
                    1
                }
                .build()
        )
    }

    fun drawText(
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE,
        stack: PoseStack = PoseStack()
    ) {
        val msdf = font.msdf
        val size = font.size

        batching.add(
            Batched.builder()
                .pipeline(Pipelines.MSDF_PIPELINE)
                .sampler(CustomSampler("Sampler0", msdf.texture.textureView, FilterMode.LINEAR))
                .slice(MsdfUniform.update(msdf.atlas.distanceRange, msdf.thickness))
                .vertex { msdf.applyGlyphs(it, stack.last().pose(), text, size, x, y, color) }
                .build()
        )
    }

    fun projection(unit: () -> Unit) {
        val width = window.width.toFloat() / 2f
        val height = window.height.toFloat() / 2f
        val buffer = RenderSystem.getProjectionMatrixBuffer()!!
        val type = RenderSystem.getProjectionType()

        RenderSystem.setProjectionMatrix(projection.getBuffer(width, height), ProjectionType.ORTHOGRAPHIC)
        unit()
        RenderSystem.setProjectionMatrix(buffer, type)
    }

    fun update() {
        batching.update()
    }
}