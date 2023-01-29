package Tema_POO;

import java.util.ArrayList;

public class Teacher extends User implements Element {
    //TODO : ArrayList of courses
    public ArrayList<Course> courses = new ArrayList<>(); // lista cursuri la care preda Teacher

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
