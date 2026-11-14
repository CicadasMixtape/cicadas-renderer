#version 330

float squircle(vec2 position, vec2 size, float radius, float squirt) {
    radius = radius * squirt / 2.0;
    vec2 q = abs(position) - size + radius;
    vec2 q_max = max(q, 0.0);
    float len = pow(pow(q_max.x, squirt) + pow(q_max.y, squirt), 1.0 / squirt);
    return  min(max(q.x, q.y), 0.0) + len - radius;
}