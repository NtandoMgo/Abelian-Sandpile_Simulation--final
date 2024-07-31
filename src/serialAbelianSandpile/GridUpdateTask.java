package serialAbelianSandpile;

import java.util.concurrent.RecursiveTask;

public class GridUpdateTask extends RecursiveTask<Boolean> {
    private static final int THRESHOLD = 200;
    private int[][] myGrid;
    private int[][] myUpdateGrid;

    private int startRow, endRow;
    private int startCol, endCol;

    public GridUpdateTask(int[][] grid, int[][] updateGrid, int startRow, int endRow, int startCol, int endCol) {
        myGrid = grid;
        myUpdateGrid = updateGrid;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    @Override
    protected Boolean compute() {
        int area = (endRow - startRow) * (endCol - startCol); // sub grid area
        if (area <= THRESHOLD) {
            return updateGridSection();
        } else {
            // divide and conquer
            int midRow = (startRow + endRow) / 2;
            int midCol = (startCol + endCol) / 2;

            GridUpdateTask upLTask = new GridUpdateTask(myGrid, myUpdateGrid, startRow, midRow, startCol, midCol);
            GridUpdateTask upRTask = new GridUpdateTask(myGrid, myUpdateGrid, startRow, midRow, midCol, endCol);
            GridUpdateTask lowLTask = new GridUpdateTask(myGrid, myUpdateGrid, midRow, endRow, startCol, midCol);
            GridUpdateTask lowRTask = new GridUpdateTask(myGrid, myUpdateGrid, midRow, endRow, midCol, endCol);

            invokeAll(upLTask, upRTask, lowLTask, lowRTask); // starts all tasks concurrently

            // now each task must wait and get results
            return upLTask.join() || upRTask.join() || lowLTask.join() || lowRTask.join(); // true if at least 1 change was made
        }
    }

    private boolean updateGridSection() {
        boolean change = false;

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                myUpdateGrid[i][j] = (myGrid[i][j] % 4) +
                        (myGrid[i - 1][j] / 4) +
                        (myGrid[i + 1][j] / 4) +
                        (myGrid[i][j - 1] / 4) +
                        (myGrid[i][j + 1] / 4);
                if (myGrid[i][j] != myUpdateGrid[i][j]) {
                    change = true;
                }
            }
        }
        return change;
    }
}
