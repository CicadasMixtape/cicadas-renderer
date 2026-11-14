package cicadas.mixtape.renderer.context

import cicadas.mixtape.renderer.font.Font
import cicadas.mixtape.renderer.objects.Gradient
import cicadas.mixtape.renderer.objects.Radius
import cicadas.mixtape.renderer.objects.Squirt
import cicadas.mixtape.renderer.shader.projection.Projection
import cicadas.mixtape.renderer.svg.SVGTexture
import cicadas.mixtape.renderer.utility.render.Renderer2D
import com.github.weisj.jsvg.view.FloatSize
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.MouseHandler
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.joml.Quaternionf
import java.awt.Color

open class UIContext protected constructor(
    protected val stack: PoseStack,
    protected val projection: Projection,
    private val handler: MouseHandler?,
    private val inLevel: () -> Boolean
) {
    companion object {
        @JvmStatic
        fun create(stack: PoseStack, projection: Projection, handler: MouseHandler?, inLevel: () -> Boolean) = UIContext(stack, projection, handler, inLevel)
    }

    fun push() = stack.pushPose()

    fun pop() = stack.popPose()

    fun move(x: Float, y: Float, z: Float = 0f) = stack.translate(x, y, z)

    fun scale(x: Float, y: Float, z: Float = 1f) = stack.scale(x, y, z)

    fun rotate(quaternion: Quaternionf) = stack.mulPose(quaternion)

    fun centeredScale(scale: Float, x: Float, y: Float) {
        move(x, y)
        scale(scale, scale)
        move(-x, -y)
    }

    fun centeredRotate(quaternion: Quaternionf, x: Float, y: Float) {
        move(x, y)
        rotate(quaternion)
        move(-x, -y)
    }

    fun mouseX() = if (handler == null) 0f else handler.xpos().toFloat() / 2f

    fun mouseY() = if (handler == null) 0f else handler.ypos().toFloat() / 2f

    fun level() = inLevel()

    fun backupColor() = Renderer2D.backupColor()

    fun resetColor() = Renderer2D.resetColor()

    fun setColor(color: Color) = Renderer2D.setColor(color)

    fun stack() = stack

    fun matrix4f() = stack.last().pose()

    fun projection() = projection.uniform()

    fun drawRect(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient
    ) {
        Renderer2D.drawRect(this, x, y, width, height, gradient)
    }

    fun drawRoundedRect(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient
    ) {
        Renderer2D.drawRoundedRect(this, x, y, width, height, radius, gradient)
    }

    fun drawShadow(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        power: Float,
        radius: Radius,
        gradient: Gradient
    ) {
        Renderer2D.drawShadow(this, x, y, width, height, power, radius, gradient)
    }

    fun drawTexture(
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawTexture(this, texture, x, y, width, height, gradient)
    }

    fun drawTexture(
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawTexture(this, id, x, y, width, height, gradient)
    }

    fun drawRoundedTexture(
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawRoundedTexture(this, texture, x, y, width, height, radius, gradient)
    }

    fun drawRoundedTexture(
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawRoundedTexture(this, id, x, y, width, height, radius, gradient)
    }

    fun drawSquircle(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        squirt: Squirt,
        radius: Radius,
        gradient: Gradient
    ) {
        Renderer2D.drawSquircle(this, x, y, width, height, squirt, radius, gradient)
    }

    fun drawRoundedBlur(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawRoundedBlur(this, x, y, width, height, radius, gradient)
    }

    fun drawLiquidGlass(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        power: Float,
        clean: Boolean,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawLiquidGlass(this, x, y, width, height, power, clean, radius, gradient)
    }

    fun drawText(
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        Renderer2D.drawText(this, font, text, x, y, color)
    }

    fun drawText(
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        Renderer2D.drawText(this, font, text, x, y, color)
    }

    fun drawCenteredText(
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        Renderer2D.drawCenteredText(this, font, text, x, y, color)
    }

    fun drawCenteredText(
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        Renderer2D.drawCenteredText(this, font, text, x, y, color)
    }

    fun drawRightText(
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        Renderer2D.drawRightText(this, font, text, x, y, color)
    }

    fun drawRightText(
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        Renderer2D.drawRightText(this, font, text, x, y, color)
    }

    fun drawVector(
        texture: SVGTexture,
        x: Float,
        y: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawVector(this, texture, x, y, gradient)
    }

    fun drawVector(
        id: Identifier,
        size: FloatSize,
        x: Float,
        y: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        Renderer2D.drawVector(this, id, size, x, y, gradient)
    }

    open fun update() {
        Renderer2D.update()
    }
}