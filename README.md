# Cicadas-Renderer

Рендер для всеми любимой игры Minecraft на версии 26.3

## 2D
* Рект (закруглённый/обычный)
* Текстура (закруглённая/обычная)
* Блюр
* Сквиркл
* Жидкое стекло
* Текст

## 3D (AABB и VoxelShape)
* Боксы
* Оутлайны

## Анимации
* Simple
* Spring

## Функции плавности
* Simple
* CubicBezier

## Также рендер поддерживает загрузку SVG файлов в текстуру

## Добавление библиотеки в проект

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.CicadasMixtape:cicadas-renderer:26.3-1.0")
}
```

![preview.png](assets/preview.png)

Код превьюшки со скриншота находится [тут](src/testmod/kotlin/cicadas/mixtape/renderer/testmod/Testmod.kt).

###### (Написано полностью на котлине)