package cicadas.mixtape.renderer.shader.uniform.api

import cicadas.mixtape.renderer.shader.slice.CustomSlice
import net.minecraft.client.renderer.DynamicUniformStorage

abstract class CustomUniform<T : DynamicUniformStorage.DynamicUniform>(
    private val name: String,
    size: Int
) {
    companion object {
        @JvmField
        val STORAGES = arrayListOf<DynamicUniformStorage<*>>()

        @JvmStatic
        fun endAll() = STORAGES.forEach { it.endFrame() }
    }

    private val storage = DynamicUniformStorage<T>(
        name,
        size,
        16
    )

    init {
        STORAGES.add(storage)
    }

    fun update(data: T) = CustomSlice(name, storage.writeUniform(data))
}