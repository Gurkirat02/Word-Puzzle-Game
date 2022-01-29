package com.sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line implements Drawable{

    private double width;
    private double height;
    private double topLeftX;
    private double topLeftY;
    private Color color;

    public Line(double x, double y, double dividerWidth, double dividerHeight, Color dividerColor){
        topLeftX = x;
        topLeftY = y;
        width = dividerWidth;
        height = dividerHeight;
        color = dividerColor;
    }

    public double getTopLeftX(){return topLeftX;}
    public double getTopLeftY(){return topLeftY;}
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    //add whatever get methods are required


    public void drawWith(GraphicsContext thePen){

        //set desired pen properties
        thePen.setFill(color);
        //draw the line
        thePen.fillRect(topLeftX,
                topLeftY,
                width,
                height);

    }
}
