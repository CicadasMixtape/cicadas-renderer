package cicadas.mixtape.renderer.api

import cicadas.mixtape.renderer.ducks.LevelRendererDuck
import net.minecraft.client.Minecraft

inline val mc get() = Minecraft.getInstance()
inline val level get() = mc.level!!
inline val window get() = mc.window
inline val mainTarget get() = mc.mainRenderTarget
inline val frustum get() = (mc.levelRenderer as LevelRendererDuck).`cicadas$getFrustum`()!!