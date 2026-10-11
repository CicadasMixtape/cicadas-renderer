#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec2 vertexCoord;
in vec4 vertexColor;

uniform sampler2D sampler0;

layout(std140) uniform FontData {
    float range;
    float thickness;
};

out vec4 fragColor;

float median(vec3 color) {
    return max(min(color.r, color.g), min(max(color.r, color.g), color.b));
}

void main() {
    vec2 h = vec2(dFdx(vertexCoord.x), dFdy(vertexCoord.y)) * textureSize(sampler0, 0);
    float dist = median(texture(sampler0, vertexCoord).rgb) - 0.5 + thickness;
    float pixels = range * inversesqrt(h.x * h.x + h.y * h.y);
    float step = smoothstep(-0.5, 0.5, dist * pixels);
    fragColor = vec4(vertexColor.rgb, vertexColor.a * step) * ColorModulator;
}