package cicadas.mixtape.rendertest.fonts

import cicadas.mixtape.rendertest.RenderTest

object Fonts {
    // Дефолтные шрифты
    @JvmField val BOLD = MsdfFont.build(RenderTest.id("default/sf_bold"))
    @JvmField val MEDIUM = MsdfFont.build(RenderTest.id("default/sf_medium"))
    @JvmField val REGULAR = MsdfFont.build(RenderTest.id("default/sf_regular"))
    @JvmField val SEMIBOLD = MsdfFont.build(RenderTest.id("default/sf_semibold"))

    // Скруглённые шрифты
    @JvmField val ROUNDED_BOLD = MsdfFont.build(RenderTest.id("rounded/sf_rounded_bold"))
    @JvmField val ROUNDED_MEDIUM = MsdfFont.build(RenderTest.id("rounded/sf_rounded_medium"))
    @JvmField val ROUNDED_REGULAR = MsdfFont.build(RenderTest.id("rounded/sf_rounded_regular"))
    @JvmField val ROUNDED_SEMIBOLD = MsdfFont.build(RenderTest.id("rounded/sf_rounded_semibold"))
}