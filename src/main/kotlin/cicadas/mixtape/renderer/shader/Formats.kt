package cicadas.mixtape.renderer.shader

import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormatElement

object Formats {
    @JvmField val SIZE = VertexFormatElement.register(8, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 2)
    @JvmField val RADIUS = VertexFormatElement.register(9, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 1)

    @JvmField
    val RECT = VertexFormat.builder()
        .add("Position", VertexFormatElement.POSITION)
        .add("Size", SIZE)
        .add("Radius", RADIUS)
        .add("Color", VertexFormatElement.COLOR)
        .build()
}