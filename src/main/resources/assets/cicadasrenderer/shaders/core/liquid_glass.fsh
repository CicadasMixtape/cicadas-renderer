#version 330
#extension GL_ARB_separate_shader_objects : require

#include <cicadasrenderer:rect.glsl>
#include <cicadasrenderer:liquid_uniform.glsl>
#include <cicadasrenderer:screen_uniform.glsl>

layout(location = 0) in vec2 vertexSize;
layout(location = 1) in vec2 vertexCoord;
layout(location = 2) in float vertexRadius;
layout(location = 3) in vec4 vertexColor;

uniform sampler2D Sampler0;

layout(location = 0) out vec4 fragColor;

void main() {
    vec2 center = vertexSize / 2.0;
    vec2 p = vertexCoord * vertexSize - center;

    float dist = rect(-p, center - 1.0, vertexRadius);
    float alpha = 1.0 - smoothstep(-0.5, 0.5, dist);

    vec2 uv = gl_FragCoord.xy / screenSize;
    vec2 d = vec2(dFdx(p.x), dFdy(p.y));
    d = sign(d) * max(abs(d), 1e-6);

    vec2 wc = (gl_FragCoord.xy - p / d) / screenSize;
    float bevel = min(center.x, center.y);
    float rb = pow(1.0 - clamp(-dist / bevel, 0.0, 1.0), power);
    vec2 lens = clamp(wc + (uv - wc) * (1.0 - rb * 0.5), 0.0, 1.0);
    vec4 color = vec4(texture(Sampler0, lens).rgb, 1.0) * vertexColor;

    fragColor = vec4(color.rgb, color.a * alpha);
}