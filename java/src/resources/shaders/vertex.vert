#version 150 core
in vec2 aPosition;

uniform mat3 model;
uniform mat3 ortho;
void main(){
    gl_Position = vec4(ortho * model * vec3(aPosition, 1), 1);
}