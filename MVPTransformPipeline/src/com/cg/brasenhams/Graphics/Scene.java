package com.cg.brasenhams.Graphics;

import com.cg.brasenhams.BaseOperation.Matrix4x4;
import com.cg.brasenhams.BaseOperation.Vector3;
import com.cg.brasenhams.BaseOperation.Vector4;
import com.cg.brasenhams.Plot.Plotter;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    public List<Mesh3D> Meshes;



    private boolean mBackFaceCulling = true;


    protected Camera main;

    private boolean mViewFrustrumCulling = true;

    public Scene()
    {
        Meshes = new ArrayList<>();
    }



    public void renderScene(WritableRaster raster)
    {
        if(main==null)
            return;

        for (Mesh3D mesh: Meshes) {
            Vector3 meshPosition = mesh.getLocalPosition();
            Matrix4x4 backToBase = Matrix4x4.createTranslation(-meshPosition.getX(),-meshPosition.getY(),-meshPosition.getZ());


            Matrix4x4 cameraTransform = main.getTransform().getInverseRotationMatrix().multiply(main.getTransform().getInverseTranslationMatrix());
            Matrix4x4 worldTransform = mesh.getTransform().getTransformMatrix();

            Matrix4x4 totalTransform = main.getProjectionMatrix().multiply(cameraTransform).multiply(worldTransform).multiply(backToBase);
            List<Vector4> faceNormals = null;
            if(mesh.hasNormals())
            {
                faceNormals = mesh.getTransformedFaceNormals(mesh.getTransform().getRotationMatrix().multiply(backToBase));
            }
            List<VertexF> transformedVertices = mesh.getTransformedVertices(totalTransform);
            List<Face> faceList = mesh.FaceList;
            for (int faceIndex = 0; faceIndex < faceList.size(); faceIndex++) {
                Face f = faceList.get(faceIndex);
                if (mBackFaceCulling && faceNormals != null) {
                    Vector4 normal = faceNormals.get(faceIndex).unit();
                    Vector4 viewVector = new Vector4(
                            new Vector4(main.getTransform().position),
                            mesh.getTransformedVertex(f.Vertices.get(0),worldTransform).getInnerVector())
                            .unit();

                    float dot = viewVector.dot(normal);
                    if(dot<0)
                    {
                        continue;
                    }
                }
                int i;
                for (i = 0; i < f.Vertices.size() - 1; i++) {
                    VertexF currentVertex = transformedVertices.get(f.Vertices.get(i));
                    VertexF nextVertex = transformedVertices.get(f.Vertices.get(i + 1));
                    if(belongsToPlane(currentVertex) && belongsToPlane(nextVertex))
                    {
                        Plotter.plotLine(main.getCurrentScreen().map(currentVertex.getInnerVector().getX(), currentVertex.getInnerVector().getY()),
                                main.getCurrentScreen().map(nextVertex.getInnerVector().getX(), nextVertex.getInnerVector().getY()),
                                raster);
                    }

                }

                VertexF currentVertex = transformedVertices.get(f.Vertices.get(i));
                VertexF nextVertex = transformedVertices.get(f.Vertices.get(0));
                if(belongsToPlane(currentVertex) && belongsToPlane(nextVertex))
                {
                    Plotter.plotLine(main.getCurrentScreen().map(currentVertex.getInnerVector().getX(), currentVertex.getInnerVector().getY()),
                            main.getCurrentScreen().map(nextVertex.getInnerVector().getX(), nextVertex.getInnerVector().getY()),
                            raster);
                }


            }
        }

    }

    private boolean belongsToPlane(VertexF currentVertex) {
        Vector4 innerVector = currentVertex.getInnerVector();
        for (int i=0; i<4;i++)
        {
            if(Float.isInfinite(innerVector.elements[i]))
                return false;
        }

        if(!hasViewFrustrumCulling())
            return true;

        return innerVector.getX()>=-1 && innerVector.getX()<=1
                && innerVector.getY()>=-1 && innerVector.getY()<=1
                && innerVector.getZ()>=-1 && innerVector.getZ()<=1;
    }

    public void update(long millis)
    {
        float inSecs = millis/1000f;
        for (Mesh3D mesh: Meshes)
        {
            mesh.update(inSecs);
        }
    }

    public boolean backFaceCulling() {
        return mBackFaceCulling;
    }

    public void setBackFaceCulling(boolean mBackFaceCulling) {
        this.mBackFaceCulling = mBackFaceCulling;
    }


    public boolean hasViewFrustrumCulling() {
        return mViewFrustrumCulling;
    }

    public void setViewFrustrumCulling(boolean mViewFrustrumCulling) {
        this.mViewFrustrumCulling = mViewFrustrumCulling;
    }
}
