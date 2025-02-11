#version 150 core
in vec2 aPosition;
in vec2 aTextureCoord;

out vec2 textureCoord;

uniform mat3 model;
uniform mat3 ortho;
uniform float zindex;
void main(){
    textureCoord = aTextureCoord;
    gl_Position = vec4(ortho * model * vec3(aPosition, 1), 1);
    gl_Position.z = zindex;
}