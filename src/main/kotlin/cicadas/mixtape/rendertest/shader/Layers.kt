package cicadas.mixtape.rendertest.shader

import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

object Layers {
    @JvmStatic
    fun quads() = RenderType.create("rendertest-quads", RenderSetup.builder(Pipelines.QUAD_PIPELINE).createRenderSetup())

    @JvmStatic
    fun lines() = RenderType.create("rendertest-lines", RenderSetup.builder(Pipelines.LINES_PIPELINE).createRenderSetup())

    @JvmStatic
    fun hands(id: Identifier) = RenderType.create("rendertest-hands", RenderSetup.builder(Pipelines.HAND_PIPELINE).withTexture("sampler0", id).useOverlay().useLightmap().createRenderSetup())
}