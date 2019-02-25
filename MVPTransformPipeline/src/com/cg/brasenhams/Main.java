package com.cg.brasenhams;

import com.cg.brasenhams.BaseOperation.Vector2Int;
import com.cg.brasenhams.Graphics.*;
import com.cg.brasenhams.Plot.Plotter;
import com.cg.brasenhams.Utils.FPSCounter;
import com.cg.brasenhams.Utils.Time;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {


    private static Mesh3D cube;

    public static BufferedImage gradientSetRaster(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3]; //RGB

        for (int y = 0; y < height; y++) {
            int val = (int) (y * 255f / height);
            for (int shift = 1; shift < 3; shift++) {
                pixel[shift] = val;
            }

            for (int x = 0; x < width; x++) {
                raster.setPixel(x, y, pixel);

            }
        }

        return img;
    }



    public static void main(String... args) {
        Frame w = new Frame("Raster");  //window
        final int imageWidth = 640;
        final int imageHeight = 480;

        w.setSize(imageWidth,imageHeight);
        w.setLocation(100,100);
        w.setVisible(true);

        Graphics g = w.getGraphics();

        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        gradientSetRaster(img);
        g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);  //draw the image. You can think of this as the display method.

        

        MyScene scene = new MyScene();

        scene.renderScene(img.getRaster());
        g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);  //draw the image. You can think of this as the display method.
       /* w.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });*/
        Timer timer = new Timer();
        Time time = new Time();
        time.millis = System.currentTimeMillis();
        FPSCounter fpsCounter = new FPSCounter(10);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scene.update(System.currentTimeMillis()-time.millis);
                gradientSetRaster(img);
                scene.renderScene(img.getRaster());

                g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);
                fpsCounter.push((float)Math.ceil(1000f/(System.currentTimeMillis()-time.millis)));
                String fps = ("FPS:"+ fpsCounter.avgFPS());
               /* FontMetrics fm = g.getFontMetrics();
                Rectangle2D bounds = fm.getStringBounds(fps,g);*/
                g.setColor(Color.white);
                g.drawChars(fps.toCharArray(),0,fps.length(),10,45);
                time.millis = System.currentTimeMillis();

            }
        }, 1000/60, 1000/60);

        w.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                scene.handleInput(e);
            }
        });


        w.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                w.dispose();
                g.dispose();
                System.exit(0);
            }
        });
    }



}
