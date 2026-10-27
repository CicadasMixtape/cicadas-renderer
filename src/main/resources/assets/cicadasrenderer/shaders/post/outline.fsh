#version 330

#moj_import <cicadasrenderer:outline_uniform.glsl>
#moj_import <cicadasrenderer:screen_uniform.glsl>

in vec2 vertexCoord;

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    vec2 texel = 1.0 / screenSize;
    float center = texture(Sampler0, vertexCoord).a;

    if (center != 0.0) {
        fragColor = tint;
        return;
    }

    float alpha = 0.0;
    for (float x = -lineWidth; x <= lineWidth; x++) {
        for (float y = -lineWidth; y <= lineWidth; y++) {
            float a = texture(Sampler0, vertexCoord + vec2(x, y) * texel).a;

            if (a != 0.0) {
                alpha += max(0, (lineWidth - sqrt(x * x + y * y)));
            }
        }
    }

    fragColor = vec4(tint.rgb, alpha);
}