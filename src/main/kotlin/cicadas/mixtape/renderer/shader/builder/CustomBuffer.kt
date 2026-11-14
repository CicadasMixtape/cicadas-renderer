package cicadas.mixtape.renderer.shader.builder

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.MeshData
import com.mojang.renderpearl.api.buffers.GpuBuffer
import com.mojang.renderpearl.api.buffers.GpuBufferSlice
import com.mojang.renderpearl.api.commands.RenderPass
import com.mojang.renderpearl.api.pipeline.IndexType

class CustomBuffer private constructor(
    private val vertex: GpuBufferSlice,
    private val index: GpuBuffer,
    private val type: IndexType,
    private val count: Int
) {
    companion object {
        @JvmStatic
        fun create(mesh: MeshData): CustomBuffer {
            val state = mesh.drawState()
            val format = state.format
            val auto = RenderSystem.getSequentialBuffer(state.primitiveTopology)

            return CustomBuffer(
                auto.asdhyasuijdghasd(), // TODO пиздец
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

    fun drawIndexed(pass: RenderPass, start: Int, count: Int) {
        pass.drawIndexed(count, 1, start, 0, 0)
    }

    fun drawIndexed(pass: RenderPass) {
        drawIndexed(pass, 0, count)
    }
}