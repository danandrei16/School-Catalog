package Tema_POO;

import java.util.*;

public class FullCourse extends Course {
    public FullCourse(CourseBuilder builder) {
        super(builder);
    }

    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> promotedStudents = new ArrayList<>();
        for (Grade grade : getGrades())
            if (grade.getPartialScore() >= 3 && grade.getExamScore() >= 2)
                promotedStudents.add(grade.getStudent());
        return promotedStudents;
    }

    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String name, int credits) {
            super(name, credits);
        }

        @Override
        public CourseBuilder setStrategy(BestScoreStrategy strategy) {
            super.strategy = strategy;
            return this;
        }
        @Override
        public FullCourseBuilder setProfessor(Teacher professor) {
            super.professor = professor;
            return this;
        }

//        public CourseBuilder setAssistants(HashSet<Assistant> assistants) {
//            super.assistants = assistants;
//            return this;
//        }
//
//        public CourseBuilder setGrades(ArrayList<Grade> grades) {
//            super.grades = grades;
//            return this;
//        }
//
//        public CourseBuilder setGroups(HashMap<String, Group> groups) {
//            super.groups = groups;
//            return this;
//        }
//
//        public CourseBuilder setStrategy(BestScoreStrategy strategy) {
//            super.strategy = strategy;
//            return this;
//        }
        public FullCourse build() {
            return new FullCourse(this);
        }
    }
}
