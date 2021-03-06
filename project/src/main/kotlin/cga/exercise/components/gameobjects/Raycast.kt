package cga.exercise.components.gameobjects

import cga.exercise.components.geometry.Renderable
import cga.exercise.components.geometry.Transformable
import org.joml.Math
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class Raycast(var x: Float, var y: Float, var z: Float, val mm: Matrix4f) : Transformable() {

    val speed = -0.001f

    //Returns position on collision with wall
    fun moveUntilCollision(col: CollisionPool) : Vector4f{

        this.setModelMatrix(mm)

        for (i in 0 .. 100000) {
            /*
            x += xdir*speed
            y += ydir*speed
            z += zdir*speed
            */
            translateLocal(Vector3f(0f,0f,speed))
            if (col.checkPointCollision(this.getWorldPosition().x,this.getWorldPosition().y,this.getWorldPosition().z)) {
                val getColMask = col.checkPointCollisionEntity(this.getWorldPosition().x,this.getWorldPosition().y,this.getWorldPosition().z)
                val getRotation = getColMask.getCollisionSide(this.getWorldPosition().x,this.getWorldPosition().y,this.getWorldPosition().z)
                //val portalable = getColMask.portalable
                //if (portalable == null || portalable == true) {
                if (getRotation != 22f && ((getColMask.x2 - getColMask.x1 >= 3 || getColMask.z2 - getColMask.z1 >=3) && getColMask.y2 - getColMask.y1 >= 6)) {
                    var xret = this.getWorldPosition().x
                    var zret = this.getWorldPosition().z
                    if (getRotation == 90f || getRotation == 270f) {
                        //zret = Math.max(Math.min(this.getWorldPosition().z, getColMask.z2-1.5f), getColMask.z1+1.5f)
                    }
                    if (getRotation == 0f || getRotation == 180f) {
                        //xret = Math.max(Math.min(this.getWorldPosition().x, getColMask.x2-1.5f), getColMask.x1+1.5f)
                    }
                    val yret = Math.max(Math.min(this.getWorldPosition().y, getColMask.y2-3f), getColMask.y1+3f)
                    return Vector4f(xret, yret, zret, getRotation)
                }
            }
        }

        return Vector4f(-9999f)

    }

}