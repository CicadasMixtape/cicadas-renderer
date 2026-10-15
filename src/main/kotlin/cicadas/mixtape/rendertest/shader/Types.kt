package cicadas.mixtape.rendertest.shader

import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType

object Types {
    @JvmField
    val QUADS = RenderType.create("rendertest-quads", RenderSetup.builder(Pipelines.QUAD_PIPELINE).createRenderSetup())

    @JvmField
    val LINES = RenderType.create("rendertest-lines", RenderSetup.builder(Pipelines.LINES_PIPELINE).createRenderSetup())
}