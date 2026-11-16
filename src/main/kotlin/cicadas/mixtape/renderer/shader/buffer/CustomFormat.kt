package cicadas.mixtape.renderer.shader.buffer

import com.mojang.blaze3d.vertex.VertexFormat

data class CustomFormat(
    val mode: VertexFormat.Mode,
    val format: VertexFormat
) {
    fun indexCount(vertexCount: Int) = when(mode) {
        VertexFormat.Mode.QUADS -> vertexCount / 4 * 6
        VertexFormat.Mode.LINES -> vertexCount / 2 * 6
        else -> vertexCount
    }
}