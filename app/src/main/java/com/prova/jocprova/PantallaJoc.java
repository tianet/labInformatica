package com.prova.jocprova;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class PantallaJoc extends SurfaceView {
    private Context context;
    private int maxModelX, maxModelY;
    private float ratio;
    private int lastX, lastY;
    private SurfaceHolder holder;                // per gestionar el pintat de pantalla
    private LoopJoc jocLoopThread;               // controla el temps de dibuixat a pantalla
    private int radi;
    private Random ran = new Random();             // per generar n�meros aleatoris
    private Imatge num, myImatge;

    // Fancy screen
    double s_x;
    double s_y;
    double f_x;
    double f_y;
    double delta_x;
    double delta_y;
    int n_cols;
    int n; //Number of cells in the x axe.
    int m; //Number of cells in the y axe.
    double cellSizeX;
    double cellSizeY;


    private Grid grid;
    private Grid gameGrid;
    private TopLeftIndicators tli;
    private TopLeftIndicators userTli;



    public PantallaJoc(Context cont, Grid g, TopLeftIndicators t) {
        super(cont);
        this.context = cont;
        this.grid = new Grid(g);;
        this.tli = t;
        this.gameGrid = g;
        jocLoopThread = new LoopJoc(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder arg0) {
                iniciaJoc();
                partir();
                jocLoopThread.start();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            public void surfaceDestroyed(SurfaceHolder arg0) {
                aturar();
            }
        });
    }

    public void iniciaJoc() {
        maxModelX = 1080;
        maxModelY = 1583;
        s_x = 0 * maxModelX;
        s_y = 0.2 * maxModelX;
        f_x = 1 * maxModelX;
        f_y = 0.8 * maxModelY;
        delta_x = f_x - s_x;
        delta_y = f_y - s_y;
        n_cols = grid.getN_cols();
        n = grid.getCols() + grid.getN_cols();
        m = grid.getRows() + grid.getN_cols();
        cellSizeX = delta_x/n;
        cellSizeY = delta_y/m;
    }

    protected void pinta(Canvas canvas) {
        //float densitat = context.getResources().getDisplayMetrics().scaledDensity;

        if (canvas == null) return;

        // Quin és el ratio de conversió per la resolució d'aquesta pantalla ?
        float ratio1 = maxModelX / (float) this.getWidth();
        float ratio2 = maxModelY / (float) this.getHeight();
        if (ratio1 > ratio2) ratio = 1 / ratio1;
        else ratio = 1 / ratio2;


        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, paint);


        drawGridContent(canvas);
        drawGrid(grid.getCols(),grid.getRows(),grid.getN_cols(),s_x,s_y,f_x,f_y,canvas);
        drawNumbers(canvas);
    }


    public void drawGridContent(Canvas canvas){
        for (int j = n_cols; j < m; j++) {
            for (int i = n_cols; i < n; i++) {
                grid.getArray_cols()[i - n_cols][j - n_cols].drawColOnCanvas(canvas,
                        toScreen((int) (i * cellSizeX + s_x)), toScreen((int) (j * cellSizeY + s_y)),
                        toScreen((int) cellSizeX ), toScreen((int) cellSizeY),
                        5);
            }
        }
    }

    public void drawGrid (int nColumn, int nRow, int nCol , double sX, double sY, double fX, double fY,   Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        double cellSizeX = (fX-sX)/(nCol+nColumn);
        double cellSizeY = (fY-sY)/(nCol+nRow);

        double lengthX = (nRow+nCol+1)*cellSizeX;
        double lengthY = (nColumn+nCol-1)*cellSizeY;

        //Dibuixar línies verticals
        for (int i = 0; i < nCol+nColumn+1; i++) {
            if (i < nCol) {
                canvas.drawLine(toScreen((int) (sX+i*cellSizeX)), toScreen((int) (sY+cellSizeY*nCol)), toScreen((int) (sX+i*cellSizeX)), toScreen((int) (fY)), paint);
            } else {
                canvas.drawLine(toScreen((int) (sX+i*cellSizeX)), toScreen((int) (sY)), toScreen((int) (sX+i*cellSizeX)), toScreen((int) (fY)), paint);
            }
        }

        //Dibuixar línies horitzontals
        for (int i = 0; i < nRow+nCol+1; i++) {
            if (i < nCol) {
                canvas.drawLine(toScreen((int) (sX+cellSizeX*nCol)), toScreen((int) (sY+i*cellSizeY)), toScreen((int) (fX)), toScreen((int) (sY+i*cellSizeY)), paint);
            } else {
                canvas.drawLine(toScreen((int) (sX)), toScreen((int)(sY+i*cellSizeY)), toScreen((int) (fX)), toScreen((int) (sY+i*cellSizeY)), paint);
            }
        }
    }

    public  void drawButton(int s_x, int s_y, int f_x, int f_y) {
        int nColors = grid.getN_cols();
        int buttonSize = (int) (s_x-f_x)/(nColors+1);

    }
    public void drawNumbers ( Canvas canvas) {
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        double delta_x = f_x - s_x;
        double delta_y = f_y - s_y;
        double cellSizeX = (f_x-s_x)/(grid.getN_cols()+grid.getCols());
        double cellSizeY = (f_y-s_y)/(grid.getN_cols()+grid.getRows());
        int textSize;
        if (cellSizeX<cellSizeY) {
            textSize = (int) cellSizeX/2;
        } else {
            textSize = (int) cellSizeY/2;
        }
        int n = grid.getCols() + grid.getN_cols();
        int m = grid.getRows() + grid.getN_cols();
        int[][] topInd = tli.getTopInd();
        boolean[][] topIndCirc = tli.getTopIndCirc();
        boolean[][] leftIndCirc = tli.getLefIndCirc();
        int[][] leftInd = tli.getLeftInd();
        int y;
        int x;
        //Top Indicators
        for (int j = 0; j < grid.getCols(); j++) {
            x = (int) (s_x + cellSizeX*grid.getN_cols()+j*cellSizeX +cellSizeX/4);
            for (int i = 0; i < grid.getN_cols(); i++) {
                y = (int) (s_y + (grid.getN_cols()-i)*cellSizeY - 2*cellSizeY/5);
                escriuNormal(canvas, ""+ topInd[j][i], toScreen(textSize), grid.getColors()[i].getC(),toScreen(x),toScreen(y));
                if(topIndCirc[j][i]==true && topInd[j][i]>1) {
                    paint.setColor(grid.getColors()[i].getC());
                    canvas.drawCircle(toScreen((int) (s_x + cellSizeX*grid.getN_cols()+j*cellSizeX +cellSizeX/2)), toScreen((int) (s_y + (grid.getN_cols()-i)*cellSizeY - cellSizeY/2 )), toScreen(textSize-2), paint);
                }

            }
        }
        //Left Indicators
        for (int j = 0; j < grid.getRows(); j++) {
            y = (int) (s_y + (grid.getN_cols()+j+1)*cellSizeY - 2*cellSizeY/5);
            for (int i = 0; i < grid.getN_cols(); i++) {
                x = (int) (s_x + cellSizeX*grid.getN_cols()-(i+1)*cellSizeX +cellSizeX/4);
                escriuNormal(canvas, ""+ leftInd[j][i], toScreen(textSize), grid.getColors()[i].getC(),toScreen(x),toScreen(y));
                if(leftIndCirc[j][i]==true && leftInd[j][i]>1) {
                    paint.setColor(grid.getColors()[i].getC());
                    canvas.drawCircle(toScreen((int) (s_x + cellSizeX*grid.getN_cols()-(i+1)*cellSizeX +cellSizeX/2)), toScreen((int) (s_y + (grid.getN_cols()+(j+1))*cellSizeY - cellSizeY/2 )), toScreen(textSize-2), paint);
                }

            }
        }



    }



    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                return true;
            }
            case MotionEvent.ACTION_UP: {
                int x = fromScreen((int) event.getX());
                int y = fromScreen((int) event.getY());
                if(x>s_x && x<f_x && y>s_y && y<f_y){
                    int p =(int) ((x-s_x)/cellSizeX);
                    int q =(int) ((y-s_y)/cellSizeY);
                    if (p > n_cols && q > n_cols){
                        if (grid.getArray_cols()[p-n_cols][q-n_cols].getF()==Form.EMPTY){
                            grid.setCol(p-n_cols,q-n_cols,new Col(Color.BLACK,Form.SQUARE));
                        }
                    }
                }
            }

        }
        return super.onTouchEvent(event);
    }

    public int toScreen(int in) {
        return (int) (in * ratio);
    }

    public int fromScreen(int in) {
        return (int) (in / ratio);
    }

    public void escriuCentrat(Canvas canvas, String missatge, int size, int color, int posicio) {
        Paint paint = new Paint();
        paint.setTypeface(((JocProvaActivity) context).fontJoc);
        paint.setTextSize(size);

        // Centrat horitzontal
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(color);
        paint.setAlpha(200);

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) (posicio - ((paint.descent() + paint.ascent())) / 4);

        canvas.drawText(missatge, xPos, yPos, paint);
    }

    public void escriuNormal(Canvas canvas, String missatge, int size, int color, int posicioX, int posicioY) {
        Paint paint = new Paint();
        paint.setTypeface(((JocProvaActivity) context).fontJoc);
        paint.setTextSize(size);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(color);
        paint.setAlpha(200);

        int yPos = (int) (posicioY - ((paint.descent() + paint.ascent())) / 4);

        canvas.drawText(missatge, posicioX, yPos, paint);
    }

    public void aturar() {
        jocLoopThread.setRunning(false);
    }

    public void partir() {
        jocLoopThread.setRunning(true);
    }

}