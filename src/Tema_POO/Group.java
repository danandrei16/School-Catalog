package Tema_POO;

import java.util.*;

public class Group extends ArrayList<Student> {

    private Assistant assistant;
    private String ID;
    Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Student getStudent(int i) {
        return this.get(i);
    }

    public void addStudent(Student student) {
        this.add(student);
    }

    public int numberOfStudents() {
        return this.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Group students = (Group) o;
        return Objects.equals(assistant, students.assistant) && Objects.equals(ID, students.ID) && Objects.equals(comp, students.comp);
    }

    public String toString() {
        String ret = "Group ID: " + ID + "\nAssistant: " + assistant + "\nStudents:\n";
        for(int i = 0; i < this.size(); i++) {
            ret += " " + this.get(i) + "\n";
        }
        return ret;
    }
}
