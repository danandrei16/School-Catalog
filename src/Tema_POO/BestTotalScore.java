package Tema_POO;

import java.util.ArrayList;

public class BestTotalScore extends BestScoreStrategy{
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        double maxTotalScore = -1;
        Student bestStudent = null;
        for(Grade grade : grades)
            if(grade.getTotal() > maxTotalScore) {
                maxTotalScore = grade.getTotal();
                bestStudent = grade.getStudent();
            }
        return bestStudent;
    }

    public String toString() {
        return "BestTotalScore";
    }
}
