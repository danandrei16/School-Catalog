package Tema_POO;

import java.util.*;

public class PartialCourse extends Course {
    public PartialCourse(CourseBuilder builder) {
        super(builder);
    }

    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> promotedStudents = new ArrayList<>();
        for (Grade grade : getGrades())
            if (grade.getTotal() >= 5)
                promotedStudents.add(grade.getStudent());
        return promotedStudents;
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        public PartialCourseBuilder(String name, int credits) {
            super(name, credits);
        }

        @Override
        public CourseBuilder setProfessor(Teacher professor) {
            //super.setProfessor(professor);
            super.professor = professor;
            return this;
        }
//
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

        @Override
        public CourseBuilder setStrategy(BestScoreStrategy strategy) {
            super.strategy = strategy;
            return this;
        }
        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }
}
