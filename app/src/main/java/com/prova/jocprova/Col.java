package com.prova.jocprova;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Juancki on 15/09/16.
 */

public class Col {
    private int color;
    private Form f;


    public Col(int color, Form f) {
        this.color = color;
        this.f = f;
    }

    public int getC() {
        return color;
    }

    public Form getF() {
        return f;
    }


    public void drawColOnCanvas(Canvas c, int startX,int startY, int widht,int height,int padding,Paint p){
        switch (f){
            case CIRCLE:
                int r = widht/2-padding;
                c.drawCircle(startX+r, startY+r, r-padding, p);
                break;
            case SQUARE:
                c.drawRect(startX+padding,startY+padding,startX+ widht-padding,startY+height-padding,p);
        }
    }
    public void drawColOnCanvas(Canvas c, int startX,int startY, int widht,int height,int padding){

        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(4);
        switch (f){
            case CIRCLE:
                int r = widht/2;
                c.drawCircle(startX+r, startY+r, r-padding, p);
                break;
            case SQUARE:
                c.drawRect(startX+padding,startY+padding,startX+ widht-padding,startY+height-padding,p);
        }

        /*Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        drawColOnCanvas(c, startX,startY, finishX, finishY, padding, p);*/
    }

}
