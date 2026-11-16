package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.shader.buffer.CustomBuffer
import cicadas.mixtape.renderer.shader.buffer.CustomFormat
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.ByteBufferBuilder

class BatchBuilder(val format: CustomFormat) {
    val bytes = ByteBufferBuilder(1 shl 16)
    var builder: BufferBuilder? = null
    var buffer: CustomBuffer? = null
    var count = 0

    fun builder() = builder ?: BufferBuilder(bytes, format.topology, format.format).also { builder = it }
}