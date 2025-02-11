package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector4{
    public double x = 0;
    public double y = 0;
    public double z = 0;
    public double w = 0;
    public Vector4(double x, double y, double z, double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public Vector4 copy(){
        return new Vector4(x, y, z , w);
    }
    public Vector4 add(Vector4 other){
        return new Vector4(x + other.x, y + other.y, z + other.z, w + other.w);
    }
    public Vector4 scale(double scalar){
        return new Vector4(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }
    public Vector4 scaleV(Vector4 scalar){
        return new Vector4(this.x * scalar.x, this.y * scalar.y, this.z * scalar.z, this.w * scalar.w);
    }
    public double length(){
        return Math.hypot(w, Math.hypot(z, Math.hypot(x, y)));
    }
    // public Vector3 normalize(){            piernicze to potem to dorobie
    //     return this.divide(this.length());
    // }
    // public Vector3 negate(){
    //     return this.scale(-1);
    // }
    // public Vector3 sub(Vector3 other){
    //     return this.add(other.negate());
    // }
    // public Vector3 divide(double scalar){
    //     return this.scale(1/scalar);
    // }
    // public static double dot(Vector3 a, Vector3 b){
    //     return a.x * b.x + a.y * b.y + a.z * b.z;
    // }
    // public static Vector3 cross(Vector3 a, Vector3 b){
    //     var x = a.y * b.z - a.z * b.y;
    //     var y = a.z * b.x - a.x * b.z;
    //     var z = a.x * b.y - a.y * b.x;
    //     return new Vector3(x,y,z);
    // }
    // public static Vector3 lerp(double t, Vector3 a, Vector3 b){
    //     return new Vector3(
    //         Interpolation.lerp(t, a.x, b.x),
    //         Interpolation.lerp(t, a.y, b.y),
    //         Interpolation.lerp(t, a.z, b.z));
    // }
    // public static Vector3 fromArray(double[] a){
    //     return new Vector3(a[0], a[1], a[2]);
    // }
    public float[] toArray(){
        return new float[]{(float)x, (float)y, (float)z, (float)w};
    }
    public FloatBuffer toFloatBuffer(){
        return BufferUtils.createFloatBuffer(4).put(toArray()).flip();
    }
}