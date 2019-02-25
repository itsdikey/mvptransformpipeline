package com.cg.brasenhams.Graphics;

import java.util.ArrayList;
import java.util.List;

public class CGObject {


    public List<ICGObjectUpdater> updateComponents;

    public CGObject()
    {
        updateComponents = new ArrayList<>();
        transform = new Transform();
    }

    public Transform transform;

    public Transform getTransform() {
        return transform;
    }

    public void update(float secs)
    {
        for (ICGObjectUpdater updater : updateComponents)
        {

            updater.update(this,secs);
        }
    }
}
