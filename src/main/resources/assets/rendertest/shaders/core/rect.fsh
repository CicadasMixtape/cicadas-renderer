#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <rendertest:rect.glsl>
#moj_import <rendertest:rect_data.glsl>

in vec2 vertexSize;
in vec2 vertexCoord;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec2 center = vertexSize / 2.0;
    float distance = rect(center - (vertexCoord * vertexSize), center - 1.0, radius);
    float step = 1.0 - smoothstep(-0.5, 0.5, distance);
    fragColor = vec4(vertexColor.rgb, vertexColor.a * step) * ColorModulator;
}