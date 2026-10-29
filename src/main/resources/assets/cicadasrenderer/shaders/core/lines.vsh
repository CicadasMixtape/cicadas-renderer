#version 330

#moj_import <cicadasrenderer:projection_uniform.glsl>
#moj_import <cicadasrenderer:screen_uniform.glsl>

in vec3 Position;
in vec4 Color;
in vec3 Normal;
in float LineWidth;

out vec4 vertexColor;

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

    if (gl_VertexID % 2 == 0) {
        gl_Position = vec4((ndc1 + vec3(offset, 0.0)) * start.w, start.w);
    } else {
        gl_Position = vec4((ndc1 - vec3(offset, 0.0)) * start.w, start.w);
    }

    vertexColor = Color;
}