package com.example.a2048;

import android.content.Context;
import android.widget.GridLayout;

public class GameGrid extends GridLayout {
    private int gridWidth, gridHeight;
    private final GameBlock[][] gameBlockMatrix;

    public GameGrid(Context context, int gridWidth, int gridHeight) {
        super(context);
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.gameBlockMatrix = new GameBlock[gridHeight][gridWidth];


    }


}
