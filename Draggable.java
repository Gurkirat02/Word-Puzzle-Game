package com.sample;

public interface Draggable {
    public boolean containsPoint(double targetX , double targetY);
    /*
    Answer whether the point (targetX, targetY) lies within the draggable
    */
    public void setLocation(double leftX, double bottomY); //set bottom left location
    public void moveBy(double deltaX, double deltaY); //move in x and y direction by delta amount
}
