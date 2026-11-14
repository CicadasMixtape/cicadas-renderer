package cicadas.mixtape.renderer.utility.render

import cicadas.mixtape.renderer.api.addVertex
import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.mc
import cicadas.mixtape.renderer.api.mul
import cicadas.mixtape.renderer.api.screenSize
import cicadas.mixtape.renderer.batching.Batched
import cicadas.mixtape.renderer.batching.Batching
import cicadas.mixtape.renderer.context.UIContext
import cicadas.mixtape.renderer.font.Font
import cicadas.mixtape.renderer.objects.Gradient
import cicadas.mixtape.renderer.objects.Radius
import cicadas.mixtape.renderer.objects.Squirt
import cicadas.mixtape.renderer.pass.KawasePass
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.uniform.*
import cicadas.mixtape.renderer.svg.SVGManager
import cicadas.mixtape.renderer.svg.SVGTexture
import cicadas.mixtape.renderer.utility.color.ShaderColor
import cicadas.mixtape.renderer.utility.scissor.ScissorUtility
import com.github.weisj.jsvg.view.FloatSize
import com.mojang.blaze3d.textures.FilterMode
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import java.awt.Color

object Renderer2D {
    private val batching = Batching()
    private val shaderColor = ShaderColor()

    fun drawRect(
        context: UIContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.POSITION_COLOR_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .slice(context.projection())
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, 0f).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, 0f).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          0f).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          0f).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedRect(
        context: UIContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.RECT_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .slice(context.projection())
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawShadow(
        context: UIContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        power: Float,
        radius: Radius,
        gradient: Gradient
    ) {
        val x = x - power
        val y = y - power
        val width = width + (power * 2f)
        val height = height + (power * 2f)
        val radius = Radius(radius.leftBottomRadius + power, radius.leftTopRadius + power, radius.rightTopRadius + power, radius.rightBottomRadius + power)

        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.SHADOW_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .slice(context.projection())
                .slice(ShadowUniform.update(power))
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawTexture(
        context: UIContext,
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.POSITION_TEXTURE_COLOR_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .sampler(CustomSampler(0, texture.textureView, FilterMode.NEAREST))
                .slice(context.projection())
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, 0f).setUv(0f, 1f).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, 0f).setUv(1f, 1f).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          0f).setUv(1f, 0f).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          0f).setUv(0f, 0f).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawTexture(
        context: UIContext,
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawTexture(context, mc.textureManager.getTexture(id), x, y, width, height, gradient)
    }

    fun drawRoundedTexture(
        context: UIContext,
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.TEXTURE_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .sampler(CustomSampler(0, texture.textureView, FilterMode.NEAREST))
                .slice(context.projection())
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedTexture(
        context: UIContext,
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawRoundedTexture(context, mc.textureManager.getTexture(id), x, y, width, height, radius, gradient)
    }

    fun drawSquircle(
        context: UIContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        squirt: Squirt,
        radius: Radius,
        gradient: Gradient
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.SQUIRCLE_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .slice(context.projection())
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, width, height, radius.leftBottomRadius,  squirt.leftBottomSquirt).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, width, height, radius.rightBottomRadius, squirt.rightBottomSquirt).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          width, height, radius.rightTopRadius,    squirt.rightTopSquirt).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          width, height, radius.leftTopRadius,     squirt.leftTopSquirt).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedBlur(
        context: UIContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.BLUR_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .sampler(CustomSampler(0, KawasePass.texture(), FilterMode.LINEAR))
                .slice(context.projection())
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawLiquidGlass(
        context: UIContext,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        power: Float,
        clean: Boolean,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.LIQUID_GLASS)
                .scissorState(ScissorUtility.copyState())
                .sampler(CustomSampler(0, if (clean) mainTarget.colorTextureView else KawasePass.texture(), FilterMode.LINEAR))
                .slice(context.projection())
                .slice(LiquidUniform.update(power))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(context.matrix4f(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor.current()).rgb)
                    it.addVertex(context.matrix4f(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor.current()).rgb)
                    1
                }
                .build()
        )
    }

    fun drawText(
        context: UIContext,
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        batching.add(
            Batched.builder()
                .matrix4f(context.matrix4f())
                .pipeline(CicadasPipelines.MSDF_PIPELINE)
                .scissorState(ScissorUtility.copyState())
                .sampler(CustomSampler(0, font.textureView(), FilterMode.LINEAR))
                .slice(context.projection())
                .slice(font.uniform())
                .vertex { font.glyphs(it, context.matrix4f(), text, x, y, color.mul(shaderColor.current())) }
                .build()
        )
    }

    fun drawText(
        context: UIContext,
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(context, font, Component.literal(text), x, y, color)
    }

    fun drawCenteredText(
        context: UIContext,
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(context, font, text, x - font.getWidth(text) / 2f, y, color)
    }

    fun drawCenteredText(
        context: UIContext,
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(context, font, text, x - font.getWidth(text) / 2f, y, color)
    }

    fun drawRightText(
        context: UIContext,
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(context, font, text, x - font.getWidth(text), y, color)
    }

    fun drawRightText(
        context: UIContext,
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(context, font, text, x - font.getWidth(text), y, color)
    }

    fun drawVector(
        context: UIContext,
        texture: SVGTexture,
        x: Float,
        y: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawTexture(context, texture, x, y, texture.width(), texture.height(), gradient)
    }

    fun drawVector(
        context: UIContext,
        id: Identifier,
        size: FloatSize,
        x: Float,
        y: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawVector(context, SVGManager.getTexture(id, size), x, y, gradient)
    }

    fun backupColor() {
        shaderColor.backup()
    }

    fun resetColor() {
        shaderColor.reset()
    }

    fun setColor(color: Color) {
        shaderColor.set(color)
    }

    fun update() {
        batching.update()
        UniformStorage.endFrame()
    }
}