import constraints.DiagonalConstraint;
import constraints.KnightsMoveConstraint;
import constraints.MinimalSolutionConstraint;
import model.Sudoku;

public class Main {

    public static void main(String[] args) {

        int minNumberOfClues = 81;
        Sudoku minSudoku = null;

        for (int i = 0; i < 100; i++) {

            Sudoku s = new Sudoku().addRowConstraints()
                    .addColumnConstraints()
                    .addBoxConstraints();

            new DiagonalConstraint(s);
            new KnightsMoveConstraint(s);
            new MinimalSolutionConstraint(s);

            s.solve();


            if(s.getNumberOfClues() < minNumberOfClues){
                System.out.println(String.format("Found a sudoku with %d clues", s.getNumberOfClues()));
                minNumberOfClues = s.getNumberOfClues();
                minSudoku = s;
                if(minNumberOfClues <= 17)
                    break;
            }

        }

        new KnightsMoveConstraint(minSudoku).check(minSudoku);

        System.out.println(minSudoku);
        System.out.println(minSudoku.toString(true));
    }
}
