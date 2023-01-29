package Tema_POO;

import java.util.*;

public abstract class Course {

    private final String name;
    private final Teacher professor;
    private final HashSet<Assistant> assistants;
    private ArrayList<Grade> grades;
    private final HashMap<String, Group> groups;
    private final int credits;
    private BestScoreStrategy strategy;

    public Course(CourseBuilder builder) {
        this.name = builder.name;
        this.professor = builder.professor;
        this.credits = builder.credits;
        this.strategy = builder.strategy;

        this.groups = new HashMap<>();
        this.assistants = new HashSet<>();
        this.grades = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public Assistant getAssistant(int i) {
        Object[] assistantsArray = assistants.toArray();
        return (Assistant) assistantsArray[i];
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public Teacher getProfessor() {
        return professor;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public int getCredits() {
        return credits;
    }

    public BestScoreStrategy getStrategy() {
        return strategy;
    }

    // methods
    public void addAssistant(String ID, Assistant assistant) {
        // if assistant isn't in the set, add it
        if(this.assistants.contains(assistant) == false)
            this.assistants.add(assistant);

        // set the assistant to the group with the allocated ID
        Group group = this.groups.get(ID);
        group.setAssistant(assistant);
    }

    public void addStudent(String ID, Student student) {
        Group group = this.groups.get(ID);
        group.addStudent(student);
    }

    public void addGroup(Group group) {
        String ID = group.getID();
        this.groups.put(ID, group);
    }

    public void addGroup(String ID, Assistant assistant) {
        Group group = new Group(ID, assistant);
        this.groups.put(ID, group);
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        Group group = new Group(ID, assistant, comp);
        this.groups.put(ID, group);
    }

    public Grade getGrade(Student student) {
        for(Grade grade : grades)
            if(grade.getStudent() == student)
                return grade;
        // student not found
        return null;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students= new ArrayList<>();
        for(Group group : groups.values())
            for(int i = 0; i < group.numberOfStudents(); i++)
                students.add(group.getStudent(i));
        return students;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> studentGrades = new HashMap<>();
        // adding student from each group
        for(Student student: getAllStudents())
            studentGrades.put(student, getGrade(student));

        return studentGrades;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public Student getBestStudent() {
        return this.strategy.getBestStudent(grades);
    }

    public int getNumberOfStudents() {
        int nr = 0;
        for(Group group : groups.values())
            nr += group.numberOfStudents();
        return nr;
    }

    public String toString() {
        return "COURSE: " + name.toUpperCase() + "\n" +
                "  Teacher: " + professor + " | Credits: " + credits + " | Strategy: " + strategy;
    }

    public static abstract class CourseBuilder {
        String name;
        Teacher professor;
        HashSet<Assistant> assistants;
        ArrayList<Grade> grades;
        HashMap<String, Group> groups;
        int credits;
        BestScoreStrategy strategy;

        public CourseBuilder(String name, int credits) {
            this.name = name;
            this.credits = credits;
        }

        public abstract CourseBuilder setStrategy(BestScoreStrategy strategy);

        public abstract CourseBuilder setProfessor(Teacher professor);

        public abstract Course build();
    }

    public static class Snapshot {
        public ArrayList<Grade> grades;
        public Snapshot snapshot;

        public Snapshot(ArrayList<Grade> grades) {
            this.grades = grades;
        }
    }

    Snapshot snapshot = new Snapshot(grades);

    public void makeBackup() {
        Snapshot snapshot = new Snapshot(grades);
    }

    public void undo() {
        grades = snapshot.grades;
    }

}
