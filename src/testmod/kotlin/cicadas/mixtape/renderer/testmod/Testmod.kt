package cicadas.mixtape.renderer.testmod

import cicadas.mixtape.renderer.CicadasRenderer
import cicadas.mixtape.renderer.events.Render2DCallback
import cicadas.mixtape.renderer.events.Render3DCallback
import cicadas.mixtape.renderer.testmod.font.Fonts
import cicadas.mixtape.renderer.objects.DefaultBox
import cicadas.mixtape.renderer.objects.DefaultOutline
import cicadas.mixtape.renderer.objects.Gradient
import cicadas.mixtape.renderer.objects.Radius
import cicadas.mixtape.renderer.objects.Squirt
import cicadas.mixtape.renderer.context.UIContext
import cicadas.mixtape.renderer.objects.VoxelBox
import cicadas.mixtape.renderer.objects.VoxelOutline
import cicadas.mixtape.renderer.svg.SVGManager
import cicadas.mixtape.renderer.testmod.api.init
import cicadas.mixtape.renderer.testmod.api.level
import cicadas.mixtape.renderer.testmod.api.mc
import cicadas.mixtape.renderer.testmod.api.withAlpha
import cicadas.mixtape.renderer.utility.render.Renderer3D
import com.github.weisj.jsvg.view.FloatSize
import net.fabricmc.api.ModInitializer
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import java.awt.Color

object Testmod : ModInitializer {
    private val COLOR = Color(255, 100, 100)
    private val SQUIRT = Squirt(10f, 15f, 5f, 15f)
    private val RADIUS = Radius(15f, 25f, 10f, 25f)
    private val GRADIENT = Gradient.Vertical(Color.WHITE, COLOR)

    override fun onInitialize() {
        // Нужно, чтобы при первом кадре отрисовки не было пролага
        // Опционально
        CicadasRenderer.initialization { Fonts.init() }

        Render2DCallback.EVENT.register {
            onRender2D(it)
        }

        Render3DCallback.EVENT.register {
            onRender3D(it)
        }
    }

    private fun onRender2D(context: UIContext) {
        if (!context.inLevel()) {
            return
        }

        var x = 25f
        context.drawRoundedRect(x, 25f, 100f, 100f, RADIUS, GRADIENT)
        context.drawRect(x, 150f, 100f, 100f, GRADIENT)

        x += 125f
        context.drawSquircle(x, 25f, 100f, 100f, SQUIRT, RADIUS, GRADIENT)

        x += 125f
        context.drawRoundedTexture(id("textures/kot.png"), x, 25f, 100f, 100f, RADIUS)
        context.drawTexture(id("textures/kot.png"), x, 150f, 100f, 100f)

        x += 125f
        context.drawRoundedBlur(x, 25f, 100f, 100f, RADIUS)
        context.drawLiquidGlass(x, 150f, 100f, 100f, 15f, true, RADIUS)
        context.drawLiquidGlass(x, 275f, 100f, 100f, 15f, false, RADIUS)

        x += 125f
        val svg1 = SVGManager.getTexture(id("vector/cloud.svg"), FloatSize(100f, 100f))
        val svg2 = SVGManager.getTexture(id("vector/cloud.svg"), FloatSize(50f, 50f))

        context.drawVector(svg1, x, 25f)
        context.drawVector(svg2, x, 150f)

        val text = getText()
        val font1 = Fonts.BOLD.getFont(25f)
        val font2 = Fonts.ROUNDED_BOLD.getFont(25f)

        context.drawText(font1, text, 25f, 275f)
        context.drawText(font2, text, 150f, 275f)
    }

    private fun onRender3D(context: UIContext) {
        val result = mc.hitResult ?: return

        if (result.type == HitResult.Type.MISS) {
            return
        }

        when (result) {
            is BlockHitResult -> {
                val pos = result.blockPos
                val state = level.getBlockState(pos)
                val shape = state.getShape(level, pos)

                Renderer3D.addVoxelBox(VoxelBox(pos, shape, COLOR.withAlpha(100)))
                Renderer3D.addVoxelOutline(VoxelOutline(pos, shape, COLOR, 3f))
            }

            is EntityHitResult -> {
                val entity = result.entity
                val aabb = entity.boundingBox

                Renderer3D.addDefaultBox(DefaultBox(aabb, COLOR.withAlpha(100)))
                Renderer3D.addDefaultOutline(DefaultOutline(aabb, COLOR, 3f))
            }
        }
    }

    fun getText(): Component {
        val white = Component.literal("White\n").withStyle(ChatFormatting.WHITE)
        val red = Component.literal("Red\n").withStyle(ChatFormatting.RED)
        val green = Component.literal("Green\n").withStyle(ChatFormatting.GREEN)
        val blue = Component.literal("Blue").withStyle(ChatFormatting.BLUE)
        return white.append(red).append(green).append(blue)
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("testmod", string)
}