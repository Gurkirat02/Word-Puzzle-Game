package com.sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public interface Drawable {
    public final static Color defaultDrawableFillColor = Color.RED;
    public final static Color defaultDrawableStrokeColor = Color.BLACK;
    public static final Color highlightColor = Color.YELLOW;

    public void drawWith(GraphicsContext thePen);
}
