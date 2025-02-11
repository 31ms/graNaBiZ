package engine;

import engine.graphics.Geometry;
import engine.graphics.Renderer;
import engine.graphics.Texture;
import math.Vector2;

public class Background extends Object2D{
    public Background(){
        super(Geometry.rect);
        zindex = 1;
        texture = new Texture();
        texture.setWrap(Texture.Enums.Wrap.REPEAT);
        size = new Vector2(1, (float)Renderer.window.width / (float)Renderer.window.height);
    }
    public void align(){
        this.size = Renderer.window.getSize();
        this.texture.scale.x = (float)Renderer.window.width / (float)Renderer.window.height
            * ((float)texture.image.height/(float)this.texture.image.width); 
    }
}
