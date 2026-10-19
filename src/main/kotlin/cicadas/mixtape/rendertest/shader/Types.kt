package cicadas.mixtape.rendertest.shader

import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier

object Types {
    @JvmField
    val QUADS = RenderType.create("rendertest-quads", RenderSetup.builder(Pipelines.QUAD_PIPELINE).createRenderSetup())

    @JvmField
    val LINES = RenderType.create("rendertest-lines", RenderSetup.builder(Pipelines.LINES_PIPELINE).createRenderSetup())

    @JvmStatic
    fun entities(id: Identifier) = RenderType.create("rendertest-entities", RenderSetup.builder(Pipelines.OUTLINE_PIPELINE).withTexture("Sampler0", id).createRenderSetup())
}