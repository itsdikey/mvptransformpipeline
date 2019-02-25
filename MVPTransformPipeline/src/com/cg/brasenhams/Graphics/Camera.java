package com.cg.brasenhams.Graphics;

import com.cg.brasenhams.BaseOperation.Matrix4x4;
import com.cg.brasenhams.BaseOperation.Vector2Int;

public class Camera extends CGObject {
    private float FOV;
    private float nearZ;
    private float farZ;
    private Screen currentScreen;

    public float getFOV() {
        return FOV;
    }

    public void setFOV(float FOV) {
        this.FOV = FOV;
        mCachedMatrixInvalid = true;
    }

    public float getNearZ() {
        return nearZ;
    }

    public void setNearZ(float nearZ) {
        this.nearZ = nearZ;
        mCachedMatrixInvalid = true;
    }

    public float getFarZ() {
        return farZ;
    }

    public void setFarZ(float farZ) {
        this.farZ = farZ;
        mCachedMatrixInvalid = true;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
        mCachedMatrixInvalid = true;
    }

    public static class Screen
    {
        public int screenX;
        public int screenY;

        public float getAspectRatio()
        {
            return ((float)screenX)/(float)screenY;
        }

        public Screen(int screenX, int screenY)
        {
            this.screenX = screenX;
            this.screenY = screenY;
        }

        public Vector2Int map(float x, float y)
        {

            int xInt =  (int)(x*screenX/2+screenX/2);
            int yInt = (int)(-y*screenY/2+screenY/2);
            return new Vector2Int(xInt,yInt);

        }
    }

    private Matrix4x4 mCachedProjectionMatrix;
    private boolean mCachedMatrixInvalid;

    public Matrix4x4 getProjectionMatrix()
    {
        if(mCachedProjectionMatrix!=null && !mCachedMatrixInvalid)
            return mCachedProjectionMatrix;
        mCachedProjectionMatrix = Matrix4x4.createProjection(FOV,currentScreen.getAspectRatio(),nearZ,farZ);
        mCachedMatrixInvalid=false;
        return mCachedProjectionMatrix;
    }
}
