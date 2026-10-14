package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.lerpedAABB
import cicadas.mixtape.rendertest.api.level
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.nullCheck
import cicadas.mixtape.rendertest.api.blockState
import cicadas.mixtape.rendertest.api.withAlpha
import cicadas.mixtape.rendertest.fonts.Fonts
import cicadas.mixtape.rendertest.svg.SVGManager
import cicadas.mixtape.rendertest.utility.RenderUtility
import cicadas.mixtape.rendertest.utility.Renderer2D
import cicadas.mixtape.rendertest.utility.Renderer3D
import com.github.weisj.jsvg.view.FloatSize
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.LevelLoadingScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import java.awt.Color

object RenderManager {
    @JvmField
    val COLOR = Color(255, 100, 100)

    fun onRender2D() {
        if (nullCheck || mc.screen is LevelLoadingScreen || mc.debugEntries.isOverlayVisible || mc.options.hideGui) {
            return
        }

        RenderUtility.projection {
            var x = 25f
            var y = 25f

            Renderer2D.drawRoundedRect(x, y, 100f, 100f, 20f, COLOR)

            x += 125f
            Renderer2D.drawSquircle(x, y, 100f, 100f, 20f, 5f, COLOR)

            x += 125f
            Renderer2D.drawRoundedTexture(RenderTest.id("textures/kot.png"), x, y, 100f, 100f, 20f, Color.WHITE)

            x += 125f
            Renderer2D.drawRoundedBlur(x, y, 100f, 100f, 20f, Color.WHITE)

            val size1 = FloatSize(100f, 100f)
            val svg1 = SVGManager.getTexture(RenderTest.id("vector/cloud.svg"), size1)
            x += 125f
            Renderer2D.drawRoundedTexture(svg1, x, y, size1.width, size1.height, 0f, Color.WHITE)

            val size2 = FloatSize(50f, 50f)
            val svg2 = SVGManager.getTexture(RenderTest.id("vector/cloud.svg"), size2)
            x += 25f
            y += 100f
            Renderer2D.drawRoundedTexture(svg2, x, y, size2.width, size2.height, 0f, Color.WHITE)

            val size3 = FloatSize(25f, 25f)
            val svg3 = SVGManager.getTexture(RenderTest.id("vector/cloud.svg"), size3)
            x += 12.5f
            y += 62.5f
            Renderer2D.drawRoundedTexture(svg3, x, y, size3.width, size3.height, 0f, Color.WHITE)

            val text = testText()
            val font1 = Fonts.BOLD.getFont(20f)
            val font2 = Fonts.ROUNDED_BOLD.getFont(20f)

            x = 25f
            y = 150f
            Renderer2D.drawText(font1, text, x, y + font1.getHeight(), Color.WHITE)

            x += 125f
            Renderer2D.drawText(font2, text, x, y + font2.getHeight(), Color.WHITE)
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

                Renderer3D.addVoxelBox(Renderer3D.VoxelBox(pos, shape, COLOR.withAlpha(100)))
                Renderer3D.addVoxelOutline(Renderer3D.VoxelOutline(pos, shape, COLOR, 3f))
            }

            is EntityHitResult -> {
                val aabb = result.entity.lerpedAABB
                Renderer3D.addDefaultBox(Renderer3D.DefaultBox(aabb, COLOR.withAlpha(100)))
                Renderer3D.addDefaultOutline(Renderer3D.DefaultOutline(aabb, COLOR, 3f))
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