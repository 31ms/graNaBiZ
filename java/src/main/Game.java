package main;

import engine.FileReader;
import engine.Image;
import engine.Input;
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
        while (!window.shouldClose()){
            Input.updateInput();
            
            Renderer.clear();
            Renderer.render();
            window.swap();
            Timer.sleep(100);
        };
    }
    public static void main(String[] args){
       game = new Game();
    }
}
