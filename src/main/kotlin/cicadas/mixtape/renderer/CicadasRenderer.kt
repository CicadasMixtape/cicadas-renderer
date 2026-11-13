package cicadas.mixtape.renderer

import cicadas.mixtape.renderer.api.init
import cicadas.mixtape.renderer.api.mc
import cicadas.mixtape.renderer.shader.CicadasFormats
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.CicadasSnippets
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManagerReloadListener

object CicadasRenderer {
    // В аргумент "fonts" запихивайте вызов класса с фонтами
    @JvmStatic
    fun initialization(fonts: () -> Unit) {
        CicadasFormats.init()
        CicadasSnippets.init()
        CicadasPipelines.init()

        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(id("reloader"), ResourceManagerReloadListener {
            fonts()
        })
    }

    @JvmStatic
    internal fun id(string: String) = Identifier.fromNamespaceAndPath("cicadasrenderer", string)

    @JvmStatic
    internal fun stream(id: Identifier) = javaClass.classLoader.getResourceAsStream("assets/${id.namespace}/${id.path}") ?: throw RuntimeException("failed to load: $id")

    @JvmStatic
    internal fun texture(id: Identifier) = mc.textureManager.getTexture(id)
}