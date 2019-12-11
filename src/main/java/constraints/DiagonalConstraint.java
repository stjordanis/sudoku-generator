package constraints;

import model.Sudoku;
import org.chocosolver.solver.variables.IntVar;

public class DiagonalConstraint {

    private Sudoku sudoku;

    public DiagonalConstraint(Sudoku sudoku){

        this.sudoku = sudoku;

        IntVar[] diag0 = {getVar(0,0), getVar(1,1), getVar(2,2),getVar(3,3),getVar(4,4),getVar(5,5),getVar(6,6),getVar(7,7),getVar(8,8)};
        sudoku.getModel().allDifferent(diag0).post();

        IntVar[] diag1 = {getVar(8,0), getVar(7,1), getVar(6,2),getVar(5,3),getVar(4,4),getVar(3,5),getVar(2,6),getVar(1,7),getVar(0,8)};
        sudoku.getModel().allDifferent(diag1).post();
    }

    private IntVar getVar(int x, int y){
        return sudoku.getVar(x,y);
    }
}
