#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <rendertest:corners.glsl>

in vec4 vertexColor;
in vec2 vertexCoord;
in vec2 vertexSize;

uniform sampler2D sampler0;

layout(std140) uniform RectData {
    float radius;
};

out vec4 fragColor;

void main() {
    vec4 color = texture(sampler0, vertexCoord) * vertexColor;
    vec2 center = vertexSize / 2.0;
    float distance = corners(center - (vertexCoord * vertexSize), center - 1.0, radius);
    float step = 1.0 - smoothstep(-0.5, 0.5, distance);
    fragColor = vec4(color.rgb, color.a * step) * ColorModulator;
}