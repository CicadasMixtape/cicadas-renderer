package cicadas.mixtape.renderer.shader

import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType

object CicadasTypes {
    @JvmField
    val QUADS = RenderType.create("cicadasrenderer-quads", RenderSetup.builder(CicadasPipelines.QUAD_PIPELINE).createRenderSetup())

    @JvmField
    val LINES = RenderType.create("cicadasrenderer-lines", RenderSetup.builder(CicadasPipelines.LINES_PIPELINE).createRenderSetup())
}