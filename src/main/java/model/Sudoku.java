package model;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Sudoku {

    private Model sudokuModel;
    private Map<String, IntVar> sudokuModelVariables = new HashMap<>();
    private Set<Point> clues = new HashSet<>();

    public Sudoku(){
        sudokuModel = new Model();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String name = String.format("g_%d_%d", i, j);
                IntVar var = sudokuModel.intVar(name, new int[]{1,2,3,4,5,6,7,8,9});
                sudokuModelVariables.put(name, var);
            }
        }
    }

    public IntVar getVar(int x, int y){
        String name = String.format("g_%d_%d", x, y);
        return sudokuModelVariables.get(name);
    }

    public int getValue(int x, int y){
        return getVar(x,y).getValue();
    }

    public Model getModel(){
        return sudokuModel;
    }

    public Sudoku addRowConstraints(){
        for (int i = 0; i < 9; i++) {
            IntVar[] row = {getVar(0,i), getVar(1,i), getVar(2,i),getVar(3,i),getVar(4,i),getVar(5,i),getVar(6,i),getVar(7,i),getVar(8,i)};
            sudokuModel.allDifferent(row).post();
        }
        return this;
    }

    public Sudoku addColumnConstraints(){
        for (int i = 0; i < 9; i++) {
            IntVar[] row = {getVar(i,0), getVar(i,1), getVar(i,2),getVar(i,3),getVar(i,4),getVar(i,5),getVar(i,6),getVar(i,7),getVar(i,8)};
            sudokuModel.allDifferent(row).post();
        }
        return this;
    }

    public Sudoku addBoxConstraints(){
        for (int i : new int[]{0, 3, 6}) {
            for (int j : new int[]{0, 3, 6}) {
                IntVar[] box = {
                        getVar(i,j), getVar(i+1,j), getVar(i+2,j),
                        getVar(i,j+1),getVar(i+1,j+1),getVar(i+2,j+1),
                        getVar(i,j+2),getVar(i+1,j+2),getVar(i+2,j+2)};
                sudokuModel.allDifferent(box).post();
            }
        }
        return this;
    }

    public Sudoku addClueConstraint(int x, int y, int val){
        sudokuModel.arithm(getVar(x,y), "=", val).post();
        clues.add(new Point(x, y));
        return this;
    }

    public int getNumberOfClues(){
        return clues.size();
    }

    public String toString(){
        return toString(false);
    }

    public String toString(boolean hintsOnly){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(hintsOnly) {
                    if (clues.contains(new Point(i, j)))
                        buffer.append(" " + getVar(i, j).getValue() + " ");
                    else
                        buffer.append(" . ");
                }else
                    buffer.append(" " + getVar(i, j).getValue() + " ");
                if(j==2 || j == 5)
                    buffer.append("|");
            }
            if(i==2 || i==5)
                buffer.append("\n---------+---------+--------");
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public boolean hasUniqueSolution(){
        Solver solver = sudokuModel.getSolver();
        solver.reset();
        int n = 0;
        while(solver.solve()){
            n++;
            if(n > 1)
                break;
        }
        return n == 1;
    }

    public boolean hasAtLeastOneSolution(){
        Solver solver = sudokuModel.getSolver();
        solver.reset();
        return solver.solve();
    }

    public Sudoku solve(){
        Solver solver = sudokuModel.getSolver();
        solver.reset();
        solver.solve();
        return this;
    }

}
