package cicadas.mixtape.rendertest.shader.builder

import com.mojang.blaze3d.vertex.VertexFormat

data class CustomFormat(
    val mode: VertexFormat.Mode,
    val format: VertexFormat
)