package Tema_POO;

import java.util.ArrayList;

public class Assistant extends User implements Element {
    public ArrayList<Course> courses = new ArrayList<>();

    public Assistant(String firstName, String lastName) {
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
