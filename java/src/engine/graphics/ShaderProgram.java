package engine.graphics;
import static org.lwjgl.opengl.GL32.*;

import engine.FileReader;
public class ShaderProgram {
    private static int id;
    static void init(){
        id = glCreateProgram();
        addShader("vertex.vert", GL_VERTEX_SHADER);
        addShader("fragment.frag", GL_FRAGMENT_SHADER);
    }
    private static void addShader(String filename, int target){
        int shader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(shader, FileReader.readText("shaders/" + filename));
        glCompileShader(shader);
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(shader));
        }
        glAttachShader(id, shader);
    }
}
