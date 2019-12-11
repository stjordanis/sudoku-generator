package constraints;

import model.Sudoku;
import org.chocosolver.solver.variables.IntVar;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class KnightsMoveConstraint {

    public KnightsMoveConstraint(Sudoku sudoku){
        for (int i : new int[]{1,4,7}) {
            for (int j : new int[]{1,4,7}) {
                Point[] kmoves = kmove(i,j);
                if(kmoves.length == 0)
                    continue;
                IntVar srcVar = sudoku.getVar(i,j);
                for (int k = 0; k < kmoves.length; k++) {
                    IntVar dstVar = sudoku.getVar(kmoves[k].x, kmoves[k].y);
                    sudoku.getModel().arithm(srcVar, "!=", dstVar).post();
                }
            }
        }
    }

    private Point[] kmove(int x, int y){
        Set<Point> tmp = new HashSet<>();
        for (int i : new int[]{-2,-1,1,2}) {
            for (int j : new int[]{-2,-1,1,2}) {
                if(java.lang.Math.abs(i) == java.lang.Math.abs(j))
                    continue;
                int x2 = x + i;
                int y2 = y + j;
                if(x2 < 0 || x2 >=9 || y2 < 0 || y2 >=9)
                    continue;
                tmp.add(new Point(x2, y2));
            }
        }
        return tmp.toArray(new Point[tmp.size()]);
    }

    public void check(Sudoku sudoku){
        for (int i : new int[]{1,4,7}) {
            for (int j : new int[]{1,4,7}) {
                Point[] kmoves = kmove(i,j);
                if(kmoves.length == 0)
                    continue;
                IntVar srcVar = sudoku.getVar(i,j);
                for (int k = 0; k < kmoves.length; k++) {
                    IntVar dstVar = sudoku.getVar(kmoves[k].x, kmoves[k].y);
                    if(srcVar.getValue() == dstVar.getValue())
                        System.out.println(String.format("constraints.KnightsMoveConstraint failed at (%d,%d) and (%d,%d)", i,j,kmoves[k].x,kmoves[k].y));
                }
            }
        }
    }
}
