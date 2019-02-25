package com.cg.brasenhams;

import com.cg.brasenhams.Graphics.*;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;

public class MyScene extends Scene {


    Mesh3D cube;

    public MyScene()
    {
        super();
        initScene();
    }


    public void handleInput(KeyEvent e)
    {

        if (e.getKeyCode() == KeyCode.R.getCode())
        {
            cube.getTransform().rotate(0,(float)Math.toRadians(10),0);
        }

        if (e.getKeyCode() == KeyCode.E.getCode())
        {
            cube.getTransform().rotate(0,-(float)Math.toRadians(10),0);
        }

        if (e.getKeyCode() == KeyCode.W.getCode())
        {
            this.main.getTransform().translate(0,0,-1.5f);
        }

        if (e.getKeyCode() == KeyCode.S.getCode())
        {
            this.main.getTransform().translate(0,0,1.1f);
        }
        if (e.getKeyCode() == KeyCode.A.getCode())
        {
            this.main.getTransform().translate(-1.1f,0,0);
        }

        if (e.getKeyCode() == KeyCode.D.getCode())
        {
            this.main.getTransform().translate(1.1f,0,0);
        }

        if (e.getKeyCode() == KeyCode.K.getCode())
        {
            this.main.getTransform().rotate(0,(float)Math.toRadians(4),0);
        }

        if (e.getKeyCode() == KeyCode.L.getCode())
        {
            this.main.getTransform().rotate(0,-(float)Math.toRadians(4),0);
        }


        if (e.getKeyCode() == KeyCode.U.getCode())
        {
            this.main.setFOV(this.main.getFOV()+5);
        }


        if (e.getKeyCode() == KeyCode.I.getCode())
        {
            this.main.setFOV(this.main.getFOV()-5);
        }

        if (e.getKeyCode() == KeyCode.B.getCode())
        {
            this.setBackFaceCulling(!this.backFaceCulling());
        }

        if (e.getKeyCode() == KeyCode.V.getCode())
        {
            this.setViewFrustrumCulling(!this.hasViewFrustrumCulling());
        }

    }


    private void initScene() {

        cube = InitCube();
        Mesh3D secondCube = InitCube();
        Mesh3D thirdCube = InitCube();

        secondCube.updateComponents.add(
                (cgObject, timeInSecs) -> {
                    float targetRot = (float)Math.toRadians(45)*timeInSecs;
                    cgObject.getTransform().rotate(0,targetRot,0);
                });

        thirdCube.updateComponents.add(
                (cgObject, timeInSecs) -> {
                    float targetRot = -(float)Math.toRadians(45)*timeInSecs;
                    cgObject.getTransform().rotate(0,targetRot,0);
                });


        this.Meshes.add(cube);
        this.Meshes.add(secondCube);
        this.Meshes.add(thirdCube);

        for (int i = 0; i<40;i++)
            this.Meshes.add(InitStar());

        Camera camera = new Camera();
        camera.setNearZ(0.1f);
        camera.setFarZ(100);
        camera.setFOV(90);
        camera.setCurrentScreen(new Camera.Screen(640,480));
        this.main = camera;

        cube.transform.translate(0,0,-5);
        secondCube.transform.translate(6,0,-8);

        thirdCube.transform.translate(-6,0,-8);

    }

    private static Mesh3D InitStar()
    {
        Mesh3D star = new Mesh3D();

        star.addVertex(VertexF.fromCoordinates(-0.5f,0,0))
                .addVertex(VertexF.fromCoordinates(0,1,0))
                .addVertex(VertexF.fromCoordinates(0.5f,0,0))
                .addVertex(VertexF.fromCoordinates(0,-1,0));

        star.addFace(new Face()
                .addVertex(0)
                .addVertex(1)
                .addVertex(2)
                .addVertex(3));

        star.getTransform().scale(0.8f,1,1);
        float randomSpeed = (float)Math.random()+0.5f;
        star.updateComponents.add(
                (cgObject, timeInSecs) -> {
                    float targetRot = (float)Math.toRadians(100*randomSpeed)*timeInSecs;
                    cgObject.getTransform().rotate(0,targetRot,0);

                });

        star.transform.translate((float)(Math.random()*100-50),(float)(Math.random()*100-50),(float)(Math.random()*10-50));

        star.computeNormals();
        return star;
    }

    private static Mesh3D InitCube() {
        Mesh3D cube = new Mesh3D();
        VertexF first = VertexF.fromCoordinates(-1,-1,1);
        VertexF second = VertexF.fromCoordinates(1,-1,1);
        VertexF third = VertexF.fromCoordinates(1,1,1);
        VertexF fourth = VertexF.fromCoordinates(-1,1,1);
        VertexF fifth = VertexF.fromCoordinates(-1,1,-1);
        VertexF sixth = VertexF.fromCoordinates(1,1,-1);
        VertexF seventh = VertexF.fromCoordinates(1,-1,-1);
        VertexF eight = VertexF.fromCoordinates(-1,-1,-1);

        cube.addVertex(first)
                .addVertex(second)
                .addVertex(third)
                .addVertex(fourth)
                .addVertex(fifth)
                .addVertex(sixth)
                .addVertex(seventh)
                .addVertex(eight);

        cube
                .addFace(
                        new Face()
                                .addVertex(3)
                                .addVertex(2)
                                .addVertex(1)
                                .addVertex(0))
                .addFace(
                        new Face()
                                .addVertex(0)
                                .addVertex(1)
                                .addVertex(6)
                                .addVertex(7))
                .addFace(
                        new Face()
                                .addVertex(7)
                                .addVertex(6)
                                .addVertex(5)
                                .addVertex(4)
                )
                .addFace(
                        new Face()
                                .addVertex(4)
                                .addVertex(5)
                                .addVertex(2)
                                .addVertex(3)
                )
                .addFace(
                        new Face()
                                .addVertex(4)
                                .addVertex(3)
                                .addVertex(0)
                                .addVertex(7)
                )
                .addFace(
                        new Face()
                                .addVertex(2)
                                .addVertex(5)
                                .addVertex(6)
                                .addVertex(1)
                );

        cube.computeNormals();
        return cube;
    }


}
