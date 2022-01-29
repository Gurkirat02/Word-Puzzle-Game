package com.sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class DraggableWord implements Drawable, Draggable{
    private String word;
    private double bottomLeftX;
    private double bottomLeftY;
    private Font wordFont = Font.font( "Courier New", FontWeight.BOLD, 30 );
    private boolean isSelected = false; //selected if true


    public DraggableWord(String aString, double x, double y){
        word = aString;
        bottomLeftX = x;
        bottomLeftY = y;
        initializeTextDimensions();
    }

    public void setText(String aString){word = aString;}
    public String getText(){return word;}
    public void setSelected(boolean selectedIfTrue){isSelected = selectedIfTrue;}
    public boolean getSelected(){return isSelected;}
    public Font getFont(){return wordFont;}
    public double getLocationX(){return bottomLeftX;}
    public double getLocationY(){return bottomLeftY;}

    //box around word for mouse targeting
    private double textBoundingBoxWidth;
    private double textBoundingBoxHeight;

    private void initializeTextDimensions(){
        //build a text object to represent the actual width of the greeting string
        Text theText = new Text(getText()); //create text object that can be measured
        theText.setFont(getFont()); //set font of the text object
        //get the width and height of the text object
        textBoundingBoxWidth = theText.getLayoutBounds().getWidth();
        textBoundingBoxHeight = theText.getLayoutBounds().getHeight()/2;
    }

    //interface Draggable Methods
    public boolean containsPoint(double targetX , double targetY){
        /*
        Answer whether the point targetX,targetY falls within this word
        */


        if (targetX < getLocationX()) return false;
        if (targetX > getLocationX() + textBoundingBoxWidth) return false;
        if (targetY > getLocationY()) return false;
        if (targetY < getLocationY() - textBoundingBoxHeight) return false;
        return true;
    }

    public void setLocation(double lowerLeftX, double lowerLeftY){
        bottomLeftX = lowerLeftX;
        bottomLeftY = lowerLeftY;
    }

    @Override
    public void moveBy(double deltaX, double deltaY) {
        bottomLeftX += deltaX;
        bottomLeftY += deltaY;
    }

    public void drawWith(GraphicsContext thePen){

        //set desired pen properties
        if(isSelected)
            thePen.setFill(highlightColor);
        else
            thePen.setFill(defaultDrawableFillColor);
        thePen.setStroke(defaultDrawableStrokeColor);
        thePen.setLineWidth(2);
        thePen.setFont(wordFont );

        //draw the word
        thePen.fillText(word, getLocationX(), getLocationY());
        thePen.strokeText(getText(), getLocationX(), getLocationY());

        if(isSelected) {
            //Draw a bounding box around the word to represent the target area.
            thePen.setStroke(Color.YELLOW);
            //draw the paddle
            thePen.strokeRect(bottomLeftX, //upper left X
                    bottomLeftY - textBoundingBoxHeight, //upper left Y
                    textBoundingBoxWidth, //width
                    textBoundingBoxHeight); //height
        }

    }

    public static ArrayList<String> sampleWords(){
        String someWords = "a so had he mind oh can't and a just his his from who broke now man pluck but songs it There never Nantucket would strings all he was young guitar said he sing";
        ArrayList<String> wordList = new ArrayList<>();
        for(String word : someWords.split(" ")){
            wordList.add(word);
        }
        return wordList;
    }

}
