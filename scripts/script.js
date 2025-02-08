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
let a = new PhysicsObject2D(Geometry.rect)
let b = new PhysicsObject2D(Geometry.rect)
a.size = new Vector2(100,50)
b.size = new Vector2(50,100)
let dt = 0
let c = 0
function frame(){
  dt = new Date().getMilliseconds()
  CanvasScreen.fillWindow()
  Renderer.clear()
  Renderer.render(Object2D.scene)
  console.log(a.isColliding(b))
  a.rotation+=Math.PI/180
  c+=0.04
  a.position.y = 200*Math.sin(c)
  b.position.y = 200*Math.sin(c/2)
  dt = Math.abs(dt - new Date().getMilliseconds())
  console.log(dt)
}
setInterval(frame,2)
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