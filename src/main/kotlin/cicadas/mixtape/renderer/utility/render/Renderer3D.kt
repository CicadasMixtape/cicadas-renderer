package cicadas.mixtape.renderer.utility.render

import cicadas.mixtape.renderer.api.addLine
import cicadas.mixtape.renderer.api.frustum
import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.mc
import cicadas.mixtape.renderer.api.screenSize
import cicadas.mixtape.renderer.api.withCamera
import cicadas.mixtape.renderer.batching.BatchRenderer
import cicadas.mixtape.renderer.batching.Batched
import cicadas.mixtape.renderer.context.LevelContext
import cicadas.mixtape.renderer.objects.level.DefaultBox
import cicadas.mixtape.renderer.objects.level.DefaultOutline
import cicadas.mixtape.renderer.objects.level.VoxelBox
import cicadas.mixtape.renderer.objects.level.VoxelOutline
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.data.ScreenData
import cicadas.mixtape.renderer.shader.uniform.impl.ScreenUniform
import net.minecraft.world.phys.AABB

object Renderer3D : BatchRenderer() {
    fun drawDefaultBox(
        context: LevelContext,
        box: DefaultBox
    ) {
        if (!frustum.isVisible(box.aabb)) {
            return
        }

        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.POSITION_COLOR_PIPELINE)
                .slice(context.projection())
                .vertex {
                    val aabb = box.aabb.withCamera
                    val color = box.color
                    val x1 = aabb.minX.toFloat()
                    val y1 = aabb.minY.toFloat()
                    val z1 = aabb.minZ.toFloat()
                    val x2 = aabb.maxX.toFloat()
                    val y2 = aabb.maxY.toFloat()
                    val z2 = aabb.maxZ.toFloat()

                    it.addVertex(context.matrix4f(), x1, y1, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y1, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y2, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y2, z1).setColor(color.rgb)

                    it.addVertex(context.matrix4f(), x2, y1, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y1, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y2, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y2, z1).setColor(color.rgb)

                    it.addVertex(context.matrix4f(), x1, y1, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y1, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y2, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y2, z1).setColor(color.rgb)

                    it.addVertex(context.matrix4f(), x1, y1, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y1, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y2, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y2, z2).setColor(color.rgb)

                    it.addVertex(context.matrix4f(), x1, y1, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y1, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y1, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y1, z2).setColor(color.rgb)

                    it.addVertex(context.matrix4f(), x1, y2, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y2, z1).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x2, y2, z2).setColor(color.rgb)
                    it.addVertex(context.matrix4f(), x1, y2, z2).setColor(color.rgb)

                    24
                }
                .build()
        )
    }

    fun drawDefaultOutline(
        context: LevelContext,
        outline: DefaultOutline
    ) {
        if (!frustum.isVisible(outline.aabb)) {
            return
        }

        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.LINES_PIPELINE)
                .slice(context.projection())
                .slice(ScreenUniform.update(ScreenData(mainTarget.screenSize)))
                .vertex {
                    val aabb = outline.aabb.withCamera
                    val color = outline.color
                    val lineWidth = outline.lineWidth
                    val x1 = aabb.minX.toFloat()
                    val y1 = aabb.minY.toFloat()
                    val z1 = aabb.minZ.toFloat()
                    val x2 = aabb.maxX.toFloat()
                    val y2 = aabb.maxY.toFloat()
                    val z2 = aabb.maxZ.toFloat()

                    it.addLine(context.matrix4f(), x1, y1, z1, x1, y2, z1, color.rgb, lineWidth)
                    it.addLine(context.matrix4f(), x2, y1, z1, x2, y2, z1, color.rgb, lineWidth)

                    it.addLine(context.matrix4f(), x2, y1, z2, x2, y2, z2, color.rgb, lineWidth)
                    it.addLine(context.matrix4f(), x1, y1, z2, x1, y2, z2, color.rgb, lineWidth)

                    it.addLine(context.matrix4f(), x1, y1, z1, x2, y1, z1, color.rgb, lineWidth)
                    it.addLine(context.matrix4f(), x2, y1, z1, x2, y1, z2, color.rgb, lineWidth)

                    it.addLine(context.matrix4f(), x2, y1, z2, x1, y1, z2, color.rgb, lineWidth)
                    it.addLine(context.matrix4f(), x1, y1, z2, x1, y1, z1, color.rgb, lineWidth)

                    it.addLine(context.matrix4f(), x1, y2, z1, x2, y2, z1, color.rgb, lineWidth)
                    it.addLine(context.matrix4f(), x2, y2, z1, x2, y2, z2, color.rgb, lineWidth)

                    it.addLine(context.matrix4f(), x2, y2, z2, x1, y2, z2, color.rgb, lineWidth)
                    it.addLine(context.matrix4f(), x1, y2, z2, x1, y2, z1, color.rgb, lineWidth)

                    24
                }
                .build()
        )
    }

    fun drawVoxelBox(
        context: LevelContext,
        box: VoxelBox
    ) {
        if (!frustum.isVisible(AABB(box.pos))) {
            return
        }

        if (box.shape.isEmpty) {
            return
        }

        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.POSITION_COLOR_PIPELINE)
                .slice(context.projection())
                .vertex {
                    val shape = box.shape
                    val pos = box.pos
                    val color = box.color
                    val camera = mc.gameRenderer.mainCamera.position()
                    var count = 0

                    shape.forAllBoxes { x1, y1, z1, x2, y2, z2 ->
                        val x1 = (x1 + (pos.x - camera.x())).toFloat()
                        val y1 = (y1 + (pos.y - camera.y())).toFloat()
                        val z1 = (z1 + (pos.z - camera.z())).toFloat()
                        val x2 = (x2 + (pos.x - camera.x())).toFloat()
                        val y2 = (y2 + (pos.y - camera.y())).toFloat()
                        val z2 = (z2 + (pos.z - camera.z())).toFloat()

                        it.addVertex(context.matrix4f(), x1, y1, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y1, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y2, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y2, z1).setColor(color.rgb)

                        it.addVertex(context.matrix4f(), x2, y1, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y1, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y2, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y2, z1).setColor(color.rgb)

                        it.addVertex(context.matrix4f(), x1, y1, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y1, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y2, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y2, z1).setColor(color.rgb)

                        it.addVertex(context.matrix4f(), x1, y1, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y1, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y2, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y2, z2).setColor(color.rgb)

                        it.addVertex(context.matrix4f(), x1, y1, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y1, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y1, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y1, z2).setColor(color.rgb)

                        it.addVertex(context.matrix4f(), x1, y2, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y2, z1).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x2, y2, z2).setColor(color.rgb)
                        it.addVertex(context.matrix4f(), x1, y2, z2).setColor(color.rgb)

                        count += 24
                    }

                    count
                }
                .build()
        )
    }

    fun drawVoxelOutline(
        context: LevelContext,
        outline: VoxelOutline
    ) {
        if (!frustum.isVisible(AABB(outline.pos))) {
            return
        }

        if (outline.shape.isEmpty) {
            return
        }

        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.LINES_PIPELINE)
                .slice(context.projection())
                .slice(ScreenUniform.update(ScreenData(mainTarget.screenSize)))
                .vertex {
                    val shape = outline.shape
                    val pos = outline.pos
                    val color = outline.color
                    val lineWidth = outline.lineWidth
                    val camera = mc.gameRenderer.mainCamera.position()
                    var count = 0

                    shape.forAllEdges { x1, y1, z1, x2, y2, z2 ->
                        val x1 = (x1 + (pos.x - camera.x())).toFloat()
                        val y1 = (y1 + (pos.y - camera.y())).toFloat()
                        val z1 = (z1 + (pos.z - camera.z())).toFloat()
                        val x2 = (x2 + (pos.x - camera.x())).toFloat()
                        val y2 = (y2 + (pos.y - camera.y())).toFloat()
                        val z2 = (z2 + (pos.z - camera.z())).toFloat()
                        it.addLine(context.matrix4f(), x1, y1, z1, x2, y2, z2, color.rgb, lineWidth)
                        count += 2
                    }

                    count
                }
                .build()
        )
    }
}