package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.lerpedAABB
import cicadas.mixtape.rendertest.api.level
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.nullCheck
import cicadas.mixtape.rendertest.api.blockState
import cicadas.mixtape.rendertest.api.withAlpha
import cicadas.mixtape.rendertest.font.Fonts
import cicadas.mixtape.rendertest.objects.DefaultBox
import cicadas.mixtape.rendertest.objects.DefaultOutline
import cicadas.mixtape.rendertest.objects.Gradient
import cicadas.mixtape.rendertest.objects.Radius
import cicadas.mixtape.rendertest.objects.VoxelBox
import cicadas.mixtape.rendertest.objects.VoxelOutline
import cicadas.mixtape.rendertest.svg.SVGManager
import cicadas.mixtape.rendertest.utility.render.RenderUtility
import cicadas.mixtape.rendertest.utility.render.Renderer2D
import cicadas.mixtape.rendertest.utility.render.Renderer3D
import com.github.weisj.jsvg.view.FloatSize
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.LevelLoadingScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.boss.enderdragon.EndCrystal
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import java.awt.Color

object RenderManager {
    private val COLOR = Color(255, 100, 100)
    private val RADIUS = Radius(15f, 25f, 10f, 25f)
    private val GRADIENT = Gradient.Vertical(Color.WHITE, COLOR)

    fun onRender2D() {
        if (nullCheck || mc.screen is LevelLoadingScreen || mc.debugEntries.isOverlayVisible || mc.options.hideGui) {
            return
        }

        RenderUtility.projection {
            var x = 25f
            Renderer2D.drawRoundedRect(x, 25f, 100f, 100f, RADIUS, GRADIENT)

            x += 125f
            Renderer2D.drawSquircle(x, 25f, 100f, 100f, 5f, RADIUS, GRADIENT)

            x += 125f
            Renderer2D.drawRoundedTexture(RenderTest.id("textures/kot.png"), x, 25f, 100f, 100f, RADIUS)

            x += 125f
            Renderer2D.drawRoundedBlur(x, 25f, 100f, 100f, RADIUS)

            x += 125f
            Renderer2D.drawLiquidGlass(x, 25f, 100f, 100f, 15f, true, RADIUS)

            x += 125f
            Renderer2D.drawLiquidGlass(x, 25f, 100f, 100f, 15f, false, RADIUS)

            val size1 = FloatSize(100f, 100f)
            val svg1 = SVGManager.getTexture(RenderTest.id("vector/cloud.svg"), size1)
            x += 125f
            Renderer2D.drawRoundedTexture(svg1, x, 25f, size1.width, size1.height)

            val size2 = FloatSize(32f, 32f)
            val svg2 = SVGManager.getTexture(RenderTest.id("vector/cloud.svg"), size2)
            x += 125f
            Renderer2D.drawRoundedTexture(svg2, x, 75f - size2.height / 2f, size2.width, size2.height)

            val text = testText()
            val font1 = Fonts.BOLD.getFont(20f)
            val font2 = Fonts.ROUNDED_BOLD.getFont(20f)

            Renderer2D.drawText(font1, text, 25f, 150f + font1.getHeight())
            Renderer2D.drawText(font2, text, 150f, 150f + font2.getHeight())
        }
    }

    fun onRender3D() {
        val result = mc.hitResult ?: return

        when (result) {
            is BlockHitResult -> {
                val pos = result.blockPos
                val state = pos.blockState
                val shape = state.getShape(level, pos)

                if (state.isAir) {
                    return
                }

                Renderer3D.addVoxelBox(VoxelBox(pos, shape, COLOR.withAlpha(100)))
                Renderer3D.addVoxelOutline(VoxelOutline(pos, shape, COLOR, 3f))
            }

            is EntityHitResult -> {
                val entity = result.entity

                if (entity is EndCrystal) {
                    return
                }

                val aabb = entity.lerpedAABB
                Renderer3D.addDefaultBox(DefaultBox(aabb, COLOR.withAlpha(100)))
                Renderer3D.addDefaultOutline(DefaultOutline(aabb, COLOR, 3f))
            }
        }
    }

    fun testText(): Component {
        val test = Component.literal("White\n").withStyle(ChatFormatting.WHITE)
        val red = Component.literal("Red\n").withStyle(ChatFormatting.RED)
        val green = Component.literal("Green\n").withStyle(ChatFormatting.GREEN)
        val blue = Component.literal("Blue").withStyle(ChatFormatting.BLUE)
        return test.append(red).append(green).append(blue)
    }
}