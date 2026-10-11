#version 330

in vec2 vertexCoord;

uniform sampler2D sampler0;

layout(std140) uniform BlurData {
    float offset;
    vec2 halfPixel;
};

out vec4 fragColor;

void main() {
    vec4 sum = texture(sampler0, vertexCoord +vec2(-halfPixel.x * 2.0, 0.0) * offset);
    sum += texture(sampler0, vertexCoord + vec2(-halfPixel.x, halfPixel.y) * offset) * 2.0;
    sum += texture(sampler0, vertexCoord + vec2(0.0, halfPixel.y * 2.0) * offset);
    sum += texture(sampler0, vertexCoord + vec2(halfPixel.x, halfPixel.y) * offset) * 2.0;
    sum += texture(sampler0, vertexCoord + vec2(halfPixel.x * 2.0, 0.0) * offset);
    sum += texture(sampler0, vertexCoord + vec2(halfPixel.x, -halfPixel.y) * offset) * 2.0;
    sum += texture(sampler0, vertexCoord + vec2(0.0, -halfPixel.y * 2.0) * offset);
    sum += texture(sampler0, vertexCoord + vec2(-halfPixel.x, -halfPixel.y) * offset) * 2.0;
    fragColor = sum / 12.0;
}