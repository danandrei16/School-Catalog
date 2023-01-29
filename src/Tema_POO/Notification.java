package Tema_POO;

public class Notification {
    Grade grade;

    public Notification(Grade grade) {
        this.grade = grade;
    }

    public String toString() {
        if (grade.getExamScore() == 0.00)
            return "New partial score for student: " + grade.getStudent().toString().toUpperCase() +
                    " in " + grade.getCourse().toUpperCase() + ": " + grade.getPartialScore();
        else
            return "New exam score for student: " + grade.getStudent().toString().toUpperCase() +
                    " in " + grade.getCourse().toUpperCase() + ": " + grade.getExamScore();
        //return "New grade for student " + grade.getStudent().toString().toUpperCase() + " in " + grade.getCourse() + ": " + grade;
    }
}
