#version 330
#extension GL_ARB_separate_shader_objects : require

#include <cicadasrenderer:projection_uniform.glsl>
#include <cicadasrenderer:screen_uniform.glsl>

layout(location = 0) in vec3 Position;
layout(location = 1) in vec4 Color;
layout(location = 2) in vec3 Normal;
layout(location = 3) in float LineWidth;

layout(location = 0) out vec4 vertexColor;

const float VIEW_SHRINK = 1.0 - (1.0 / 256.0);
const mat4 VIEW_SCALE = mat4(
    VIEW_SHRINK, 0.0, 0.0, 0.0,
    0.0, VIEW_SHRINK, 0.0, 0.0,
    0.0, 0.0, VIEW_SHRINK, 0.0,
    0.0, 0.0, 0.0, 1.0
);

void main() {
    vec4 start = proj * VIEW_SCALE * modelView * vec4(Position, 1.0);
    vec4 end = proj * VIEW_SCALE * modelView * vec4(Position + Normal, 1.0);

    vec3 ndc1 = start.xyz / start.w;
    vec3 ndc2 = end.xyz / end.w;

    vec2 direction = normalize((ndc2.xy - ndc1.xy) * screenSize);
    vec2 offset = vec2(-direction.y, direction.x) * LineWidth / screenSize;

    if (offset.x < 0.0) {
        offset *= -1.0;
    }

    if (gl_VertexIndex % 2 == 0) {
        gl_Position = vec4((ndc1 + vec3(offset, 0.0)) * start.w, start.w);
    } else {
        gl_Position = vec4((ndc1 - vec3(offset, 0.0)) * start.w, start.w);
    }

    vertexColor = Color;
}