package cicadas.mixtape.rendertest

import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormatElement

object Formats {
    @JvmField
    val COORD = VertexFormatElement.register(8, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 2)

    @JvmField
    val SIZE: VertexFormatElement = VertexFormatElement.register(9, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 2)

    @JvmField
    val RECT = VertexFormat.builder()
        .add("Position", VertexFormatElement.POSITION)
        .add("Color", VertexFormatElement.COLOR)
        .add("Coord", COORD)
        .add("Size", SIZE)
        .build()
}