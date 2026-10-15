@file:Suppress("FunctionName")

package cicadas.mixtape.rendertest.interfaces

import net.minecraft.client.renderer.culling.Frustum

interface ILevelRenderer {
    fun `rendertest$getFrustum`(): Frustum?
}