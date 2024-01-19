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



    public void handleSweep(String direction) {
        boolean[][] merged = new boolean[gridHeight][gridWidth]; // Keep track of merges

        if (direction.equals("LEFT") || direction.equals("RIGHT")) {
            // Move and merge from left-to-right or top-to-bottom
            for (int row = 0; row < gridHeight; row++) {
                for (int col = 0; col < gridWidth; col++) {
                    moveAndMergeTiles(row, col, direction, merged);
                }
            }
        } else {
            // Move and merge from right-to-left or bottom-to-top
            for (int row = gridHeight - 1; row >= 0; row--) {
                for (int col = gridWidth - 1; col >= 0; col--) {
                    moveAndMergeTiles(row, col, direction, merged);
                }
            }
        }
    }

    private void moveAndMergeTiles(int row, int col, String direction, boolean[][] merged) {
        if (gameBlockMatrix[row][col].getValue() == 0) {
            return; // Skip empty cells
        }

        int nextRow = row, nextCol = col;
        while (nextCellAvailable(direction, nextRow, nextCol)) {
            // Move to the next cell in the direction
            if (direction.equals("UP")) nextRow--;
            else if (direction.equals("DOWN")) nextRow++;
            else if (direction.equals("LEFT")) nextCol--;
            else if (direction.equals("RIGHT")) nextCol++;

            if (nextRow < 0 || nextRow >= gridHeight || nextCol < 0 || nextCol >= gridWidth) {
                break; // Break if out of grid bounds
            }

            if (gameBlockMatrix[nextRow][nextCol].getValue() != 0) {
                if (gameBlockMatrix[nextRow][nextCol] == gameBlockMatrix[row][col] && !merged[nextRow][nextCol]) {
                    // Merge tiles
                    gameBlockMatrix[nextRow][nextCol].setValue(gameBlockMatrix[nextRow][nextCol].getValue() * 2);
                    gameBlockMatrix[row][col].setValue(0);
                    merged[nextRow][nextCol] = true;
                }
                break; // Stop moving if we hit a non-matching tile
            } else {
                // Move tile
                gameBlockMatrix[nextRow][nextCol] = gameBlockMatrix[row][col];
                gameBlockMatrix[row][col].setValue(0);
            }
        }
    }


    public boolean nextCellAvailable (String direction, int row, int col) {

        switch (direction){
            case "UP":
                if (row > 0 && gameBlockMatrix[row-1][col].getValue() == 0) {return true;}
                break;
            case "DOWN":
                if (row < gameBlockMatrix.length-1 && gameBlockMatrix[row+1][col].getValue() == 0) {return true;}
                break;
            case "LEFT":
                if (col > 0 && gameBlockMatrix[row][col-1].getValue() == 0) {return true;}
                break;
            case "RIGHT":
                if (col < gameBlockMatrix[row].length-1 && gameBlockMatrix[row][col+1].getValue() == 0) {return true;}
                break;
        }
        return false;
    }

    public void addGameBlockToMatrix(GameBlock gb) {
        gameBlockMatrix[gb.getPosX()][gb.getPosY()] = gb;
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

    public GameBlock[][] getGameBlockMatrix() {
        return gameBlockMatrix;
    }
}
