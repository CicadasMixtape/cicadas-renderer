package cicadas.mixtape.renderer.shader.uniform.api

import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.renderpearl.api.buffers.GpuBuffer
import net.minecraft.client.renderer.DynamicGpuDataStorage
import net.minecraft.client.renderer.DynamicGpuDataStorageMapped

abstract class CustomUniform<T : DynamicGpuDataStorage.DynamicGpuData>(
    private val name: String,
    size: Int
) {
    companion object {
        @JvmField
        val STORAGES = arrayListOf<DynamicGpuDataStorage<*>>()

        @JvmStatic
        fun endAll() = STORAGES.forEach { it.endFrame() }
    }

    private val storage = DynamicGpuDataStorageMapped<T>(
        name,
        size,
        GpuBuffer.USAGE_UNIFORM,
        16
    )

    init {
        STORAGES.add(storage)
    }

    fun update(data: T) = CustomSlice(name, storage.writeData(data))
}