package cicadas.mixtape.renderer.shader

import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType

object Types {
    @JvmField
    val QUADS = RenderType.create("cicadasrenderer-quads", RenderSetup.builder(Pipelines.QUAD_PIPELINE).createRenderSetup())

    @JvmField
    val LINES = RenderType.create("cicadasrenderer-lines", RenderSetup.builder(Pipelines.LINES_PIPELINE).createRenderSetup())
}