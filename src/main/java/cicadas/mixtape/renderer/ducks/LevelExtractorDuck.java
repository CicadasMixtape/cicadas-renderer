package cicadas.mixtape.renderer.ducks;

import net.minecraft.client.renderer.culling.Frustum;
import org.jetbrains.annotations.Nullable;

public interface LevelExtractorDuck {
    @Nullable Frustum cicadas$getFrustum();
}