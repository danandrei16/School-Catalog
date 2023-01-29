package Tema_POO;

import java.util.ArrayList;

public class BestExamScore extends BestScoreStrategy {
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        double maxExamScore = -1;
        Student bestStudent = null;
        for(Grade grade : grades)
            if(grade.getExamScore() > maxExamScore) {
                maxExamScore = grade.getExamScore();
                bestStudent = grade.getStudent();
            }
        return bestStudent;
    }

    public String toString() {
        return "BestExamScore";
    }

}
