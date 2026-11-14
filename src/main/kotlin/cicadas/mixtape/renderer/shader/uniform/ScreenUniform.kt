package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.storage.impl.ScreenData
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import org.joml.Vector2f

object ScreenUniform {
    private val SIZE = Std140SizeCalculator().putVec2().get()
    private val STORAGE = UniformStorage<ScreenData>("Screen", SIZE)

    fun update(screenSize: Vector2f) = STORAGE.write(ScreenData(screenSize))
}