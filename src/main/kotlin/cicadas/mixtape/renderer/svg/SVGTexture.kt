package cicadas.mixtape.renderer.svg

import cicadas.mixtape.renderer.api.toBuffered
import cicadas.mixtape.renderer.api.toNative
import com.github.weisj.jsvg.view.FloatSize
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.renderpearl.api.GpuFormat
import com.mojang.renderpearl.api.textures.GpuTexture
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.Identifier

class SVGTexture(
    private val id: Identifier,
    private val size: FloatSize
) : AbstractTexture() {
    fun load(): SVGTexture {
        val cleanId = Identifier.fromNamespaceAndPath(id.namespace, id.path.removeSuffix("_${size.width}x${size.height}"))
        val image = SVGManager.loadDocument(cleanId).toBuffered(size).toNative()

        close()

        texture = RenderSystem.getDevice().createTexture(id.toString(), GpuTexture.USAGE_COPY_DST or GpuTexture.USAGE_TEXTURE_BINDING, GpuFormat.RGBA8_UNORM, image.width, image.height, 1, 1)
        textureView = RenderSystem.getDevice().createTextureView(texture!!)
        RenderSystem.getDevice().createCommandEncoder().writeToTexture(texture!!, image)

        return this
    }

    fun width() = size.width
    fun height() = size.height
}