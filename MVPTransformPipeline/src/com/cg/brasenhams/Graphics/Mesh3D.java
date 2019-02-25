package com.cg.brasenhams.Graphics;

import com.cg.brasenhams.BaseOperation.Matrix4x4;
import com.cg.brasenhams.BaseOperation.Vector3;
import com.cg.brasenhams.BaseOperation.Vector4;
import javafx.scene.shape.Mesh;

import java.util.ArrayList;
import java.util.List;

public class Mesh3D extends CGObject {
    public List<Face> FaceList;
    public List<VertexF> Vertices;
    public List<Vector4> FaceNormals;

    private boolean mHasNormals;


    public Mesh3D()
    {
        super();
        FaceList = new ArrayList<>();
        Vertices = new ArrayList<>();
        FaceNormals = new ArrayList<>();
    }

    public Mesh3D addFace(Face face)
    {
        FaceList.add(face);
        return this;
    }


    public Mesh3D addVertex(VertexF vertexF)
    {
        Vertices.add(vertexF);
        return this;
    }

    public Vector3 getLocalPosition()
    {
        float dx=0;
        float dy=0;
        float dz=0;
        float count=0;

         for (VertexF vertexF:Vertices)
         {
             count++;
             dx+=vertexF.getInnerVector().getX();
             dy+=vertexF.getInnerVector().getY();
             dz+=vertexF.getInnerVector().getZ();
         }


        return new Vector3(dx/count,dy/count,dz/count);
    }

    public List<Face> getTransformedFaces(Matrix4x4 transform)
    {
       /* List<Face> copyFace = new ArrayList<>();
        for (Face face: FaceList)
        {
            copyFace.add(face.clone());
        }
        for(Face face : copyFace)
        {
            for (VertexF vertexF : face.Vertices)
            {
                vertexF.tranform(transform);
                vertexF.divideByW();
            }
        }*/
       // return copyFace;
        return null;
    }

    public List<VertexF> getTransformedVertices(Matrix4x4 transform)
    {
        List<VertexF> copyVertex = new ArrayList<>();
        for (VertexF vertexF: Vertices)
        {
            VertexF cloneVertex = vertexF.clone();
            cloneVertex.tranform(transform);
            cloneVertex.divideByW();
            copyVertex.add(cloneVertex);
        }
        return copyVertex;
    }

    public List<Vector4> getTransformedFaceNormals(Matrix4x4 transform) {
        List<Vector4> copyList = new ArrayList<>();
        for (Vector4 normal: FaceNormals)
        {
            Vector4 cloneNormal = transform.multiply(normal.clone());

            copyList.add(cloneNormal);
        }
        return copyList;
    }

    public VertexF getTransformedVertex(int i, Matrix4x4 transform)
    {
        VertexF vertexF = Vertices.get(i).clone();
        vertexF.tranform(transform);
        return  vertexF;
    }

    public void computeNormals()
    {
        for (Face face : FaceList)
        {
            VertexF first = Vertices.get(face.Vertices.get(0));
            VertexF second = Vertices.get(face.Vertices.get(1));
            VertexF third = Vertices.get(face.Vertices.get(2));
            Vector4 firstEdge = new Vector4(first.getInnerVector(),second.getInnerVector());
            Vector4 secondEdge = new Vector4(first.getInnerVector(),third.getInnerVector());
            FaceNormals.add(firstEdge.cross(secondEdge));
        }
        mHasNormals = true;
    }

    public boolean hasNormals() {
        return mHasNormals;
    }

}
