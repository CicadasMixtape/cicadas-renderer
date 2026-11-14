package cicadas.mixtape.renderer.shader.storage.api

import cicadas.mixtape.renderer.shader.slice.CustomSlice
import net.minecraft.client.renderer.DynamicGpuDataStorage
import net.minecraft.client.renderer.DynamicGpuDataStorageMapped

class UniformStorage<T : DynamicGpuDataStorage.DynamicGpuData>(
    private val name: String,
    size: Int
) {
    private val storage = DynamicGpuDataStorageMapped<T>(name, size, 16, 16)

    init {
        STORAGES.add(this)
    }

    companion object {
        private val STORAGES = arrayListOf<UniformStorage<*>>()

        fun endFrame() = STORAGES.forEach { it.endFrame() }
    }

    fun write(data: T) = CustomSlice(name, storage.writeData(data))

    fun endFrame() = storage.endFrame()
}