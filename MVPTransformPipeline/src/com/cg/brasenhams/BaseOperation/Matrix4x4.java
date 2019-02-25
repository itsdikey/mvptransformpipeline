package com.cg.brasenhams.BaseOperation;

public class  Matrix4x4 {
    private float[][] mElements;
    public Matrix4x4()
    {
        mElements = new float[4][4];
    }

    public Matrix4x4 multiply(Matrix4x4 other)
    {
        Matrix4x4 result = new Matrix4x4();
        for(int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                for (int ctr = 0; ctr<4;ctr++)
                {
                    result.mElements[i][j] += mElements[i][ctr]*other.mElements[ctr][j];
                }
            }
        }
        return result;
    }

    public Vector3 multiply(Vector3 vector)
    {
        Vector3 result = new Vector3();
        for(int row=0;row<3;row++)
        {
            for(int col=0;col<3;col++)
            {
                result.elements[row] += vector.elements[col]*mElements[row][col];
            }
        }
        return result;
    }

    public Vector4 multiply(Vector4 vector)
    {
        Vector4 result = new Vector4();
        for(int row=0;row<4;row++)
        {
            for(int col=0;col<4;col++)
            {
                result.elements[row] += vector.elements[col]*mElements[row][col];
            }
        }
        return result;
    }

    public static Matrix4x4 getIdentity()
    {
        Matrix4x4 identity = new Matrix4x4();
        identity.mElements[0][0] = identity.mElements[1][1] = identity.mElements[2][2] = identity.mElements[3][3] = 1;
        return identity;
    }

    public static Matrix4x4 createRotationX(float angleX)
    {
        Matrix4x4 rotation = getIdentity();
        //around x axis

        rotation.mElements[1][1] = (float)Math.cos(angleX);
        rotation.mElements[1][2] = -(float)Math.sin(angleX);
        rotation.mElements[2][1] =  (float)Math.sin(angleX);
        rotation.mElements[2][2] = (float)Math.cos(angleX);
        return  rotation;
    }

    public static Matrix4x4 createRotationY(float angleX)
    {
        Matrix4x4 rotation = getIdentity();
        //around y axis

        rotation.mElements[0][0] = (float)Math.cos(angleX);
        rotation.mElements[0][2] = (float)Math.sin(angleX);
        rotation.mElements[2][0] =  -(float)Math.sin(angleX);
        rotation.mElements[2][2] = (float)Math.cos(angleX);
        return  rotation;
    }

    public static Matrix4x4 createRotationZ(float angleX)
    {
        Matrix4x4 rotation = getIdentity();
        //around z axis

        rotation.mElements[0][0] = (float)Math.cos(angleX);
        rotation.mElements[0][1] = -(float)Math.sin(angleX);
        rotation.mElements[1][0] =  (float)Math.sin(angleX);
        rotation.mElements[1][1] = (float)Math.cos(angleX);
        return  rotation;
    }

    public static Matrix4x4 createTranslation(float tx,float ty, float tz)
    {
        Matrix4x4 translation = getIdentity();
        translation.mElements[0][3] = tx;
        translation.mElements[1][3] = ty;
        translation.mElements[2][3] = tz;
        return  translation;
    }

    public static Matrix4x4 createScaling(float sx,float sy, float sz)
    {
        Matrix4x4 scaling = getIdentity();
        scaling.mElements[0][0] = sx;
        scaling.mElements[1][1] = sy;
        scaling.mElements[2][2] = sz;
        return  scaling;
    }

    public static Matrix4x4 createProjection(float fov, float aspectRatio, float nearZ, float farZ)
    {
        float oneOverTangent = 1/((float)Math.tan(Math.toRadians(fov)/2));
        Matrix4x4 projection = Matrix4x4.getIdentity();
        projection.mElements[0][0] = oneOverTangent/aspectRatio;
        projection.mElements[1][1] = oneOverTangent;
        projection.mElements[2][2] = -(farZ+nearZ)/(farZ-nearZ);
        projection.mElements[2][3] = -(2*farZ*nearZ)/(farZ-nearZ);
        projection.mElements[3][2] = -1;
        projection.mElements[3][3] = 0;
        return projection;

    }
}
