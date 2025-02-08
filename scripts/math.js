// const epsilon = 0
const epsilon = 1E-10
class Interpolation {
    static fade(t){
        return ((6*t - 15)*t + 10)*t*t*t;
    }
    static lerp(t,  a,  b){
        return a + t*(b-a);
    }
    static inverseLerp(a, b,  o)
    {
        return (o - a)/(b - a);
    }
    static smooth(t, a,  b){
        return this.lerp(this.fade(t), a, b);
    }
    static square (x,y,leftBottom, leftTop, rightBottom, rightTop){
        return this.smooth(y, this.smooth(x, leftBottom, rightBottom), this.smooth(x, leftTop, rightTop));
    }
}
function lerp(t, a, b){
    if (typeof a == "number")
        return Interpolation.lerp(t, a, b)
    if ((a instanceof Vector2)||(a instanceof Vector3)||(a instanceof Vector4))
        return a.constructor.lerp(t, a, b);
}
function toArray(v){
    if (typeof v == "number")
        return [v]
    if ((v instanceof Vector2)||(v instanceof Vector3)||(v instanceof Vector4))
        return v.toArray()
}
function fromAr(arr){
    switch (arr.length){
        case 2:
            return Vector2.fromArray(arr)
        case 3:
            return Vector3.fromArray(arr)
        case 4:
            return Vector4.fromArray(arr)
        default:
            return arr[0]
    }
}
class Vector2{
    x = 0
    y = 0
    constructor(x, y){
        this.x = x
        this.y = y
    }
    copy(){
        return new Vector2(this.x, this.y)
    }
    add(other){
        return new Vector2(this.x + other.x, this.y + other.y)
    }
    addTo(other){
        this.x += other.x
        this.y += other.y
        return this
    }
    scale(scalar){
        return new Vector2(this.x * scalar, this.y * scalar)
    }
    scaleTo(scalar){
        this.x *= scalar
        this.y *= scalar
        return this
    }
    scaleV(scalar){
        return new Vector2(this.x * scalar.x, this.y * scalar.y)
    }
    length(){
        return Math.hypot(this.x, this.y)
    }
    normalize(){
        return this.divide(this.length())
    }
    negate(){
        return this.scale(-1)
    }
    sub(other){
        return this.add(other.negate())
    }
    subTo(other){
        this.x -= other.x
        this.y -= other.y
        return this
    }
    divide(scalar){
        return this.scale(1/scalar)
    }
    divideTo(scalar){
        this.x /= scalar
        this.y /= scalar
        return this
    }
    static dot(a, b){
        return a.x * b.x + a.y * b.y
    }
    project(line){
        let dotValue = line.x * (this.x - line.origin.x) + line.y * (this.y - line.origin.y)
        return new Vector2(
          line.origin.x + line.x * dotValue,
          line.origin.y + line.y * dotValue
        )
    }
    static lerp(t, a, b){
        return new Vector2(
            Interpolation.lerp(t, a.x, b.x),
            Interpolation.lerp(t, a.y, b.y))
    }
    static fromArray(a){
        return new Vector2(a[0], a[1])
    }
    toArray(){
        return [this.x, this.y]
    }
    toFloatArray(){
        return new Float32Array(this.toArray())
    }
}
class Line extends Vector2{
    origin = new Vector2(0,0)
    constructor(origin, direction){
      super(direction.x,direction.y)
      this.origin = origin
    }
}
class Vector3{
    x = 0
    y = 0
    z = 0
    constructor(x, y, z){
        this.x = x
        this.y = y
        this.z = z
    }
    copy(){
        return new Vector3(this.x, this.y, this.z)
    }
    add(other){
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z)
    }
    scale(scalar){
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar)
    }
    scaleV(scalar){
        return new Vector3(this.x * scalar.x, this.y * scalar.y, this.z * scalar.z)
    }
    length(){
        return Math.hypot(this.x, this.y, this.z)
    }
    normalize(){
        return this.divide(this.length())
    }
    negate(){
        return this.scale(-1)
    }
    sub(other){
        return this.add(other.negate())
    }
    divide(scalar){
        return this.scale(1/scalar)
    }
    static dot(a, b){
        return a.x * b.x + a.y * b.y + a.z * b.z
    }
    static cross(a, b){
        var x = a.y * b.z - a.z * b.y
        var y = a.z * b.x - a.x * b.z
        var z = a.x * b.y - a.y * b.x
        return new Vector3(x,y,z)
    }
    static lerp(t, a, b){
        return new Vector3(
            Interpolation.lerp(t, a.x, b.x),
            Interpolation.lerp(t, a.y, b.y),
            Interpolation.lerp(t, a.z, b.z))
    }
    static fromArray(a){
        return new Vector3(a[0], a[1], a[2])
    }
    toArray(){
        return [this.x, this.y, this.z]
    }
    toFloatArray(){
        return new Float32Array(this.toArray())
    }
}
class Vector4{
    x = 0
    y = 0
    z = 0
    w = 0
    constructor(x, y, z, w){
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }
    copy(){
        return new Vector4(this.x, this.y, this.z, this.w)
    }
    add(other){
        return new Vector4(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w)
    }
    scale(scalar){
        return new Vector4(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar)
    }
    scaleV(scalar){
        return new Vector3(this.x * scalar.x, this.y * scalar.y, this.z * scalar.z, this.w * scalar.w)
    }
    length(){
        return Math.hypot(this.x, this.y, this.z)
    }
    normalize(){
        return this.divide(this.length())
    }
    negate(){
        return this.scale(-1)
    }
    sub(other){
        return this.add(other.negate())
    }
    divide(scalar){
        return this.scale(1/scalar)
    }
    static dot(a, b){
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w
    }
    static lerp(t, a, b){
        return new Vector4(
            Interpolation.lerp(t, a.x, b.x),
            Interpolation.lerp(t, a.y, b.y),
            Interpolation.lerp(t, a.z, b.z),
            Interpolation.lerp(t, a.w, b.w))
    }
    static smooth(t, a, b){
        return new Vector4(
            Interpolation.smooth(t, a.x, b.x),
            Interpolation.smooth(t, a.y, b.y),
            Interpolation.smooth(t, a.z, b.z),
            Interpolation.smooth(t, a.w, b.w))
    }
    toNDCSpace(){
        return new Vector3(this.x, this.y, this.z).divide(this.w)
    }
    proj4D(persp){
        var v = new Vector3(this.x, this.y, this.z).scale(persp).divide(this.w)
        return new Vector4(v.x, v.y, v.z, 1)
    }
    static fromArray(a){
        return new Vector4(a[0], a[1], a[2], a[3])
    }
    static fromV3(v3, w){
        return new Vector4(v3.x, v3.y, v3.z, w)
    }
    toArray(){
        return [this.x, this.y, this.z, this.w]
    }
}
class Matrix2{
    m00 = 1; m01 = 0
    m10 = 0; m11 = 1
    _m00(m00){this.m00 = m00;return this}
    _m10(m10){this.m10 = m10;return this}
    _m01(m01){this.m01 = m01;return this}
    _m11(m11){this.m11 = m11;return this}
    baseVectors(){
        return [
            new Vector2(this.m00, this.m10),
            new Vector2(this.m01, this.m11),
        ]
    }
    log(){
        console.log(
            new Vector2(this.m00, this.m01),
            new Vector2(this.m10, this.m11),
        )
    }
    transformVector(vector) {
        var x = this.m00 * vector.x + this.m01 * vector.y;
        var y = this.m10 * vector.x + this.m11 * vector.y;
        return new Vector2(x, y);
    }
    multiply(matrix){
        var m = new Matrix2()
        m.m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10;
        m.m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10;

        m.m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11;
        m.m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11;
        return m
    }
    static rotation(angle){
        var m = new Matrix2()
        var c = Math.cos(angle)
        var s = Math.sin(angle)
        m.m00 = c
        m.m11 = c
        m.m01 = -s
        m.m10 = s
        return m
    }
    toArray(){
        return [this.m00, this.m10, this.m01, this.m11]
    }
    toFloatArray(){
        return new Float32Array(this.toArray())
    }
}
class Matrix3{
    m00 = 1; m01 = 0; m02 = 0
    m10 = 0; m11 = 1; m12 = 0
    m20 = 0; m21 = 0; m22 = 1
    _m00(m00){this.m00 = m00;return this}
    _m10(m10){this.m10 = m10;return this}
    _m20(m20){this.m20 = m20;return this}
    _m01(m01){this.m01 = m01;return this}
    _m11(m11){this.m11 = m11;return this}
    _m21(m21){this.m21 = m21;return this}
    _m02(m02){this.m02 = m02;return this}
    _m12(m12){this.m12 = m12;return this}
    _m22(m22){this.m22 = m22;return this}
    baseVectors(){
        return [
            new Vector3(this.m00, this.m10, this.m20),
            new Vector3(this.m01, this.m11, this.m21),
            new Vector3(this.m02, this.m12, this.m22),
        ]
    }
    log(){
        console.log(
            new Vector3(this.m00, this.m01, this.m02),
            new Vector3(this.m10, this.m11, this.m12),
            new Vector3(this.m20, this.m21, this.m22),
        )
    }
    transformVector(vector) {
        var x = this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z;
        var y = this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z;
        var z = this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z;
        return new Vector3(x, y, z);
    }
    transformVector2(vector, z){
        var vec3 = this.transformVector(new Vector3(vector.x, vector.y, z))
        return new Vector2(vec3.x, vec3.y)
    }
    multiply(matrix){
        var m = new Matrix3()
        m.m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20;
        m.m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20;
        m.m20 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20;

        m.m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21;
        m.m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21;
        m.m21 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21;

        m.m02 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22;
        m.m12 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22;
        m.m22 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22;
        return m
    }
    setTranslation(vec2){
        this.m02 = vec2.x
        this.m12 = vec2.y
        return this
    }
    static rotation(angle){
        var m = new Matrix3()
        var c = Math.cos(angle)
        var s = Math.sin(angle)
        m.m00 = c
        m.m11 = c
        m.m01 = -s
        m.m10 = s
        return m
    }
    static ortho(left, right, bottom, top){
        var xt = (left+right)/(left - right)
        var xs = 2/(right - left)

        var yt = (bottom+top)/(bottom - top)
        var ys = 2/(top - bottom)
        var m = new Matrix3()
        return m._m00(xs)._m02(xt)._m11(ys)._m12(yt)
    }
    toArray(){
        return [this.m00, this.m10, this.m20, this.m01, this.m11, this.m21, this.m02, this.m12, this.m22]
    }
    toFloatArray(){
        return new Float32Array(this.toArray())
    }
}
class Matrix4{
    m00 = 1; m01 = 0; m02 = 0; m03 = 0
    m10 = 0; m11 = 1; m12 = 0; m13 = 0
    m20 = 0; m21 = 0; m22 = 1; m23 = 0
    m30 = 0; m31 = 0; m32 = 0; m33 = 1
    _m00(m00){this.m00 = m00;return this}
    _m10(m10){this.m10 = m10;return this}
    _m20(m20){this.m20 = m20;return this}
    _m30(m30){this.m30 = m30;return this}
    _m01(m01){this.m01 = m01;return this}
    _m11(m11){this.m11 = m11;return this}
    _m21(m21){this.m21 = m21;return this}
    _m31(m31){this.m31 = m31;return this}
    _m02(m02){this.m02 = m02;return this}
    _m12(m12){this.m12 = m12;return this}
    _m22(m22){this.m22 = m22;return this}
    _m32(m32){this.m32 = m32;return this}
    _m03(m03){this.m03 = m03;return this}
    _m13(m13){this.m13 = m13;return this}
    _m23(m23){this.m23 = m23;return this}
    _m33(m33){this.m33 = m33;return this}
    baseVectors(){
        return [
            new Vector4(this.m00, this.m10, this.m20, this.m30),
            new Vector4(this.m01, this.m11, this.m21, this.m31),
            new Vector4(this.m02, this.m12, this.m22, this.m32),
            new Vector4(this.m03, this.m13, this.m23, this.m33)
        ]
    }
    log(){
        console.log(
            new Vector4(this.m00, this.m01, this.m02, this.m03),
            new Vector4(this.m10, this.m11, this.m12, this.m13),
            new Vector4(this.m20, this.m21, this.m22, this.m23),
            new Vector4(this.m30, this.m31, this.m32, this.m33)
        )
    }
    transformVector(vector) {
        var x = this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z + this.m03 * vector.w;
        var y = this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z + this.m13 * vector.w;
        var z = this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z + this.m23 * vector.w;
        var w = this.m30 * vector.x + this.m31 * vector.y + this.m32 * vector.z + this.m33 * vector.w;
        return new Vector4(x, y, z, w);
    }
    transformVector3(vector, w){
        var vec4 = this.transformVector(new Vector4(vector.x, vector.y, vector.z, w))
        return new Vector3(vec4.x, vec4.y, vec4.z)
    }
    multiply(matrix){
        var m = new Matrix4()
        m.m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20 + this.m03 * matrix.m30;
        m.m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20 + this.m13 * matrix.m30;
        m.m20 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20 + this.m23 * matrix.m30;
        m.m30 = this.m30 * matrix.m00 + this.m31 * matrix.m10 + this.m32 * matrix.m20 + this.m33 * matrix.m30;

        m.m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21 + this.m03 * matrix.m31;
        m.m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21 + this.m13 * matrix.m31;
        m.m21 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21 + this.m23 * matrix.m31;
        m.m31 = this.m30 * matrix.m01 + this.m31 * matrix.m11 + this.m32 * matrix.m21 + this.m33 * matrix.m31;

        m.m02 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22 + this.m03 * matrix.m32;
        m.m12 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22 + this.m13 * matrix.m32;
        m.m22 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22 + this.m23 * matrix.m32;
        m.m32 = this.m30 * matrix.m02 + this.m31 * matrix.m12 + this.m32 * matrix.m22 + this.m33 * matrix.m32;

        m.m03 = this.m00 * matrix.m03 + this.m01 * matrix.m13 + this.m02 * matrix.m23 + this.m03 * matrix.m33;
        m.m13 = this.m10 * matrix.m03 + this.m11 * matrix.m13 + this.m12 * matrix.m23 + this.m13 * matrix.m33;
        m.m23 = this.m20 * matrix.m03 + this.m21 * matrix.m13 + this.m22 * matrix.m23 + this.m23 * matrix.m33;
        m.m33 = this.m30 * matrix.m03 + this.m31 * matrix.m13 + this.m32 * matrix.m23 + this.m33 * matrix.m33;
        return m
    }
    setTranslation(vec3){
        this.m03 = vec3.x
        this.m13 = vec3.y
        this.m23 = vec3.z
        return this
    }
    static projection(fov,whAspect, n, f){
        var persp = 1/ Math.tan(fov / 2)
        var m = new Matrix4()
        m.m00 = persp/whAspect
        m.m11 = persp
        m.m22 = (f+n)/(f-n)
        m.m32 = 1
        m.m23 = 2*(n*f)/(n-f)
        m.m33 = 0
        return m
    }
    static lookAlong(eye, direction, wUp){
        var result = new Matrix4();

        var zaxis = direction.normalize();
        if (Math.abs(Vector3.dot(zaxis,wUp)) > 0.999) {
            wUp = new Vector3(1, 0, 0);
        }
        var xaxis = Vector3.cross(wUp, zaxis).normalize();
        var yaxis = Vector3.cross(zaxis, xaxis);
        result.
            _m00(xaxis.x)._m01(xaxis.y)._m02(xaxis.z)._m03(-Vector3.dot(xaxis,eye)).
            _m10(yaxis.x)._m11(yaxis.y)._m12(yaxis.z)._m13(-Vector3.dot(yaxis,eye)).
            _m20(zaxis.x)._m21(zaxis.y)._m22(zaxis.z)._m23(-Vector3.dot(zaxis,eye)).
            _m30(0)._m31(0)._m32(0)._m33(1);
        return result;
    }
    static rotationX(angle){
        var m = new Matrix4()
        var c = Math.cos(angle)
        var s = Math.sin(angle)
        m.m11 = c
        m.m22 = c
        m.m12 = -s
        m.m21 = s
        return m
    }
    static rotationY(angle){
        var m = new Matrix4()
        var c = Math.cos(angle)
        var s = Math.sin(angle)
        m.m00 = c
        m.m22 = c
        m.m02 = s
        m.m20 = -s
        return m
    }
    static rotationZ(angle){
        var m = new Matrix4()
        var c = Math.cos(angle)
        var s = Math.sin(angle)
        m.m00 = c
        m.m11 = c
        m.m01 = -s
        m.m10 = s
        return m
    }
}