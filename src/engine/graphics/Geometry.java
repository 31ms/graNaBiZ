package engine.graphics;

import engine.graphics.buffers.VertexArrayObject;
import engine.graphics.buffers.VertexBufferObject;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
public class Geometry {
    VertexArrayObject vao;
    VertexBufferObject vbo, ebo;
    public int count;
    Geometry(float[] vertices, int[] triangles){
        vao = new VertexArrayObject();
        vao.Bind();

        vbo = VertexBufferObject.vbo();
        vbo.Bind();
        vbo.uploadData(vertices, GL_STATIC_DRAW);
        ebo = VertexBufferObject.ebo();
        ebo.Bind();
        ebo.uploadData(triangles, GL_STATIC_DRAW);
        ShaderProgram.defineAttribs();
        vao.unBind();
        count = triangles.length;
    }
    public void Bind(){
        vao.Bind();
    }
    public static Geometry rect;
    public static Geometry tri;
    static void init(){
        rect = new Geometry(new float[]{
            -.5f, -.5f, 0, 1,
            -.5f, .5f, 0, 0,
            .5f, .5f, 1, 0,
            .5f, -.5f, 1, 1
        }, new int[]{
            0, 1 ,2, 2, 3, 0
        });
        tri = new Geometry(
            new float[]{
                0, .5f, 0.5f, 0,
                -.5f, -.5f, 0, 1,
                .5f, -.5f, 1, 1
            },
            new int[]{0,1,2}
        );
    }
}
