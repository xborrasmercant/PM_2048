    package com.example.a2048;

    import android.content.Context;
    import android.util.Log;
    import android.widget.GridLayout;

    import androidx.appcompat.view.ContextThemeWrapper;

    public class GameGrid extends GridLayout {
        private int gridWidth, gridHeight;
        private final GameBlock[][] gameBlockMatrix;
        Context gameBlockStyledContext = new ContextThemeWrapper(this.getContext(), R.style.GameBlockStyle); // Context with custom style is created


        public GameGrid(Context context, int gridWidth, int gridHeight) {
            super(context);
            this.gridWidth = gridWidth;
            this.gridHeight = gridHeight;
            this.gameBlockMatrix = new GameBlock[gridHeight][gridWidth];
            this.initGrid();
            this.valueToRandomGameBlock();
        }

        public void valueToRandomGameBlock() {
            int counter = 0;
            int randomWidth = (int) (Math.random() * gridWidth);
            int randomHeight = (int) (Math.random() * gridHeight);
            boolean gameOver = false;

            // If random position value isn't empty (0) regenerate a random position.
            while (gameBlockMatrix[randomWidth][randomHeight].getValue() != 0 && !gameOver) {
                if (counter > gridWidth + gridHeight) {
                    gameOver = true;
                }

                randomWidth = (int) (Math.random() * gridWidth);
                randomHeight = (int) (Math.random() * gridHeight);

                counter++;
            }

            if (gameOver) {
                // Player loses
            } else {
                gameBlockMatrix[randomWidth][randomHeight].setValue(2); // Add new gameBlock
            }
        }


        public void handleSweep(String direction) {
            boolean[][] merged = new boolean[gridHeight][gridWidth]; // Keep track of merges

            if (direction.equals("LEFT") || direction.equals("UP")) {
                // Move and merge from left-to-right or top-to-bottom
                for (int row = 0; row < gridHeight; row++) {
                    for (int col = 0; col < gridWidth; col++) {
                        Log.d("Direction", "Direction: " + direction);
                        moveAndMergeTiles(row, col, direction, merged);
                    }
                }
            } else if (direction.equals("RIGHT") || direction.equals("DOWN")) {

                for (int row = gridHeight - 1; row >= 0; row--) {
                    for (int col = gridWidth - 1; col >= 0; col--) {
                        Log.d("Direction", "Direction: " + direction);
                        moveAndMergeTiles(row, col, direction, merged);
                    }
                }
            }
        }

        private void moveAndMergeTiles(int row, int col, String direction, boolean[][] merged) {
            boolean sweepHandled = false;

            if (gameBlockMatrix[row][col].getValue() == 0 || merged[row][col]) {
                return; // Skip empty cells
            }

            int currentRow = row, currentCol = col;
            int nextRow = row, nextCol = col;

            switch (direction) {
                case "UP":
                    nextRow--;
                    break;
                case "DOWN":
                    nextRow++;
                    break;
                case "LEFT":
                    nextCol--;
                    break;
                case "RIGHT":
                    nextCol++;
                    break;
            }

            // While in bounds
            while (inBounds(nextRow, nextCol, gridHeight, gridWidth) && !sweepHandled) {
                if (this.gameBlockMatrix[nextRow][nextCol].getValue() == 0) {
                    // Move the tile
                    this.gameBlockMatrix[nextRow][nextCol].setValue(gameBlockMatrix[currentRow][currentCol].getValue());
                    this.gameBlockMatrix[currentRow][currentCol].setValue(0);
                    currentRow = nextRow;
                    currentCol = nextCol;
                } else if (gameBlockMatrix[currentRow][currentCol].getValue() == gameBlockMatrix[nextRow][nextCol].getValue() && !merged[nextRow][nextCol]) {
                    // Merge the tiles
                    gameBlockMatrix[nextRow][nextCol].setValue(gameBlockMatrix[nextRow][nextCol].getValue() * 2);
                    gameBlockMatrix[currentRow][currentCol].setValue(0);
                    merged[nextRow][nextCol] = true;
                    sweepHandled = true;
                } else {
                    sweepHandled = true; // Stop if we hit a non-matching tile
                }

                if (direction.equals("UP")) nextRow--;
                else if (direction.equals("DOWN")) nextRow++;
                else if (direction.equals("LEFT")) nextCol--;
                else if (direction.equals("RIGHT")) nextCol++;

            }
        }

        public boolean inBounds(int nextRow, int nextCol, int height, int width) {
            if (nextRow >= 0 && nextRow < height && nextCol >= 0 && nextCol < width){
                return true;
            }
            else {
                return false;
            }
        }


        public void addGameBlockToMatrix(GameBlock gb) {
            gameBlockMatrix[gb.getPosX()][gb.getPosY()] = gb;
        }

        public void initGrid() {

            for (int x = 0; x < gridWidth; x++) {
                for (int y = 0; y < gridHeight; y++) {
                    GameBlock newGameBlock = new GameBlock(gameBlockStyledContext, x, y, 0);

                    this.addGameBlockToMatrix(newGameBlock);
                }
            }
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
