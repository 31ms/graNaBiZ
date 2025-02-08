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
class Line extends Vector2{
  origin = new Vector2(0,0)
  constructor(origin, direction){
    super(direction.x,direction.y)
    this.origin = origin
  }
}
class PhysicsObject2D extends Object2D{
  constructor(geometry){
    super(geometry)
  }
  getAxis(){
    let RX = Matrix2.rotation(this.rotation).transformVector(new Vector2(1,0))
    let RY = Matrix2.rotation(this.rotation).transformVector(new Vector2(0,1))
    return [
      new Line(this.position, RX),
      new Line(this.position, RY)]
  }
  getCorners(){
    let axis = this.getAxis(this)
    let RX = axis[0].scale(this.size.x/2)
    let RY = axis[1].scale(this.size.y/2)
    return [
      this.position.add(RX).add(RY),
      this.position.add(RX.scale(-1)).add(RY),
      this.position.add(RX).add(RY.scale(-1)),
      this.position.add(RX.scale(-1)).add(RY.scale(-1))
    ]
  }
  getSignedDistance = (line, corner) => {
    let projected = corner.project(line)
    let CP = projected.sub(this.position)
    let sign = (CP.x * line.x) + (CP.y * line.y) > 0
    return CP.length() * (sign ? 1 : -1)
  }
  isColliding(onRect){
    let onRectCorners = onRect.getCorners()
    let corners = this.getCorners()
    let onThisAxis1 = []
    let onThisAxis2 = []
    let onOtherAxis1 = []
    let onOtherAxis2 = []
    for (let i = 0; i<onRectCorners.length; i++){
      onThisAxis1.push(this.getSignedDistance(this.getAxis()[0], onRectCorners[i]))
      onThisAxis2.push(this.getSignedDistance(this.getAxis()[1], onRectCorners[i]))
      onOtherAxis1.push(this.getSignedDistance(onRect.getAxis()[0], corners[i]))
      onOtherAxis2.push(this.getSignedDistance(onRect.getAxis()[1], corners[i]))
    }
    let isAxis1Colliding = ((Math.min(...onThisAxis1) < 0 && Math.max(...onThisAxis1) > 0) || Math.abs(Math.min(...onThisAxis1)) < this.size.x/2 || Math.abs(Math.max(...onThisAxis1)) < this.size.x/2)
    let isAxis2Colliding = ((Math.min(...onThisAxis2) < 0 && Math.max(...onThisAxis2) > 0) || Math.abs(Math.min(...onThisAxis2)) < this.size.y/2 || Math.abs(Math.max(...onThisAxis2)) < this.size.y/2)
    let isAxis3Colliding = ((Math.min(...onOtherAxis1) < 0 && Math.max(...onOtherAxis1) > 0) || Math.abs(Math.min(...onOtherAxis1)) < onRect.size.x/2 || Math.abs(Math.max(...onOtherAxis1)) < onRect.size.x/2)
    let isAxis4Colliding = ((Math.min(...onOtherAxis2) < 0 && Math.max(...onOtherAxis2) > 0) || Math.abs(Math.min(...onOtherAxis2)) < onRect.size.y/2 || Math.abs(Math.max(...onOtherAxis2)) < onRect.size.y/2)
    return (isAxis1Colliding && isAxis2Colliding && isAxis3Colliding && isAxis4Colliding)
  }
}
// var player = new Player(Geometry.rect)
// player.size = new Vector2(30,60)
// player.render()
let a = new PhysicsObject2D(Geometry.rect)
let b = new PhysicsObject2D(Geometry.rect)
a.size = new Vector2(100,50)
b.size = new Vector2(50,100)
a.render()
b.render()
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
  a.position.y = 200*Math.sin(7*c)
  b.position.y = 200*Math.sin(c/2)
  // player.velocity.addTo(player.acceleration.divide(16))
  // player.position.addTo(player.velocity)
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