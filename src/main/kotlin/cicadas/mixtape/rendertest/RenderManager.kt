package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.lerpedAABB
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.nullCheck
import cicadas.mixtape.rendertest.api.state
import cicadas.mixtape.rendertest.api.withAlpha
import cicadas.mixtape.rendertest.blur.BlurPass
import cicadas.mixtape.rendertest.fonts.Fonts
import cicadas.mixtape.rendertest.utility.RenderUtility
import cicadas.mixtape.rendertest.utility.Renderer2D
import cicadas.mixtape.rendertest.utility.Renderer3D
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.LevelLoadingScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.AABB
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

        BlurPass.update()

        RenderUtility.projection {
            Renderer2D.drawRoundedRect(25f, 25f, 100f, 100f, 20f, COLOR)
            Renderer2D.drawRoundedTexture(RenderTest.id("textures/kot.png"), 150f, 25f, 100f, 100f, 20f, Color.WHITE)
            Renderer2D.drawRoundedBlur(275f, 25f, 100f, 100f, 20f, Color.WHITE)

            val text = testText()

            val font1 = Fonts.BOLD.getFont(20f)
            Renderer2D.drawFont(font1, text, 400f, 25f + font1.getHeight(), Color.WHITE)

            val font2 = Fonts.ROUNDED_BOLD.getFont(20f)
            Renderer2D.drawFont(font2, text, 500f, 25f + font2.getHeight(), Color.WHITE)
        }
    }

    fun onRender3D(stack: PoseStack) {
        val result = mc.hitResult ?: return
        val aabb = when(result) {
            is BlockHitResult -> if (result.blockPos.state.isAir) return else AABB(result.blockPos)
            is EntityHitResult -> result.entity.lerpedAABB
            else -> return
        }

        Renderer3D.drawBox(aabb, stack.last().pose(), COLOR.withAlpha(100))
        Renderer3D.drawOutline(aabb, stack.last().pose(), 3f, COLOR)
    }

    fun testText(): Component {
        val test = Component.literal("Test\n").withStyle(ChatFormatting.WHITE)
        val red = Component.literal("Red\n").withStyle(ChatFormatting.RED)
        val green = Component.literal("Green\n").withStyle(ChatFormatting.GREEN)
        val blue = Component.literal("Blue").withStyle(ChatFormatting.BLUE)
        return test.append(red).append(green).append(blue)
    }
}