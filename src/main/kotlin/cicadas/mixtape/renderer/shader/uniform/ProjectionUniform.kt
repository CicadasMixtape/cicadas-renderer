package cicadas.mixtape.renderer.shader.uniform

import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.storage.impl.ProjectionData
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import org.joml.Matrix4f

object ProjectionUniform {
    private val SIZE = Std140SizeCalculator().putMat4f().putMat4f().get()
    private val STORAGE = UniformStorage<ProjectionData>("Projection", SIZE)

    fun update(proj: Matrix4f, modelView: Matrix4f) = STORAGE.write(ProjectionData(proj, modelView))
}