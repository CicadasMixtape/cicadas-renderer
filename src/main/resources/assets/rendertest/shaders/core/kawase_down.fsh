#version 330

in vec2 vertexCoord;

uniform sampler2D sampler0;

layout(std140) uniform BlurData {
    float offset;
    vec2 halfPixel;
};

out vec4 fragColor;

void main() {
    vec4 sum = texture(sampler0, vertexCoord) * 4.0;
    sum += texture(sampler0, vertexCoord - halfPixel.xy * offset);
    sum += texture(sampler0, vertexCoord + halfPixel.xy * offset);
    sum += texture(sampler0, vertexCoord + vec2(halfPixel.x, -halfPixel.y) * offset);
    sum += texture(sampler0, vertexCoord - vec2(halfPixel.x, -halfPixel.y) * offset);
    fragColor = sum / 8.0;
}