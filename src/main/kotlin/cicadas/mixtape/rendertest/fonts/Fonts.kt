package cicadas.mixtape.rendertest.fonts

import cicadas.mixtape.rendertest.RenderTest

object Fonts {
    // Дефолтные шрифты
    @JvmField val BOLD = MsdfFont.build(RenderTest.id("fonts/default/sf_bold.json"), RenderTest.id("fonts/default/sf_bold.png"))
    @JvmField val MEDIUM = MsdfFont.build(RenderTest.id("fonts/default/sf_medium.json"), RenderTest.id("fonts/default/sf_medium.png"))
    @JvmField val REGULAR = MsdfFont.build(RenderTest.id("fonts/default/sf_regular.json"), RenderTest.id("fonts/default/sf_regular.png"))
    @JvmField val SEMIBOLD = MsdfFont.build(RenderTest.id("fonts/default/sf_semibold.json"), RenderTest.id("fonts/default/sf_semibold.png"))

    // Скруглённые шрифты
    @JvmField val ROUNDED_BOLD = MsdfFont.build(RenderTest.id("fonts/rounded/sf_rounded_bold.json"), RenderTest.id("fonts/rounded/sf_rounded_bold.png"))
    @JvmField val ROUNDED_MEDIUM = MsdfFont.build(RenderTest.id("fonts/rounded/sf_rounded_medium.json"), RenderTest.id("fonts/rounded/sf_rounded_medium.png"))
    @JvmField val ROUNDED_REGULAR = MsdfFont.build(RenderTest.id("fonts/rounded/sf_rounded_regular.json"), RenderTest.id("fonts/rounded/sf_rounded_regular.png"))
    @JvmField val ROUNDED_SEMIBOLD = MsdfFont.build(RenderTest.id("fonts/rounded/sf_rounded_semibold.json"), RenderTest.id("fonts/rounded/sf_rounded_semibold.png"))
}