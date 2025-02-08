initAll()
document.body.appendChild(CanvasScreen.cnv)
CanvasScreen.fillWindow()
Renderer.clear()
class Player extends Object2D{
  velocity = new Vector2(0,0)
  acceleration = new Vector2(0,-20)
  constructor(geometry){
    super(geometry)
  }
  jump(){
    this.velocity.y = 20
  }
}
let objects = []
for(let i = 0; i<10; i++){
  let j = new PhysicsObject2D(Geometry.rect,0)
  j.size = new Vector2(100,100)
  j.position = new Vector2(250*Math.random() - 125,250*Math.random() - 125)
  objects.push()
}
let a = new PhysicsObject2D(Geometry.rect,0)
a.size = new Vector2(100,50)
a.zindex = -1
ImageLoader.loadImage("../testbackground.png").then((img) => {
  a.texture = new Texture().uploadImage(img)
})
let dt = 0
let c = 0
var background = new Background()
function frame(){
  dt = new Date().getMilliseconds()
  CanvasScreen.fillWindow()
  console.log(CollisionChecker.checkCollisions(0,a))
  c+=0.02
  a.position.y = 250*Math.sin(c)
  background.align()
  Renderer.clear()
  Renderer.render(Object2D.scene)
  dt = Math.abs(dt - new Date().getMilliseconds())
}
setInterval(frame,16)
ImageLoader.loadImage("../testbackground.png").then((img) => {
  background.setImage(img)
})
document.onkeydown = (e) => {
  switch(e.code){
    case "Space":
      player.jump()
      break
    case "A":
      player.velocity.x = -10
      break
    case "D":
      player.velocity   .x = 10
    break
  }
}