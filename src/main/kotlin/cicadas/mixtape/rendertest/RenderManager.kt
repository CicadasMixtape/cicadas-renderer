package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.nullCheck
import cicadas.mixtape.rendertest.utility.FontUtility
import cicadas.mixtape.rendertest.utility.RenderUtility
import cicadas.mixtape.rendertest.utility.Renderer2D
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.LevelLoadingScreen
import net.minecraft.network.chat.Component
import java.awt.Color

object RenderManager {
    fun onRender() {
        if (nullCheck || mc.screen is LevelLoadingScreen || mc.debugEntries.isOverlayVisible || mc.options.hideGui) {
            return
        }

        RenderUtility.projection {
            Renderer2D.drawRoundedRect(25f, 25f, 100f, 100f, 20f, Color(255, 100, 100))
            Renderer2D.drawRoundedTexture(RenderTest.id("textures/kot.png"), 150f, 25f, 100f, 100f, 20f, Color.WHITE)

            val test = Component.literal("Test\n").withStyle(ChatFormatting.WHITE)
            val red = Component.literal("Red\n").withStyle(ChatFormatting.RED)
            val green = Component.literal("Green\n").withStyle(ChatFormatting.GREEN)
            val blue = Component.literal("Blue\n").withStyle(ChatFormatting.BLUE)

            Renderer2D.drawFont(test.append(red).append(green).append(blue), 275f, 25f + FontUtility.getHeight(10f), 10f, Color.WHITE)
        }
    }
}