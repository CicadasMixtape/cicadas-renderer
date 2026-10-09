package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.nullCheck
import cicadas.mixtape.rendertest.utility.RenderUtility
import cicadas.mixtape.rendertest.utility.Renderer2D
import net.minecraft.client.gui.screens.LevelLoadingScreen
import java.awt.Color

object RenderManager {
    fun onRender() {
        if (nullCheck || mc.screen is LevelLoadingScreen || mc.debugEntries.isOverlayVisible || mc.options.hideGui) {
            return
        }

        RenderUtility.projection {
            Renderer2D.drawRoundedRect(25f, 25f, 100f, 100f, 20f, Color(255, 100, 100))
            Renderer2D.drawRoundedTexture(RenderTest.id("textures/kot.png"), 150f, 25f, 100f, 100f, 20f, Color.WHITE)
        }
    }
}