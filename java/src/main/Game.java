package main;

import engine.FileReader;
import engine.Image;
import engine.Input;
import engine.Object2D;
import engine.Timer;

import engine.Window;
import engine.graphics.Geometry;
import engine.graphics.Renderer;

public class Game {
    static Game game;
    Window window;
    public Game(){
        window = new Window(600, 400, "okno");
        Renderer.init(window);
        window.setCallbacks();
        Geometry.rect.Bind();
        // System.out.println(Object2D.scene);
        Object2D a = new Object2D(Geometry.rect);
        while (!window.shouldClose()){
            Input.updateInput();
            a.rotation += .1;
            Renderer.clear();
            Renderer.render(Object2D.scene);
            window.swap();
            Timer.sleep(16);
        };
    }
    public static void main(String[] args){
       game = new Game();
    }
}
