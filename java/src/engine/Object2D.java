package engine;

import static org.lwjgl.opengl.GL32.*;

import java.util.ArrayList;

import engine.graphics.Geometry;
import engine.graphics.ShaderProgram;
import math.*;

public class Object2D {
    public static ArrayList<Object2D> scene = new ArrayList<>();
    // public float zindex = 0;
    public Vector2 position = new Vector2(0, 0);
    public float rotation = 0;
    public Vector3 color = new Vector3(1,1,1);
    public Vector2 size = new Vector2(1, 1);
    public Geometry geometry;
    // public texture = null;
    public Object2D(Geometry geometry){
        this.geometry = geometry;
        Object2D.scene.add(this);
    }
    public Matrix3 getModel(){
        return Matrix3.rotation(this.rotation).multiply(new Matrix3()._m00(size.x)._m11(size.y))
        ._m02(this.position.x)
        ._m12(this.position.y);
    }
    public void render(){
        // if (this.texture != null)
        //     this.texture.Bind()
        // else
        // Texture.defaultTexture.Bind()
        ShaderProgram.Uniforms.setMatrix3(ShaderProgram.Uniforms.model, this.getModel());
        // Renderer.program.setVector3("color", this.color)
        // Renderer.program.setFloat("zindex", this.zindex)
        // Renderer.gl.drawElements(Renderer.gl.TRIANGLES, this.geometry.count, Renderer.gl.UNSIGNED_SHORT, 0)
        geometry.Bind();
        glDrawElements(GL_TRIANGLES, geometry.count, GL_UNSIGNED_INT, 0);
    }
    public void remove(){
        scene.remove(this);
    }
}
