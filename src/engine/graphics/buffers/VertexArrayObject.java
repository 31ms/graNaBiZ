package engine.graphics.buffers;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArrayObject {
    private int id;
    public VertexArrayObject(){
        id = glGenVertexArrays();
    }
    public void Bind(){
        glBindVertexArray(id);
    }
    public void unBind(){
        glBindVertexArray(0);
    }
    public void Destroy(){
        glDeleteVertexArrays(id);
    }
}
