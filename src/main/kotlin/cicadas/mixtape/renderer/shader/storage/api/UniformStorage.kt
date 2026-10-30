package cicadas.mixtape.renderer.shader.storage.api

import cicadas.mixtape.renderer.shader.slice.CustomSlice
import net.minecraft.client.renderer.DynamicUniformStorage

class UniformStorage<T : DynamicUniformStorage.DynamicUniform>(
    private val name: String,
    size: Int
) {
    private val storage = DynamicUniformStorage<T>(name, size, 16)

    init {
        STORAGES.add(this)
    }

    companion object {
        private val STORAGES = arrayListOf<UniformStorage<*>>()

        fun endFrame() = STORAGES.forEach { it.endFrame() }
    }

    fun write(data: T) = CustomSlice(name, storage.writeUniform(data))

    fun endFrame() = storage.endFrame()
}