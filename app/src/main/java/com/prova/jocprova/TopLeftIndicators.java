package com.prova.jocprova;

/**
 * Created by Juancki on 19/09/16.
 */

public class TopLeftIndicators {

    private int [][] topInd;
    private boolean [][] topIndCirc;
    private int [][] leftInd;
    private boolean [][] lefIndCirc;


    public int[][] getTopInd() {
        return topInd;
    }

    public boolean[][] getTopIndCirc() {
        return topIndCirc;
    }

    public boolean[][] getLefIndCirc() {
        return lefIndCirc;
    }

    public int[][] getLeftInd() {
        return leftInd;
    }

    public TopLeftIndicators(Grid grid) {
        int rows = grid.getRows();
        int cols = grid.getCols();
        Col [] colors = grid.getColors();
        int n_cols = colors.length;
        Col [][] array = grid.getArray_cols();
        this.leftInd = new int [rows][n_cols];
        this.lefIndCirc = new boolean[rows][n_cols];
        this.topInd = new int[cols][n_cols];
        this.topIndCirc = new boolean[cols][n_cols];
        for (int aux = 0 ; aux<n_cols;aux++){
            Col color = colors[aux];
            int status  =0;
            for (int row = 0; row <rows; row ++) {
                status = 0;
                for (int col = 0; col < cols; col++) {
                    switch (status) {
                        case 0:
                            if (color == array[col][row]) {
                                leftInd[row][aux]++;
                                status = 1;
                            }
                            break;
                        case 1:
                            if (color == array[col][row]) {
                                leftInd[row][aux]++;
                            } else {
                                status = 2;
                            }
                            break;
                        case 2:
                            if (color == array[col][row]) {
                                leftInd[row][aux]++;
                                status = 3;
                            }
                            break;
                        case 3:
                            if (color == array[col][row]) {
                                leftInd[row][aux]++;
                            }
                            break;
                    }
                }
                lefIndCirc [row][aux] = status==1|| status==2 ;
            }
        }

        for (int aux = 0 ; aux<n_cols;aux++) {
            Col color = colors[aux];
            int status = 0;
            for (int col = 0; col < cols; col++) {
                status =0 ;
                for (int row = 0; row < rows; row++) {
                    switch (status) {
                        case 0:
                            if (color == array[col][row]) {
                                topInd[col][aux]++;
                                status = 1;
                            }
                            break;
                        case 1:
                            if (color == array[col][row]) {
                                topInd[col][aux]++;
                            } else {
                                status = 2;
                            }
                            break;
                        case 2:
                            if (color == array[col][row]) {
                                topInd[col][aux]++;
                                status = 3;
                            }
                            break;
                        case 3:
                            if (color == array[col][row]) {
                                topInd[col][aux]++;
                            }
                            break;
                    }
                }
                topIndCirc [col][aux] = status==1 || status==2 ;
            }
        }
    }
}
