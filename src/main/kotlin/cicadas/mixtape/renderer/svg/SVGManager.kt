package cicadas.mixtape.renderer.svg

import cicadas.mixtape.renderer.api.mc
import com.github.weisj.jsvg.SVGDocument
import com.github.weisj.jsvg.parser.LoaderContext
import com.github.weisj.jsvg.parser.SVGLoader
import com.github.weisj.jsvg.view.FloatSize
import net.minecraft.resources.Identifier

object SVGManager {
    private val loader = SVGLoader()
    private val textures = mutableMapOf<Identifier, SVGTexture>()

    fun loadDocument(id: Identifier): SVGDocument {
        val stream = mc.resourceManager.open(id)
        return loader.load(stream, null, LoaderContext.createDefault()) ?: throw RuntimeException("failed to load: $id")
    }

    fun getTexture(id: Identifier, size: FloatSize): SVGTexture {
        val id = id.withSuffix("_${size.width}x${size.height}")
        return textures[id] ?: createTexture(id, size)
    }

    private fun createTexture(id: Identifier, size: FloatSize): SVGTexture {
        val texture = SVGTexture(id, size).load()
        textures[id] = texture
        return texture
    }
}