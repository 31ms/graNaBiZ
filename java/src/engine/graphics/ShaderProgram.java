package engine.graphics;
import static org.lwjgl.opengl.GL32.*;

import engine.FileReader;
public class ShaderProgram {
    static void init(){
        int vShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vShader, FileReader.readText("shaders/vertex.vert"));
        glCompileShader(vShader);
        int status = glGetShaderi(vShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vShader));
        }

        int fShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(fShader, FileReader.readText("shaders/fragment.frag"));
        glCompileShader(fShader);
        status = glGetShaderi(fShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(fShader));
        }
    }
    private int createShader(){
        
    }
}
