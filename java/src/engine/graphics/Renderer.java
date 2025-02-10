package engine.graphics;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import engine.Window;

public class Renderer {
    private static Window window; 
    public static void init(Window window){
        Renderer.window = window;
        window.Bind();
        createCapabilities();
        Geometry.init();
        glClearColor(0, .2f, .1f, 1);

        ShaderProgram.init();
    }
    public static void clear(){
        glClear(GL_COLOR_BUFFER_BIT);
    }
    public static Window getWindow(){
        return window;
    }
}
