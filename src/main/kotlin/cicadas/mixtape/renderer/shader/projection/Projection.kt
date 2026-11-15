package cicadas.mixtape.renderer.shader.projection

import cicadas.mixtape.renderer.shader.data.ProjectionData
import cicadas.mixtape.renderer.shader.uniform.impl.ProjectionUniform
import org.joml.Matrix4f

class Projection private constructor(
    private val proj: Matrix4f,
    private val modelView: Matrix4f
) {
    companion object {
        @JvmStatic
        fun create(proj: Matrix4f, modelView: Matrix4f) = Projection(proj, modelView)
    }

    fun uniform() = ProjectionUniform.update(ProjectionData(proj, modelView))
}