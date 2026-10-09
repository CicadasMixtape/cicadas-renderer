package cicadas.mixtape.rendertest.shader.buffer

import com.mojang.blaze3d.buffers.GpuBuffer
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
        fun create(data: MeshData): CustomBuffer {
            val state = data.drawState()
            val format = state.format
            val auto = RenderSystem.getSequentialBuffer(state.mode)

            return CustomBuffer(
                format.uploadImmediateVertexBuffer(data.vertexBuffer()),
                auto.getBuffer(state.indexCount),
                auto.type(),
                state.indexCount
            )
        }
    }

    fun vertex() = vertex

    fun index() = index

    fun type() = type

    fun count() = count
}