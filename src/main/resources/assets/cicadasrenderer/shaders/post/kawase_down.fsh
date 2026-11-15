#version 330
#extension GL_ARB_separate_shader_objects : require

#include <cicadasrenderer:kawase_uniform.glsl>

layout(location = 0) in vec2 vertexCoord;

uniform sampler2D Sampler0;

layout(location = 0) out vec4 fragColor;

void main() {
    vec4 sum = texture(Sampler0, vertexCoord) * 4.0;
    sum += texture(Sampler0, vertexCoord - halfPixel.xy * offset);
    sum += texture(Sampler0, vertexCoord + halfPixel.xy * offset);
    sum += texture(Sampler0, vertexCoord + vec2(halfPixel.x, -halfPixel.y) * offset);
    sum += texture(Sampler0, vertexCoord - vec2(halfPixel.x, -halfPixel.y) * offset);
    fragColor = vec4(sum.rgb / 8.0, 1.0);
}