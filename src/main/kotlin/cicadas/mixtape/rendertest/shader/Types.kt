package cicadas.mixtape.rendertest.shader

import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

object Types {
    @JvmStatic
    fun quads() = RenderType.create("rendertest-quads", RenderSetup.builder(Pipelines.QUAD_PIPELINE).createRenderSetup())

    @JvmStatic
    fun lines() = RenderType.create("rendertest-lines", RenderSetup.builder(Pipelines.LINES_PIPELINE).createRenderSetup())

    @JvmStatic
    fun hands(id: Identifier) = RenderType.create("rendertest-hands", RenderSetup.builder(Pipelines.HAND_PIPELINE).withTexture("sampler0", id).useOverlay().createRenderSetup())

    @JvmStatic
    fun items(id: Identifier) = RenderType.create("rendertest-items", RenderSetup.builder(Pipelines.ITEM_PIPELINE).withTexture("sampler0", id).useOverlay().createRenderSetup())
}