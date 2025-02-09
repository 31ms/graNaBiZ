package main;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.BufferUtils;

import engine.Timer;

import engine.Window;

public class Game {
    // BufferUtils
    Window window;
    public Game(){
        window = new Window(200, 200, "okno");
        while (!glfwWindowShouldClose(window.id)){
            glfwPollEvents();
            System.out.println("TICK");
            glfwSwapBuffers(window.id);
            Timer.sleep(100);
        };
    }
    public static void main(String[] args){
        new Game();
    }
}
