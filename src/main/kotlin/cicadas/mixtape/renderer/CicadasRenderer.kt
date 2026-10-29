package cicadas.mixtape.renderer

import cicadas.mixtape.renderer.api.init
import cicadas.mixtape.renderer.shader.CicadasFormats
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.CicadasSnippets
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManagerReloadListener

object CicadasRenderer {
    fun initialization(unit: () -> Unit) {
        CicadasFormats.init()
        CicadasSnippets.init()
        CicadasPipelines.init()

        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(id("reloader"), ResourceManagerReloadListener {
            unit()
        })
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("cicadasrenderer", string)
}