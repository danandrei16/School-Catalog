package Tema_POO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Grade implements Comparable<Grade>, Cloneable {
    private Double partialScore = 0.00, examScore = 0.00;
    private Student student;
    private String course;

    // getters and setters
    public Double getPartialScore() {
        return this.partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return this.examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return this.course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getTotal() {
        return Double.valueOf(new DecimalFormat("0.00").format(this.partialScore + this.examScore));
    }

    //@Override
    public int compareTo(Grade o) {
        if(this.examScore > o.examScore)
            return 1;
        else if(this.examScore < o.examScore)
            return -1;
        else
            return 0;
    }

    public Object clone() {
        return this;
    }

    public String toString() {
        return student + " | Course: " + course + " | Partial Score: " + partialScore + " | Exam Score: "
                + examScore + " | Total Score: " + this.getTotal() + "\n";
    }

//    @Override
//    public Grade clone() {
//        try {
//            return (Grade) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
