package constraints;

import model.Sudoku;

import java.util.Random;

public class MinimalSolutionConstraint {

    private Random random = new Random(System.currentTimeMillis());

    public MinimalSolutionConstraint(Sudoku sudoku){

        // solve
        sudoku.solve();

        // copy solution
        int[][] tmpSolution = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tmpSolution[i][j] = sudoku.getValue(i,j);
            }
        }

        // gradually fill in vars
        while(!sudoku.hasUniqueSolution()){
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            sudoku.addClueConstraint(x, y, tmpSolution[x][y]);
        }

    }
}
