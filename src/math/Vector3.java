package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector3{
    public double x = 0;
    public double y = 0;
    public double z = 0;
    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3 copy(){
        return new Vector3(this.x, this.y, this.z);
    }
    public Vector3 add(Vector3 other){
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    public Vector3 scale(double scalar){
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }
    public Vector3 scaleV(Vector3 scalar){
        return new Vector3(this.x * scalar.x, this.y * scalar.y, this.z * scalar.z);
    }
    public double length(){
        return Math.hypot(this.z, Math.hypot(this.x, this.y));
    }
    public Vector3 normalize(){
        return this.divide(this.length());
    }
    public Vector3 negate(){
        return this.scale(-1);
    }
    public Vector3 sub(Vector3 other){
        return this.add(other.negate());
    }
    public Vector3 divide(double scalar){
        return this.scale(1/scalar);
    }
    public static double dot(Vector3 a, Vector3 b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    public static Vector3 cross(Vector3 a, Vector3 b){
        var x = a.y * b.z - a.z * b.y;
        var y = a.z * b.x - a.x * b.z;
        var z = a.x * b.y - a.y * b.x;
        return new Vector3(x,y,z);
    }
    public static Vector3 lerp(double t, Vector3 a, Vector3 b){
        return new Vector3(
            Interpolation.lerp(t, a.x, b.x),
            Interpolation.lerp(t, a.y, b.y),
            Interpolation.lerp(t, a.z, b.z));
    }
    public static Vector3 fromArray(double[] a){
        return new Vector3(a[0], a[1], a[2]);
    }
    public float[] toArray(){
        return new float[]{(float)x, (float)y, (float)z};
    }
    public FloatBuffer toFloatBuffer(){
        return BufferUtils.createFloatBuffer(3).put(toArray()).flip();
    }
}