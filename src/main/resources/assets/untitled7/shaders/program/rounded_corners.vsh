#version 120

attribute vec4 Position;
attribute vec2 UV;

varying vec2 texCoord;

void main() {
    gl_Position = Position;
    texCoord = UV;
}
