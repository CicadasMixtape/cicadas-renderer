#version 330

float rect(vec2 position, vec2 size, float radius) {
    vec2 q = abs(position) - size + radius;
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - radius;
}