#version 330

#moj_import <cicadasrenderer:kawase_uniform.glsl>

in vec2 vertexCoord;

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    vec4 sum = texture(Sampler0, vertexCoord + vec2(-halfPixel.x * 2.0, 0.0) * offset);
    sum += texture(Sampler0, vertexCoord + vec2(-halfPixel.x, halfPixel.y) * offset) * 2.0;
    sum += texture(Sampler0, vertexCoord + vec2(0.0, halfPixel.y * 2.0) * offset);
    sum += texture(Sampler0, vertexCoord + vec2(halfPixel.x, halfPixel.y) * offset) * 2.0;
    sum += texture(Sampler0, vertexCoord + vec2(halfPixel.x * 2.0, 0.0) * offset);
    sum += texture(Sampler0, vertexCoord + vec2(halfPixel.x, -halfPixel.y) * offset) * 2.0;
    sum += texture(Sampler0, vertexCoord + vec2(0.0, -halfPixel.y * 2.0) * offset);
    sum += texture(Sampler0, vertexCoord + vec2(-halfPixel.x, -halfPixel.y) * offset) * 2.0;
    fragColor = vec4(sum.rgb / 12.0, 1.0);
}