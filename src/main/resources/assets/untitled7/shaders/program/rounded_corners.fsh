#version 120

uniform sampler2D DiffuseSampler;
uniform vec2 OutSize;
uniform float Radius;

varying vec2 texCoord;

void main() {
    vec2 uv = texCoord;
    vec2 pixelPos = uv * OutSize;
    
    // Расчет расстояния от углов
    float distTL = length(pixelPos - vec2(Radius));
    float distTR = length(pixelPos - vec2(OutSize.x - Radius, Radius));
    float distBL = length(pixelPos - vec2(Radius, OutSize.y - Radius));
    float distBR = length(pixelPos - vec2(OutSize.x - Radius, OutSize.y - Radius));
    
    // Проверка находится ли пиксель в зоне закругления
    if ((pixelPos.x < Radius && pixelPos.y < Radius && distTL > Radius) ||
        (pixelPos.x > OutSize.x - Radius && pixelPos.y < Radius && distTR > Radius) ||
        (pixelPos.x < Radius && pixelPos.y > OutSize.y - Radius && distBL > Radius) ||
        (pixelPos.x > OutSize.x - Radius && pixelPos.y > OutSize.y - Radius && distBR > Radius)) {
        discard;
    }
    
    gl_FragColor = texture2D(DiffuseSampler, uv);
}
