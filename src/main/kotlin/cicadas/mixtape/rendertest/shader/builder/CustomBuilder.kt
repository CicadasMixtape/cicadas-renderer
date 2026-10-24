package cicadas.mixtape.rendertest.shader.builder

import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.ByteBufferBuilder

class CustomBuilder(val format: CustomFormat) {
    val bytes = ByteBufferBuilder(1 shl 16)
    var builder: BufferBuilder? = null
    var buffer: CustomBuffer? = null
    var count = 0

    fun get() = builder ?: BufferBuilder(bytes, format.mode, format.format).also { builder = it }
}