package main;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import engine.Timer;

import engine.Window;

public class Game {
    Window window;
    public Game(){
        window = new Window(600, 400, "okno");
        window.Bind();
        createCapabilities();
        glClearColor(0, .2f, .1f, 1);
        while (!glfwWindowShouldClose(window.id)){
            glfwPollEvents();
            // System.out.println("TICK");
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(window.id);
            Timer.sleep(100);
        };
    }
    public static void main(String[] args){
        new Game();
    }
}
