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
for(let i = 0; i<30; i++){
  let j = new PhysicsObject2D(Geometry.rect,0)
  j.size = new Vector2(100,100)
  j.position = new Vector2(0,0)
  objects.push(j)
}
let a = new PhysicsObject2D(Geometry.rect,0)
// a.size = new Vector2(100,50)
// a.zindex = -1
// ImageLoader.loadImage("../testbackground.png").then((img) => {
//   a.texture = new Texture().uploadImage(img)
// })
let dt = 0
let c = 0
var background = new Background()
const times = []
function frame(){
  CanvasScreen.fillWindow()
  countFps()
  c+=0.02
  for(let i = 0; i<objects.length; i++){
    console.log(    CollisionChecker.checkCollisions(0,objects[i]).length)
  }
  // a.position.y = 250*Math.sin(c)
  // console.log(CollisionChecker.checkCollisions(0,a).length)
  background.align()
  Renderer.clear()
  Renderer.render(Object2D.scene)
}
let fpsCounter = document.getElementById("fpsCounter")
function countFps(){
  const now = performance.now()
  while (times.length > 0 && times[0] <= now - 1000) {
    times.shift()
  }
  times.push(now)
  fpsCounter.innerText = `Fps: ${times.length}`
}
setInterval(frame,1)
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