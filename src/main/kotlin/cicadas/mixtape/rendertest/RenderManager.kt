package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.addVertex
import cicadas.mixtape.rendertest.api.defaultRenderPass
import cicadas.mixtape.rendertest.api.nullCheck
import cicadas.mixtape.rendertest.utility.RenderUtility
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import java.awt.Color

object RenderManager {
    val color = Color(255, 100, 100)

    fun onRender() {
        if (nullCheck) {
            return
        }

        RenderUtility.projection {
            rect(25f, 25f, 100f, 100f, 20f, color.rgb)
        }
    }

    fun rect(x: Float, y: Float, width: Float, height: Float, radius: Float, color: Int) {
        val builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, Formats.RECT)

        builder.addVertex(x,         y + height, 0f, 1f, width, height, color)
        builder.addVertex(x + width, y + height, 1f, 1f, width, height, color)
        builder.addVertex(x + width, y,          1f, 0f, width, height, -1)
        builder.addVertex(x,         y,          0f, 0f, width, height, -1)

        val mesh = builder.buildOrThrow()
        val state = mesh.drawState()
        val format = state.format

        val vertex = format.uploadImmediateIndexBuffer(mesh.vertexBuffer())
        val auto = RenderSystem.getSequentialBuffer(state.mode)
        val index = auto.getBuffer(state.indexCount)
        val rect = RectUniform.update(radius)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.RECT_PIPELINE) {
            it.setUniform("RectData", rect)
            it.setVertexBuffer(0, vertex)
            it.setIndexBuffer(index, auto.type())
            it.drawIndexed(0, 0, state.indexCount, 1)
        }

        mesh.close()
    }
}