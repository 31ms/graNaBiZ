package engine.graphics.buffers;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArrayObject {
    private int id;
    // Creating VertexArrayObject
    public VertexArrayObject(){
        id = glGenVertexArrays();
    }
    // Binding VertexArrayObject
    public void Bind(){
        glBindVertexArray(id);
    }
    // Unbinding VertexArrayObject
    public void unBind(){
        glBindVertexArray(0);
    }
    // Destroying VertexArrayObject
    public void Destroy(){
        glDeleteVertexArrays(id);
    }
}
