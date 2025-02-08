initAll()
document.body.appendChild(CanvasScreen.cnv)
CanvasScreen.fillWindow()
Renderer.clear()
let objects = []
for(let i = 0; i<30; i++){
  let j = new PhysicsObject2D(Geometry.rect,0)
  j.size = new Vector2(100,100)
  j.position = new Vector2(0,0)
  j.color = new Vector3(Math.random(),0,Math.random()) // tu masz przedstawione jak mozna zrobic
  objects.push(j)
}
let dt = 0
let c = 0
var background = new Background()
const times = []
function frame(){
  dt = performance.now()
  CanvasScreen.fillWindow()
  countFps()
  c+=0.02
  for(let i = 0; i<objects.length; i++){
    console.log(    CollisionChecker.checkCollisions(0,objects[i]).length)
  }
  background.align()
  Renderer.clear()
  Renderer.render(Object2D.scene)
  dt = Math.abs(dt - performance.now())
  console.log(dt)
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