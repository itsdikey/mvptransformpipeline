package com.cg.brasenhams.Graphics;

import com.cg.brasenhams.BaseOperation.Matrix4x4;
import com.cg.brasenhams.BaseOperation.Vector3;

public class Transform {
    public Vector3 position;
    public Vector3 eulerAngles;
    public Vector3 localScale;

    public Transform()
    {
        position = new Vector3();
        eulerAngles = new Vector3();
        localScale = new Vector3(1,1,1);
    }

    public void translate(float x, float y, float z)
    {
        position.setX(position.getX()+x);
        position.setY(position.getY()+y);
        position.setZ(position.getZ()+z);
    }

    public void rotate(float ax, float ay, float az)
    {
        eulerAngles.setX(eulerAngles.getX()+ax);
        eulerAngles.setY(eulerAngles.getY()+ay);
        eulerAngles.setZ(eulerAngles.getZ()+az);
    }

    public void scale(float sx, float sy, float sz)
    {
        localScale.setX(sx);
        localScale.setY(sy);
        localScale.setZ(sz);
    }

    public void uniformScale(float scaleFactor)
    {
        localScale.setX(scaleFactor*localScale.getX());
        localScale.setY(scaleFactor*localScale.getY());
        localScale.setZ(scaleFactor*localScale.getZ());
    }

    public Matrix4x4 getTranslationMatrix()
    {
        return Matrix4x4.createTranslation(position.getX(),position.getY(),position.getZ());
    }

    public Matrix4x4 getRotationMatrix()
    {
        return Matrix4x4.createRotationX(eulerAngles.getX())
                .multiply(Matrix4x4.createRotationY(eulerAngles.getY()))
                .multiply(Matrix4x4.createRotationZ(eulerAngles.getZ()));
    }

    public Matrix4x4 getScale()
    {
        return Matrix4x4.createScaling(localScale.getX(),localScale.getY(),localScale.getZ());
    }

    public Matrix4x4 getTransformMatrix()
    {
        return getTranslationMatrix().multiply(getRotationMatrix()).multiply(getScale());
    }


    public Matrix4x4 getInverseTranslationMatrix()
    {
        return Matrix4x4.createTranslation(-position.getX(),-position.getY(),-position.getZ());
    }

    public Matrix4x4 getInverseRotationMatrix()
    {
        return Matrix4x4.createRotationX(-eulerAngles.getX())
                .multiply(Matrix4x4.createRotationY(-eulerAngles.getY()))
                .multiply(Matrix4x4.createRotationZ(-eulerAngles.getZ()));
    }
}
