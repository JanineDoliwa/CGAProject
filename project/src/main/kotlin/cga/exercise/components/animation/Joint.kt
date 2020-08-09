package cga.exercise.components.animation

import org.joml.Matrix4f

/**
 *  Joint in a skeleton
 *  Contains index which determines the position of the joint matrix in the uniform array of the vertex shader.
 *  @param index ID of the joint indicates position of uniform array in vertex shader
 *  @param name name of the joint in collada file
 *  @param bindLocalTransform transformation of joint in relation to parent
 */

class Joint (val index:Int, val name:String, val localBindTransform: Matrix4f){

    var children:ArrayList<Joint> = ArrayList<Joint> ()    // children of the joint
    /* animatedTransform is the transformation that gets loaded in the shader and is used to deform vertices of the skin.
    Represents transformation from joint's bind position which is the original position in object space to the wanted animation pose. */
    var animatedTransform:Matrix4f = Matrix4f()   // transforms joint from origin to current pose
    var inverseBindTransform = Matrix4f()        // used to calculate the joint matrices in animator class
    private set

    /**
     * adds child to the joint used for the joint hierarchy
     * @param child child joint of this joint
     */
    fun addChild (child:Joint) = this.children.add(child)


    /**
     * Called during set up.
     * Calculates non-inverted bindTransform for this joint by multiplying the model space bind transform of the parent
     * with the local space bind transform of this joint to get the model space bind transform of the current joint.
     * @code bindTransform = parentBindTransform * localBindTransform
     * @param parentBindTransform model space bind transform of parent joint
     */
    fun calculateInverseBindTransform(parentBindTransform:Matrix4f) {
        val bindTransform = parentBindTransform.mul(localBindTransform)
        bindTransform.invert(inverseBindTransform)
        for(child in children) child.calculateInverseBindTransform(bindTransform)   // calculates recursively the inverseBindTransform for all of the children
    }
}