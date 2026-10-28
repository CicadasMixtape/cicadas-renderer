package cicadas.mixtape.renderer.shader.uniform

import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.systems.RenderSystem
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class DefaultUniform private constructor(
    val transforms: GpuBufferSlice,
    val projection: GpuBufferSlice?
) {
    companion object {
        @JvmStatic
        fun create(): DefaultUniform {
            val transforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrix(), Vector4f(1f), Vector3f(), Matrix4f())
            val projection = RenderSystem.getProjectionMatrixBuffer()
            return DefaultUniform(transforms, projection)
        }
    }
}