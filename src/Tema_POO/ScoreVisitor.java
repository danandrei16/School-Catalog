package Tema_POO;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreVisitor implements Visitor {
    private final Catalog catalog = Catalog.getInstance();

    // implementing Tuple (triplet)
    static class Tuple<T, S, U> {
        private final T arg1;
        private final S arg2;
        private final U arg3;

        public Tuple(T arg1, S arg2, U arg3) {
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public T getFirst() {
            return arg1;
        }

        public S getSecond() {
            return arg2;
        }

        public U getThird() {
            return arg3;
        }

        public String toString() {
            return "[" + arg1 + ", " + arg2 + ", " + arg3 + "]";
        }
    }

    public HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores;
    public HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores;

    public ScoreVisitor() {
        this.examScores = new HashMap<>();
        this.partialScores = new HashMap<>();
    }

    @Override
    public void visit(Assistant assistant) {
        for(int j = 0; j < catalog.getNumberOfCourses(); j++) {
            Course course = catalog.getCourse(j);
            for(Group group : course.getGroups().values()) {
                if(group.getAssistant() == assistant) {
                    for(int i = 0; i < group.numberOfStudents(); i++) {
                        Tuple<Student, String, Double> element = partialScores.get(assistant).get(i);
                        Student student = element.getFirst();
                        String courseName = element.getSecond();
                        Double partialScore = element.getThird();
                        // grade already exists (exam score already in catalog)
                        if(course.getGrade(student) != null) {
                            course.getGrade(student).setPartialScore(partialScore);
                        } else {
                            // creating grade, leaving exam score empty for now
                            Grade newGrade = new Grade();
                            newGrade.setStudent(student);
                            newGrade.setCourse(courseName);
                            newGrade.setPartialScore(partialScore);

                            course.addGrade(newGrade);
                        }
                        catalog.notifyObservers(course.getGrade(student));
                        partialScores.get(assistant).set(i, null);
                    }
                    // removing elements from dictionary (the ones that are now verified)
                    for(int i = 0; i < group.numberOfStudents(); i++) {
                        partialScores.get(assistant).removeIf(x -> x == null);
                    }
                }
            }
        }
    }

    @Override
    public void visit(Teacher teacher) {
        for(int j = 0; j < catalog.getNumberOfCourses(); j++) {
            Course course = catalog.getCourse(j);
            if(course.getProfessor() == teacher) {
                for(int i = 0; i < course.getNumberOfStudents(); i++) {
                    // student already has partial score, now adding the exam score
                    // else creating the grade with just the exam score
                    Tuple<Student, String, Double> element = examScores.get(teacher).get(i);
                    Student student = element.getFirst();
                    Double examScore = element.getThird();
                    if(course.getGrade(student) != null) {
                        course.getGrade(student).setExamScore(examScore);
                    }
                    else {
                        Grade newGrade = new Grade();
                        newGrade.setStudent(student);
                        newGrade.setCourse(element.getSecond());
                        newGrade.setExamScore(examScore);

                        course.addGrade(newGrade);
                    }
                    catalog.notifyObservers(course.getGrade(student));
                    examScores.get(teacher).set(i, null);
                }
                for(int i = 0; i < course.getNumberOfStudents(); i++) {
                    examScores.get(teacher).removeIf(x -> x == null);
                }
            }
        }
    }
}
