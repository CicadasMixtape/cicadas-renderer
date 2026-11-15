package cicadas.mixtape.renderer.shader.builder

import com.mojang.renderpearl.api.pipeline.PrimitiveTopology
import com.mojang.renderpearl.api.vertex.VertexFormat

data class CustomFormat(
    val topology: PrimitiveTopology,
    val format: VertexFormat
) {
    fun indexCount(vertexCount: Int) = when(topology) {
        PrimitiveTopology.QUADS -> vertexCount / 4 * 6
        PrimitiveTopology.LINES -> vertexCount / 2 * 6
        else -> vertexCount
    }
}