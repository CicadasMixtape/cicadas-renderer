package cicadas.mixtape.rendertest.shader.builder

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.MeshData
import com.mojang.blaze3d.vertex.VertexFormat

class CustomBuffer private constructor(
    private val vertex: GpuBuffer,
    private val index: GpuBuffer,
    private val type: VertexFormat.IndexType,
    private val count: Int
) {
    companion object {
        fun create(mesh: MeshData): CustomBuffer {
            val state = mesh.drawState()
            val format = state.format
            val auto = RenderSystem.getSequentialBuffer(state.mode)

            return CustomBuffer(
                format.uploadImmediateVertexBuffer(mesh.vertexBuffer()),
                auto.getBuffer(state.indexCount),
                auto.type(),
                state.indexCount
            )
        }
    }

    fun setBuffer(pass: RenderPass) {
        pass.setVertexBuffer(0, vertex)
        pass.setIndexBuffer(index, type)
    }

    fun drawIndexed(pass: RenderPass) {
        drawIndexed(pass, 0, count)
    }

    fun drawIndexed(pass: RenderPass, start: Int, count: Int) {
        pass.drawIndexed(0, start, count, 1)
    }
}