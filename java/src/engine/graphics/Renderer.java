package engine.graphics;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import engine.Object2D;
import engine.Window;

public class Renderer {
    private static Window window; 
    public static void init(Window window){
        Renderer.window = window;
        window.Bind();
        createCapabilities();
        ShaderProgram.init();
        Geometry.init();
        glClearColor(0, .2f, .1f, 1);
    }
    public static void clear(){
        glClear(GL_COLOR_BUFFER_BIT);
    }
    public static void render(ArrayList<Object2D> scene){
            for (Object2D object : scene){
                object.render();
            }
        // glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        }
    public static Window getWindow(){
        return window;
    }
}
