package cicadas.mixtape.rendertest

import cicadas.mixtape.rendertest.api.init
import cicadas.mixtape.rendertest.fonts.Fonts
import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.Snippets
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManagerReloadListener

object RenderTest : ModInitializer {
    override fun onInitialize() {
        Formats.init()
        Snippets.init()
        Pipelines.init()
        RenderManager.init()

        // Нужно чтобы при первой прогрузке мира не загружать шрифты
        // Типо оптимизация
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(id("reloader"),  ResourceManagerReloadListener {
            Fonts.init()
        })
    }

    fun id(string: String) = Identifier.fromNamespaceAndPath("rendertest", string)
}