package com.cg.brasenhams.Utils;

public class FPSCounter {
    private float[] fpsCount;
    private int mCurrentFPS;
    private int mHistory;
    public FPSCounter(int history)
    {
        fpsCount = new float[history];
        mHistory = history;
        mCurrentFPS = 0;
    }



    public void push(float fps)
    {
        fpsCount[mCurrentFPS++] = fps;
        mCurrentFPS%=mHistory;
    }

    public float avgFPS()
    {
        float cumulative=0;
        for(int i=0; i<mHistory;i++)
        {
            cumulative += fpsCount[i];
        }

        return cumulative/mHistory;
    }
}
