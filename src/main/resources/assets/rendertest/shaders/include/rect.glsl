#version 330

float rect(vec2 position, vec2 size, float radius) {
    return length(max(abs(position) - size + radius, 0.0)) - radius;
}