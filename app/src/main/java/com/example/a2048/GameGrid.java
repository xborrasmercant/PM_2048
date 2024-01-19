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

    public int[] valueToRandomGameBlock() {
        int randomWidth = (int) (Math.random()*gridWidth);
        int randomHeight = (int) (Math.random()*gridHeight);

        // If random position value isn't empty (0) regenerate a random position.
        while (gameBlockMatrix[randomWidth][randomHeight].getValue() != 0) {
            randomWidth = (int) (Math.random()*gridWidth);
            randomHeight = (int) (Math.random()*gridHeight);
        }

        gameBlockMatrix[randomWidth][randomHeight].setValue(2);

        return new int[] {randomWidth, randomHeight};
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public void addGameBlockToMatrix(GameBlock gb) {
        gameBlockMatrix[gb.getPosX()][gb.getPosY()] = gb;
    }



    public GameBlock[][] getGameBlockMatrix() {
        return gameBlockMatrix;
    }
}
