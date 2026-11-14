package cicadas.mixtape.renderer.testmod.api

import net.minecraft.client.Minecraft

internal inline val mc get() = Minecraft.getInstance()
internal inline val level get() = mc.level!!
internal inline val mainTarget get() = mc.mainRenderTarget