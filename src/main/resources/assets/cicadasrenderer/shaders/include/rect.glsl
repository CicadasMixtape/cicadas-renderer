#ifndef CICADASRENDERER_RECT_GLSL
#define CICADSRENDERER_RECT_GLSL

float rect(vec2 position, vec2 size, float radius) {
    vec2 q = abs(position) - size + radius;
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - radius;
}

#endif