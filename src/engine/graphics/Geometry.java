package engine.graphics;

import engine.graphics.buffers.VertexArrayObject;
import engine.graphics.buffers.BufferObject;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
public class Geometry {
    private VertexArrayObject vao;
    private BufferObject vbo, ebo;
    public int count;
    Geometry(float[] vertices, int[] triangles){
        // Creating buffers, uploading arrays
        vao = new VertexArrayObject();
        vao.Bind();
        vbo = BufferObject.VertexBufferObject();
        vbo.Bind();
        vbo.uploadData(vertices, GL_STATIC_DRAW);
        ebo = BufferObject.ElementBufferObject();
        ebo.Bind();
        ebo.uploadData(triangles, GL_STATIC_DRAW);
        // Defining ShaderProgram attributes into bound buffer
        ShaderProgram.defineAttribs();
        vao.unBind();
        count = triangles.length;
    }
    public void Bind(){
        // Binding VertexArrayObject
        vao.Bind();
    }
    public static Geometry rect;
    public static Geometry triangle;
    static void init(){
        // Creating rect and triangle Geometries
        rect = new Geometry(new float[]{
            -.5f, -.5f, 0, 1,
            -.5f, .5f, 0, 0,
            .5f, .5f, 1, 0,
            .5f, -.5f, 1, 1
        }, new int[]{
            0, 1 ,2, 2, 3, 0
        });
        triangle = new Geometry(
            new float[]{
                0, .5f, 0.5f, 0,
                -.5f, -.5f, 0, 1,
                .5f, -.5f, 1, 1
            },
            new int[]{0,1,2}
        );
    }
}
