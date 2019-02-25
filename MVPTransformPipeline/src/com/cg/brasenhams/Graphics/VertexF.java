package com.cg.brasenhams.Graphics;

import com.cg.brasenhams.BaseOperation.Matrix4x4;
import com.cg.brasenhams.BaseOperation.Vector4;

public class VertexF {

    private Vector4 mInnerVector;

    public static VertexF fromCoordinates(float x, float y,float z)
    {
        VertexF vertexF = new VertexF();
        vertexF.mInnerVector = new Vector4(x,y,z,1);
        return vertexF;
    }

    public void tranform(Matrix4x4 matrix4x4)
    {
        mInnerVector = matrix4x4.multiply(mInnerVector);
    }

    public Vector4 getInnerVector() {
        return mInnerVector;
    }

    public void divideByW()
    {
        mInnerVector.setX(mInnerVector.getX()/mInnerVector.getW());
        mInnerVector.setY(mInnerVector.getY()/mInnerVector.getW());
        mInnerVector.setZ(mInnerVector.getZ()/mInnerVector.getW());
        mInnerVector.setW(mInnerVector.getW()/mInnerVector.getW());
    }

    public VertexF clone()
    {
        VertexF vertexF = new VertexF();
        vertexF.mInnerVector = new Vector4(mInnerVector.getX(),mInnerVector.getY(),mInnerVector.getZ(),mInnerVector.getW());
        return vertexF;
    }
}
