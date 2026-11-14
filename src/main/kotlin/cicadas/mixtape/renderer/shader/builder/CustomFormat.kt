package cicadas.mixtape.renderer.shader.builder

import com.mojang.renderpearl.api.pipeline.PrimitiveTopology
import com.mojang.renderpearl.api.vertex.VertexFormat

data class CustomFormat(
    val topology: PrimitiveTopology,
    val format: VertexFormat
)