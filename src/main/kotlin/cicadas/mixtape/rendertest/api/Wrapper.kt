package cicadas.mixtape.rendertest.api

import cicadas.mixtape.rendertest.ducks.LevelRendererDuck
import kotlinx.serialization.json.Json
import net.minecraft.client.Minecraft

@JvmField
val JSON = Json { ignoreUnknownKeys = true }

inline val mc get() = Minecraft.getInstance()
inline val player get() = mc.player!!
inline val level get() = mc.level!!
inline val nullCheck get() = mc.player == null || mc.level == null

inline val window get() = mc.window
inline val mainTarget get() = mc.mainRenderTarget
inline val frustum get() = (mc.levelRenderer as LevelRendererDuck).`rendertest$getFrustum`()!!