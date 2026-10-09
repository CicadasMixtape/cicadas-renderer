package cicadas.mixtape.rendertest.api

import net.minecraft.client.Minecraft

inline val mc get() = Minecraft.getInstance()
inline val window get() = mc.window
inline val nullCheck get() = mc.player == null || mc.level == null