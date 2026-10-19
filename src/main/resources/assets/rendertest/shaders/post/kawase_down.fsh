#version 330

#moj_import <rendertest:kawase_uniform.glsl>

in vec2 vertexCoord;

uniform sampler2D Sampler0;

out vec4 fragColor;

void main() {
    vec4 sum = texture(Sampler0, vertexCoord) * 4.0;
    sum += texture(Sampler0, vertexCoord - halfPixel.xy * offset);
    sum += texture(Sampler0, vertexCoord + halfPixel.xy * offset);
    sum += texture(Sampler0, vertexCoord + vec2(halfPixel.x, -halfPixel.y) * offset);
    sum += texture(Sampler0, vertexCoord - vec2(halfPixel.x, -halfPixel.y) * offset);
    fragColor = sum / 8.0;
}