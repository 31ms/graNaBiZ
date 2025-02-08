initAll()
document.body.appendChild(CanvasScreen.cnv)
CanvasScreen.fillWindow()
Renderer.clear()
let dt = 0
function frame(){
  dt = performance.now()
  CanvasScreen.fillWindow()
  countFps()

  Renderer.clear()
  Renderer.render(Object2D.scene)
  dt = Math.abs(dt - performance.now())
  //console.log(dt)
}

// Fps Counter
let fpsCounter = document.getElementById("fpsCounter")
const times = []
function countFps(){
  const now = performance.now()
  while (times.length > 0 && times[0] <= now - 1000) {
    times.shift()
  }
  times.push(now)
  fpsCounter.innerText = `Fps: ${times.length}`
}
setInterval(frame,4)