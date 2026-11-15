#version 330
#extension GL_ARB_separate_shader_objects : require

#include <cicadasrenderer:rect.glsl>
#include <cicadasrenderer:screen_uniform.glsl>

layout(location = 0) in vec2 vertexSize;
layout(location = 1) in vec2 vertexCoord;
layout(location = 2) in float vertexRadius;
layout(location = 3) in vec4 vertexColor;

uniform sampler2D Sampler0;

layout(location = 0) out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, gl_FragCoord.xy / screenSize) * vertexColor;
    vec2 center = vertexSize / 2.0;
    float dist = rect(center - (vertexCoord * vertexSize), center - 1.0, vertexRadius);
    float alpha = 1.0 - smoothstep(-0.5, 0.5, dist);
    fragColor = vec4(color.rgb, color.a * alpha);
}