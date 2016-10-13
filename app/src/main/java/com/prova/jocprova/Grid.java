package com.prova.jocprova;

import java.util.Random;

public class Grid {

    private Col [][] array_cols;
    private Col [] colors;
    private int cols;
    private int rows;
    private int n_cols;

    public Grid(Grid g) {
        this.cols = g.getCols();
        this.rows = g.getRows();
        this.n_cols = g.getColors().length;
        this.colors = g.getColors();
        array_cols = new Col[cols][rows];
        for(int i = 0; i <cols; i++) {
            for (int j = 0; j < rows; j++) {
                array_cols[i][j] = new Col(0, Form.EMPTY);
            }
        }
    }


    /**
     * Must throw exceptions if rows or cols are negative.
     * @param rows
     * @param cols
     */
    public Grid(int rows, int cols, Col [] colors) {
        /*if(rows <0 || cols <0){
            // Throw exception;
        }*/
        this.cols = cols;
        this.rows = rows;
        this.colors = colors;
        this.n_cols = colors.length;
        array_cols = new Col[cols][rows];
        int max = rows*cols;
        Random r = new Random();
        for(int i = 0; i< rows*cols; i ++){
            double d = r.nextDouble();
            int aux=n_cols;
            for(int j = 0; j< n_cols; j++){
                if (d< (j+1)/(double)n_cols){
                    aux = j;
                    break;
                }
            }
            array_cols[i/rows][i%rows] = colors[aux];
        }
    }

    public Col[] getColors() {
        return colors;
    }

    public Col[][] getArray_cols() {
        return array_cols.clone();
    }

    public int getCols() {
        return cols;
    }

    public void setCol(int col, int row, Col color){
        array_cols[col][row] = color;
        /*
        if (color.getF() == Form.EMPTY || array_cols[row][col].getF() != Form.EMPTY){
            // Throw Exception
        }
        boolean flag = false;
        for (Col c : colors){
            if (c == color){
                flag=true;
            }
        }
        if (!flag){
            //Throw Exception
        }*/

    }

    public int getRows() {
        return rows;
    }

    public int getN_cols() {
        return n_cols;
    }
}
