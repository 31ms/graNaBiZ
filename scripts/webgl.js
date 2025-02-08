const CanvasScreen = {
    asp : 1,
    setScreenSize(w, h){
        this.cnv.width = window.innerWidth
        this.cnv.height = window.innerHeight
        Renderer.gl.viewport(0, 0, w, h)
        this.asp = w/h
        Renderer.program.setMatrix3("ortho", Matrix3.ortho(-w/2,w/2,-h/2,h/2))
    },
    getScreenSize(){
        return new Vector2(this.cnv.width, this.cnv.height)
    },
    fillWindow(){
        this.setScreenSize(window.innerWidth, window.innerHeight)
    },
    cnv : document.createElement("canvas"),
    init(){
        this.cnv.style.display = "block"
        
        return this
    }
}
const Renderer = {
    gl : null,

    program : {
        vertexShaderSource : 
        `
        precision mediump float;
        attribute vec2 aVertexPosition;
        attribute vec2 aVertexTexCoord;
        uniform mat3 ortho;
        uniform mat3 model;
        uniform float zindex;
        varying vec2 texCoord;
        void main() {
        gl_Position = vec4(ortho*model*vec3(aVertexPosition,1),1);
        gl_Position.z = zindex;
        texCoord = aVertexTexCoord;
        }`,
        fragmentShaderSource :
        `
        precision mediump float;
        varying vec2 texCoord;
        uniform vec3 color;
        uniform sampler2D textureImage;
        // uniform vec2 texOffset;
        // uniform vec2 texScale; zamiast tego jest texOffsetMatrix BO TAK
        uniform mat3 texOffsetMatrix;
        void main()
        {
                // vec4 textureColor = vec4(0);
                // float i = 1.0;
                // for (float x = -50.0; x < 50.0; x++)
                //     for (float y = -50.0; y < 50.0; y++){
                //     vec2 t = texCoord + (vec2(x, y)/1000.0);
                //     textureColor += texture2D(textureImage, t);
                //     i+= 1.0;
                // } 
                // textureColor /= i;
            // vec4 textureColor = texture2D(textureImage, (texCoord+texOffset)*texScale);
            vec4 textureColor = texture2D(textureImage, vec2(texOffsetMatrix*vec3(texCoord,1)));
            if (textureColor.w < 0.001) discard;
            gl_FragColor = textureColor*vec4(color,1);
        }
        `,
        init(){
            this.id = Renderer.gl.createProgram()
            this.createShader(Renderer.gl.VERTEX_SHADER, this.vertexShaderSource)
            this.createShader(Renderer.gl.FRAGMENT_SHADER, this.fragmentShaderSource)
            Renderer.gl.linkProgram(this.id);
            if (!Renderer.gl.getProgramParameter(this.id, Renderer.gl.LINK_STATUS)) {
                console.error('ERROR linking program!', Renderer.gl.getProgramInfoLog(this.id));
            }
            Renderer.gl.validateProgram(this.id);
            if (!Renderer.gl.getProgramParameter(this.id, Renderer.gl.VALIDATE_STATUS)) {
                console.error('ERROR validating program!', Renderer.gl.getProgramInfoLog(this.id));
            }
            this.addAttribLocation("aVertexPosition") 
            this.addAttribLocation("aVertexTexCoord") 

            this.addUniformLocation("ortho")
            this.addUniformLocation("model")
            this.addUniformLocation("color")
            this.addUniformLocation("zindex")
            // this.addUniformLocation("texOffset") luknij na komenty fs
            // this.addUniformLocation("texScale")
            this.addUniformLocation("texOffsetMatrix")
            this.Use()
        },
        addAttribLocation(name){
            this.locations.attributes[name] = Renderer.gl.getAttribLocation(this.id, name)
        },
        addUniformLocation(name){
            this.locations.uniforms[name] = Renderer.gl.getUniformLocation(this.id, name)
        },
        locations : {
            attributes : {},
            uniforms : {}
        },
        setMatrix3(name, m){
            Renderer.gl.uniformMatrix3fv(this.locations.uniforms[name], false, m.toFloatArray())
        },
        setVector3(name, v){
            Renderer.gl.uniform3fv(this.locations.uniforms[name], v.toFloatArray())
        },
        setVector2(name, v){
            Renderer.gl.uniform2fv(this.locations.uniforms[name], v.toFloatArray())
        },
        setFloat(name, f){
            Renderer.gl.uniform1f(this.locations.uniforms[name], f)
        },
        setInt(name, i){
            Renderer.gl.uniform1f(this.locations.uniforms[name], i)
        },
        defineAttrib(name, size, vertexSize, vertexOffset){
            var loc = this.locations.attributes[name]
            Renderer.gl.enableVertexAttribArray(loc)
            Renderer.gl.vertexAttribPointer(
            loc,
            size,
            Renderer.gl.FLOAT,
            false,
            vertexSize * 4,
            vertexOffset * 4
            )
        },
        defineAttribs(){
            this.defineAttrib("aVertexPosition", 2, 4, 0)
            this.defineAttrib("aVertexTexCoord", 2, 4, 2)
        },
        createShader(type, source){
            var shdr = Renderer.gl.createShader(type)
            Renderer.gl.shaderSource(shdr, source)

            Renderer.gl.compileShader(shdr);
            if (!Renderer.gl.getShaderParameter(shdr, Renderer.gl.COMPILE_STATUS)) {
                console.error('ERROR compiling vertex shader!', Renderer.gl.getShaderInfoLog(shdr));
            }
            Renderer.gl.attachShader(this.id, shdr)
        },
        Use(){
            Renderer.gl.useProgram(this.id)
        }
    },
    render(scene){
        for (var key in scene){
            if (scene[key].length == 0) continue
            Geometry.map[key].Bind()
            for (var ob of scene[key]){
                ob.render()
            }
        }
    },
    clear(){
        this.gl.clear(this.gl.COLOR_BUFFER_BIT | this.gl.DEPTH_BUFFER_BIT)
    },
    init(){
        this.gl = CanvasScreen.cnv.getContext("webgl")
        if (!this.gl){
            throw Error("NIE MA PANIE")
        }
        this.gl.clearColor(0, .2, .1, 1)
        this.gl.enable(this.gl.DEPTH_TEST)
        this.gl.depthFunc(this.gl.LEQUAL)
        this.program.init()
        return this
    },
    setBackground(v){
        this.gl.clearColor(v.x, v.y, v.z, v.w)
    }
}
class Geometry {
    static map = {

    }
    constructor(name, vertices, triangles){
        this.name = name
        Geometry.map[name] = this
        this.arrayBuffer = Renderer.gl.createBuffer()
        Renderer.gl.bindBuffer(Renderer.gl.ARRAY_BUFFER, this.arrayBuffer)
        Renderer.gl.bufferData(Renderer.gl.ARRAY_BUFFER, new Float32Array(vertices), Renderer.gl.STATIC_DRAW)

        this.elementsBuffer = Renderer.gl.createBuffer()
        Renderer.gl.bindBuffer(Renderer.gl.ELEMENT_ARRAY_BUFFER, this.elementsBuffer)
        Renderer.gl.bufferData(Renderer.gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(triangles), Renderer.gl.STATIC_DRAW)
        this.count = triangles.length
    }
    Bind(){
        Renderer.gl.bindBuffer(Renderer.gl.ARRAY_BUFFER, this.arrayBuffer)
        Renderer.gl.bindBuffer(Renderer.gl.ELEMENT_ARRAY_BUFFER, this.elementsBuffer)
        Geometry.vertexCount = this.count
        Renderer.program.defineAttribs()
    }
    static vertexCount = 0
    static init(){
        this.rect = new Geometry("rect",[
            -.5, -.5, 0, 1,
            -.5, .5, 0, 0,
            .5, .5, 1, 0,
            .5, -.5, 1, 1
        ], [
            0, 1 ,2, 2, 3, 0
        ])
        this.tri = new Geometry("tri",
            [
                0, .5, 0.5, 0,
                -.5, -.5, 0, 1,
                .5, -.5, 1, 1
            ],
            [0,1,2]
        )
    }
}
class Object2D{
    static scene = {
        rect : [],
        tri : []
    }
    zindex = 0
    position = new Vector2(0, 0)
    rotation = 0
    color = new Vector3(1,1,1)
    size = new Vector2(1, 1)
    texture = null
    constructor(geometry){
        this.geometry = geometry
        Object2D.scene[geometry.name].push(this)
    }
    getModel(){
        return Matrix3.rotation(this.rotation).multiply(new Matrix3()._m00(this.size.x)._m11(this.size.y))
        ._m02(this.position.x)
        ._m12(this.position.y)
    }
    render(){
        if (this.texture != null)
            this.texture.Bind()
        else
        Texture.defaultTexture.Bind()
        Renderer.program.setMatrix3("model", this.getModel())
        Renderer.program.setVector3("color", this.color)
        Renderer.program.setFloat("zindex", this.zindex)
        Renderer.gl.drawElements(Renderer.gl.TRIANGLES, this.geometry.count, Renderer.gl.UNSIGNED_SHORT, 0)
    }
    renderSingle(){
        this.geometry.Bind()
        this.render()
    }
    remove(){
        var array = Object2D.scene[this.geometry.name]
        const index = array.indexOf(this);
        if (index > -1) {
            array.splice(index, 1);
        }
    }
}
class ImageLoader{
    static loadImage(URL){
        return new Promise((resolve, reject) => {
            try {
                var img = new Image
                img.crossOrigin = "anonymous"
                img.onload = () => {
                    resolve(img)
                }
                img.src = URL
            } catch(e){
                reject(e)
            }
        })
    }
}
class Texture{
    offset = new Vector2(0,0)
    scale = new Vector2(1,1)
    constructor(){
        var gl = Renderer.gl
        this.id = gl.createTexture()
        this.Bind()
        this.setWrap(Texture.enums.wrap.CLAMP_TO_EDGE)
        Renderer.gl.texParameteri(Renderer.gl.TEXTURE_2D, Renderer.gl.TEXTURE_MIN_FILTER, Renderer.gl.LINEAR)
        this.setFilter(Texture.enums.filter.NEAREST)
    }
    setWrap(value){
        Renderer.gl.texParameteri(Renderer.gl.TEXTURE_2D, Renderer.gl.TEXTURE_WRAP_S, value)
        Renderer.gl.texParameteri(Renderer.gl.TEXTURE_2D, Renderer.gl.TEXTURE_WRAP_T, value)
    }
    setFilter(value){
        Renderer.gl.texParameteri(Renderer.gl.TEXTURE_2D, Renderer.gl.TEXTURE_MAG_FILTER, value)
    }
    uploadImage(img){
        this.Bind
        Renderer.gl.texImage2D(Renderer.gl.TEXTURE_2D,
            0, Renderer.gl.RGBA, Renderer.gl.RGBA,
            Renderer.gl.UNSIGNED_BYTE,img)
        return this
    }
    uploadPixels(w, h, pixels){
        this.Bind
        Renderer.gl.texImage2D(Renderer.gl.TEXTURE_2D,
            0, Renderer.gl.RGBA,w, h, 0, Renderer.gl.RGBA,
            Renderer.gl.UNSIGNED_BYTE, new Uint8Array(pixels))
        return this
    }
    Bind(){
        var translationM = new Matrix3().setTranslation(this.offset)
        var scaleM = new Matrix3()._m00(this.scale.x)._m11(this.scale.y)
        Renderer.program.setMatrix3("texOffsetMatrix",
            translationM.multiply(scaleM)
        )
        Renderer.gl.bindTexture(Renderer.gl.TEXTURE_2D, this.id)
    }
    static init(){
        this.enums = {
            wrap : {
                CLAMP_TO_EDGE : Renderer.gl.CLAMP_TO_EDGE,
                REPEAT : Renderer.gl.REPEAT,
                MIRRORED_REPEAT : Renderer.gl.MIRRORED_REPEAT
            },
            filter : {
                LINEAR : Renderer.gl.LINEAR,
                NEAREST : Renderer.gl.NEAREST
            }
        }
        this.defaultTexture = new Texture()
        this.defaultTexture.uploadPixels(2, 2,
            [0, 0, 0, 255,  255, 0, 255, 255,
            255, 0, 255, 255,  0, 0, 0, 255])   
    }
}
class Background extends Object2D{
    constructor(){
        super(Geometry.rect)
        this.zindex = 1
        this.texture = new Texture()
        this.texture.Bind()
        this.texture.setWrap(Texture.enums.wrap.MIRRORED_REPEAT)
        this.size = new Vector2(1, CanvasScreen.asp)
    }
}
function initAll(){
    CanvasScreen.init()
    Renderer.init()
    Geometry.init()
    Texture.init()
}