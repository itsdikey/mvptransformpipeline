package com.cg.brasenhams.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Face {
    public List<Integer> Vertices;
    public Face()
    {
        Vertices = new ArrayList<>();
    }

    public Face addVertex(int index)
    {
        Vertices.add(index);
        return this;
    }

    public Face clone()
    {
        Face face = new Face();
        face.Vertices.addAll(Vertices);
        return  face;
    }
}
