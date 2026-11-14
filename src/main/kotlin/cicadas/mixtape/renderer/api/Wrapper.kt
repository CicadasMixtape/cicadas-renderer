package cicadas.mixtape.renderer.api

import cicadas.mixtape.renderer.ducks.LevelExtractorDuck
import net.minecraft.client.Minecraft

internal inline val mc get() = Minecraft.getInstance()
internal inline val window get() = mc.window
internal inline val mainTarget get() = mc.gameRenderer.mainRenderTarget()
internal inline val frustum get() = (mc.levelRenderer as LevelExtractorDuck).`cicadas$getFrustum`()!!