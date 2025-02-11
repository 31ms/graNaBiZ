package main;

import engine.*;
import math.*;
import engine.physics.*;
import engine.graphics.*;
public class Game {
    static Game game;
    public Window window;
    public Game(){
        window = new Window(1000,1000,"okno");
        Renderer.init(window);
        //Image testImage = FileReader.readImage("testbackground.png");
        //Background background = new Background();
        //background.texture.uploadImage(testImage);
        while (!window.shouldClose()){
            Input.updateInput();
            //background.align();
            //background.texture.offset.x += .001;
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