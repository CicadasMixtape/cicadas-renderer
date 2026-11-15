package cicadas.mixtape.renderer.shader.storage.api

import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.renderpearl.api.buffers.GpuBuffer
import net.minecraft.client.renderer.DynamicGpuDataStorage
import net.minecraft.client.renderer.DynamicGpuDataStorageMapped

class UniformStorage<T : DynamicGpuDataStorage.DynamicGpuData>(
    private val name: String,
    size: Int
) {
    private fun align(value1: Int, value2: Int) = (value1 + value2 - 1) / value2 * value2

    private val storage = DynamicGpuDataStorageMapped<T>(
        name,
        align(size, RenderSystem.getDevice().deviceInfo.limits().minUniformOffsetAlignment()),
        GpuBuffer.USAGE_UNIFORM,
        16
    )

    companion object {
        private val STORAGES = arrayListOf<UniformStorage<*>>()

        fun endFrame() = STORAGES.forEach { it.endFrame() }
    }

    init {
        STORAGES.add(this)
    }

    fun write(data: T) = CustomSlice(name, storage.writeData(data))

    fun endFrame() = storage.endFrame()
}