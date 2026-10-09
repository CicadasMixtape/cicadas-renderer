#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec4 vertexColor;
in vec2 vertexCoord;
in vec2 vertexSize;

out vec4 fragColor;

layout(std140) uniform RectData {
    float radius;
};

float corners(vec2 position, vec2 size, float radius) {
    return length(max(abs(position) - size + radius, 0.0)) - radius;
}

void main() {
    vec2 center = vertexSize / 2.0;
    float distance = corners(center - (vertexCoord * vertexSize), center - 1.0, radius);
    float step = 1.0 - smoothstep(0.5, 1.0, distance);
    fragColor = vec4(vertexColor.rgb, vertexColor.a * step) * ColorModulator;
}