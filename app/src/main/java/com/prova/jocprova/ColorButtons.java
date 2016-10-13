package com.prova.jocprova;

import android.graphics.Canvas;

/**
 * Created by Sebastià on 10/10/2016.
 */
public class ColorButtons {
    private int nButtons;
    private Col[] colors;
    private int sx;
    private int fx;
    private int sy;
    private int fy;
    private Canvas canvas;
    private int outerPadding;
    private int innerPadding;
    private int buttonSize;

    public ColorButtons(int fy, int sx, Col[] colors, int fx, int sy, Canvas canvas) {
        this.fy = fy;
        this.sx = sx;
        this.colors = colors;
        this.fx = fx;
        this.sy = sy;
        this.canvas = canvas;
        nButtons = colors.length+1;
        buttonSize = (sx-fx)/5;
    }

}
