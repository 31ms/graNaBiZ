package engine;

import java.util.ArrayList;

public class Scene {
    public ArrayList<Object2D> opaque = new ArrayList<>();
    public ArrayList<Object2D> transparent = new ArrayList<>();
    public void add(Object2D object){
        if (object.transparent)
            transparent.add(object);
        else
            opaque.add(object);
    }
    public void remove(Object2D object){
        if (object.transparent)
            transparent.remove(object);
        else
            opaque.remove(object);
    }
}
