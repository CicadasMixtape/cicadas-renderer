package cicadas.mixtape.rendertest.api

import kotlinx.serialization.json.Json
import net.minecraft.client.Minecraft

@JvmField
val JSON = Json { ignoreUnknownKeys = true }

inline val mc get() = Minecraft.getInstance()
inline val player get() = mc.player!!
inline val window get() = mc.window
inline val nullCheck get() = mc.player == null || mc.level == null