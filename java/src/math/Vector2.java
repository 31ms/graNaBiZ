package math;
import math.Interpolation;
public class Vector2{
    double x = 0;
    double y = 0;
    Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }
    Vector2 copy(){
        return new Vector2(this.x, this.y);
    }
    Vector2 add(Vector2 other){
        return new Vector2(this.x + other.x, this.y + other.y);
    }
    Vector2 addTo(Vector2 other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }
    Vector2 scale(double scalar){
        return new Vector2(this.x * scalar, this.y * scalar);
    }
    Vector2 scaleTo(double scalar){
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }
    Vector2 scaleV(Vector2  scalar){
        return new Vector2(this.x * scalar.x, this.y * scalar.y);
    }
    double length(){
        return Math.hypot(this.x, this.y);
    }
    Vector2 normalize(){
        return this.divide(this.length());
    }
    Vector2 negate(){
        return this.scale(-1);
    }
    Vector2 sub(Vector2 other){
        return this.add(other.negate());
    }
    Vector2 subTo(Vector2 other){
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }
    Vector2 divide(double scalar){
        return this.scale(1/scalar);
    }
    Vector2 divideTo(double scalar){
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }
    static double dot(Vector2 a, Vector2 b){
        return a.x * b.x + a.y * b.y;
    }
    static Vector2 lerp(double t, Vector2 a, Vector2 b){
        return new Vector2(
            Interpolation.lerp(t, a.x, b.x),
            Interpolation.lerp(t, a.y, b.y));
    }
    static Vector2 project(Vector2 a, Vector2 b){
        return b.divide(b.length()).scale(Vector2.dot(a, b));
    }
    // Vector2 project(Line2 line){
    //     double dotValue = line.x * (this.x - line.origin.x) + line.y * (this.y - line.origin.y);
    //     return new Vector2(
    //       line.origin.x + line.x * dotValue,
    //       line.origin.y + line.y * dotValue
    //     );
    // }
    static Vector2 fromArray(double[] a){
        return new Vector2(a[0], a[1]);
    }
    double[] toArray(){
        return new double[]{this.x, this.y};
    }
    // Vector2 toFloatArray(){
    //     return new Float32Array(this.toArray());
    // }
}
