package Tema_POO;

import java.util.ArrayList;

public class Catalog implements Subject {
    private static Catalog catalog = new Catalog();
    private ArrayList<Course> courses = new ArrayList<>();

    private Catalog() {
    }

    public static Catalog getInstance() {
        return catalog;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Course getCourse(int index) {
        return courses.get(index);
    }

    public int getNumberOfCourses() {
        return courses.size();
    }

    public String toString() {
        String courseList = "";
        for(int i = 0; i < courses.size(); i++)
            courseList += courses.get(i) + "\n";
        return courseList;
    }

    // OBSERVER PATTERN
    ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Grade grade) {
        Student student = grade.getStudent();
        Grade tempGrade = new Grade();
        tempGrade.setCourse(grade.getCourse());
        tempGrade.setStudent(grade.getStudent());

        if(grade.getExamScore() == 0.00) {
            tempGrade.setPartialScore(grade.getPartialScore());
        }
        else {
            tempGrade.setExamScore(grade.getExamScore());
        }

        Notification notification = new Notification(tempGrade);

        for (Observer observer : observers) {
            // only notifying parents of respective student
            if(student.getMother() != null)
                if(student.getMother().equals(observer)) {
                    observer.update(notification);
                    if(student.getFather() != null)
                        student.getFather().update(notification);
                    break;
                }

            if(student.getFather() != null)
                if(student.getFather().equals(observer)) {
                    observer.update(notification);
                    if(student.getMother() != null)
                        student.getMother().update(notification);
                    break;
                }
        }
    }
}
