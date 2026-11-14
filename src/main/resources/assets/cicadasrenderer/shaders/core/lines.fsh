#version 330

in vec4 vertexColor;
in float across;

flat in float halfWidth;

out vec4 fragColor;

void main() {
    float alpha = 1.0 - smoothstep(halfWidth - 0.5, halfWidth + 0.5, abs(across));

    if (alpha <= 0.0) {
        discard;
    }

    fragColor = vec4(vertexColor.rgb, vertexColor.a * alpha);
}