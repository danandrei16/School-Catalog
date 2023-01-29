package Tema_POO;

import java.util.ArrayList;

public class BestPartialScore extends BestScoreStrategy{
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        double maxPartialScore = -1;
        Student bestStudent = null;
        for(Grade grade : grades)
            if(grade.getPartialScore() > maxPartialScore) {
                maxPartialScore = grade.getPartialScore();
                bestStudent = grade.getStudent();
            }
        return bestStudent;
    }

    public String toString() {
        return "BestPartialScore";
    }
}
