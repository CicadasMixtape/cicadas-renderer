package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.init
import cicadas.mixtape.rendertest.fonts.Fonts
import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.shader.Layers
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.Snippets
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManagerReloadListener

object RenderTest : ModInitializer {
    override fun onInitialize() {
        RenderManager.init()

        // Типо оптимизация
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(id("reloader"),  ResourceManagerReloadListener {
            Formats.init()
            Snippets.init()
            Pipelines.init()
            Layers.init()
            Fonts.init()
        })
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("rendertest", string)
}