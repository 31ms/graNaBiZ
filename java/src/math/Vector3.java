package math;

public class Vector3{
    double x = 0;
    double y = 0;
    double z = 0;
    Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector3 copy(){
        return new Vector3(this.x, this.y, this.z);
    }
    Vector3 add(Vector3 other){
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    Vector3 scale(double scalar){
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }
    Vector3 scaleV(Vector3 scalar){
        return new Vector3(this.x * scalar.x, this.y * scalar.y, this.z * scalar.z);
    }
    double length(){
        return Math.hypot(this.z, Math.hypot(this.x, this.y));
    }
    Vector3 normalize(){
        return this.divide(this.length());
    }
    Vector3 negate(){
        return this.scale(-1);
    }
    Vector3 sub(Vector3 other){
        return this.add(other.negate());
    }
    Vector3 divide(double scalar){
        return this.scale(1/scalar);
    }
    static double dot(Vector3 a, Vector3 b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    static Vector3 cross(Vector3 a, Vector3 b){
        var x = a.y * b.z - a.z * b.y;
        var y = a.z * b.x - a.x * b.z;
        var z = a.x * b.y - a.y * b.x;
        return new Vector3(x,y,z);
    }
    static Vector3 lerp(double t, Vector3 a, Vector3 b){
        return new Vector3(
            Interpolation.lerp(t, a.x, b.x),
            Interpolation.lerp(t, a.y, b.y),
            Interpolation.lerp(t, a.z, b.z));
    }
    static Vector3 fromArray(double[] a){
        return new Vector3(a[0], a[1], a[2]);
    }
    double[] toArray(){
        return new double[]{this.x, this.y, this.z};
    }
    // toFloatArray(){
    //     return new Float32Array(this.toArray())
    // }
}