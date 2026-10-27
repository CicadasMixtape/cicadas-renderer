package cicadas.mixtape.renderer.svg

import cicadas.mixtape.renderer.api.toBuffered
import cicadas.mixtape.renderer.api.toNative
import com.github.weisj.jsvg.view.FloatSize
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.TextureFormat
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.Identifier

class SVGTexture(val id: Identifier, val size: FloatSize) : AbstractTexture() {
    fun load(): SVGTexture {
        val id = Identifier.fromNamespaceAndPath(id.namespace, id.path.removeSuffix("_${size.width}x${size.height}"))
        val image = SVGManager.loadDocument(id).toBuffered(size).toNative()

        close()

        texture = RenderSystem.getDevice().createTexture(id.toString(), 5, TextureFormat.RGBA8, image.width, image.height, 1, 1)
        textureView = RenderSystem.getDevice().createTextureView(texture!!)
        RenderSystem.getDevice().createCommandEncoder().writeToTexture(texture!!, image)

        return this
    }
}