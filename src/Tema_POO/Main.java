package Tema_POO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Main {
    ScoreVisitor scoreVisitor = new ScoreVisitor();

    public ScoreVisitor getScoreVisitor() {
        return scoreVisitor;
    }

    public Main(String fileName) throws Exception {
        try {
            Catalog catalog = Catalog.getInstance();
            ArrayList<Teacher> teachers = new ArrayList<>();
            ArrayList<Student> studentList = new ArrayList<>();

            UserFactory createUser = new UserFactory();

            FileReader file = new FileReader(fileName);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            JSONArray courses = (JSONArray) jsonObject.get("courses");

            for (Object courseObject : courses) {
                JSONObject courseJson = (JSONObject) courseObject;
                // course type
                String type = (String) courseJson.get("type");
                // course name
                String name = (String) courseJson.get("name");
                // course strategy
                String strategyName = (String) courseJson.get("strategy");
                BestScoreStrategy strategy;

                if (strategyName.equals("BestPartialScore"))
                    strategy = new BestPartialScore();
                else if (strategyName.equals("BestExamScore"))
                    strategy = new BestExamScore();
                else
                    strategy = new BestTotalScore();

                // creating teacher
                JSONObject teacherName = (JSONObject) courseJson.get("teacher");
                String teacherFirstName = (String) teacherName.get("firstName");
                String teacherLastName = (String) teacherName.get("lastName");

                User teacher = createUser.getUser("Teacher", teacherFirstName, teacherLastName);
                teachers.add((Teacher) teacher);
                // creating course (with name strategy and teacher so far)
                Course course;
                if (Objects.equals(type, "FullCourse"))
                    course = new FullCourse.FullCourseBuilder(name, 5)
                            .setProfessor((Teacher) teacher)
                            .setStrategy(strategy)
                            .build();
                else
                    course = new PartialCourse.PartialCourseBuilder(name, 2)
                            .setProfessor((Teacher) teacher)
                            .setStrategy(strategy)
                            .build();
                ((Teacher) teacher).addCourse(course);

                JSONArray groups = (JSONArray) courseJson.get("groups");
                for (Object groupObject : groups) {
                    JSONObject group = (JSONObject) groupObject;
                    // group id
                    String id = (String) group.get("ID");
                    // creating assistant and adding to group
                    JSONObject assistantName = (JSONObject) group.get("assistant");
                    String assistantFirstName = (String) assistantName.get("firstName");
                    String assistantLastName = (String) assistantName.get("lastName");
                    User assistant = createUser.getUser("Assistant", assistantFirstName, assistantLastName);
                    ((Assistant) assistant).addCourse(course);

                    // create group and add it to the course
                    Group newGroup = new Group(id, (Assistant) assistant);
                    course.addGroup(newGroup);
                    course.addAssistant(id, (Assistant) assistant);

                    // students
                    JSONArray students = (JSONArray) group.get("students");
                    for (Object studentObject : students) {
                        JSONObject student = (JSONObject) studentObject;
                        String studentFirstName = (String) student.get("firstName");
                        String studentLastName = (String) student.get("lastName");

                        // checking if student was previously created
                        boolean studentExists = false;
                        Student stud = null;

                        for(Student s : studentList) {
                            if (studentFirstName.equals(s.getFirstName()) && studentLastName.equals(s.getLastName())) {
                                studentExists = true;
                                stud = s;
                                //break;
                            }
                        }
                        // student already exists
                        if(studentExists == true) {
                            stud.addCourse(course);
                            course.addStudent(id, stud);
                            continue;
                        }

                        // student wasn't created yet; creating now
                        User newStudent = createUser.getUser("Student", studentFirstName, studentLastName);
                        studentList.add((Student) newStudent);

                        ((Student) newStudent).setGroupID(id);
                        ((Student) newStudent).addCourse(course);

                        // creating mother if there is one
                        JSONObject mother = (JSONObject) student.get("mother");
                        if (mother != null) {
                            String motherFirstName = (String) mother.get("firstName");
                            String motherLastName = (String) mother.get("lastName");

                            User newMother = createUser.getUser("Parent", motherFirstName, motherLastName);
                            ((Student) newStudent).setMother((Parent) newMother);
                            // adding mother to observer list
                            catalog.addObserver((Observer) newMother);
                        }
                        // creating father if there is one
                        JSONObject father = (JSONObject) student.get("father");
                        if (father != null) {
                            String fatherFirstName = (String) father.get("firstName");
                            String fatherLastName = (String) father.get("lastName");

                            User newFather = createUser.getUser("Parent", fatherFirstName, fatherLastName);
                            ((Student) newStudent).setFather((Parent) newFather);
                            // adding father to observer list
                            catalog.addObserver((Observer) newFather);
                        }
                        // adding student to group
                        course.addStudent(id, (Student) newStudent);
                    }
                }
                // add course to catalog
                catalog.addCourse(course);
            }

            // creating ScoreVisitor variable
            HashMap<Teacher, ArrayList<ScoreVisitor.Tuple<Student, String, Double>>> examScoreMap = new HashMap<>();
            ArrayList<ScoreVisitor.Tuple<Student, String, Double>> examScoreList;

            JSONArray examScores = (JSONArray) jsonObject.get("examScores");
            for (Object examScoreObject : examScores) {
                JSONObject examScore = (JSONObject) examScoreObject;

                String course = (String) examScore.get("course");
                Double grade = (Double) examScore.get("grade");

                JSONObject teacherObject = (JSONObject) examScore.get("teacher");
                String teacherFirstName = (String) teacherObject.get("firstName");
                String teacherLastName = (String) teacherObject.get("lastName");
                // finding teacher in list of teachers
                Teacher teacherGrading = null;
                for (Teacher teacher : teachers)
                    if (teacherFirstName.equals(teacher.getFirstName()) && teacherLastName.equals(teacher.getLastName())) {
                        teacherGrading = teacher;
                        break;
                    }

                // adding empty list of tuples to dictionary value of teacher
                if (examScoreMap.get(teacherGrading) == null) {
                    examScoreList = new ArrayList<>();
                    examScoreMap.put(teacherGrading, examScoreList);
                }

                JSONObject studentObject = (JSONObject) examScore.get("student");
                String studentFirstName = (String) studentObject.get("firstName");
                String studentLastName = (String) studentObject.get("lastName");

                // finding student in list of students
                Student studentGraded = null;
                for (int i = 0; i < catalog.getNumberOfCourses(); i++) {
                    Course c = catalog.getCourse(i);
                    if (course.equals(c.getName())) {
                        for (Group group : c.getGroups().values())
                            for (int j = 0; j < group.numberOfStudents(); j++) {
                                Student student = group.getStudent(j);
                                if (studentFirstName.equals(student.getFirstName()) && studentLastName.equals(student.getLastName())) {
                                    studentGraded = student;
                                    break;
                                }
                            }
                    }
                }
                // adding tuple to the dictionary
                ScoreVisitor.Tuple<Student, String, Double> triplet = new ScoreVisitor.Tuple<>(studentGraded, course, grade);
                examScoreMap.get(teacherGrading).add(triplet);
            }
            // adding exam score dictionary to score visitor
            scoreVisitor.examScores = examScoreMap;

            HashMap<Assistant, ArrayList<ScoreVisitor.Tuple<Student, String, Double>>> partialScoreMap = new HashMap<>();
            ArrayList<ScoreVisitor.Tuple<Student, String, Double>> partialScoreList;

            JSONArray partialScores = (JSONArray) jsonObject.get("partialScores");
            for (Object partialScoreObject : partialScores) {
                JSONObject partialScore = (JSONObject) partialScoreObject;

                String course = (String) partialScore.get("course");
                Double grade = (Double) partialScore.get("grade");

                JSONObject assistantObject = (JSONObject) partialScore.get("assistant");
                String assistantFirstName = (String) assistantObject.get("firstName");
                String assistantLastName = (String) assistantObject.get("lastName");

                // finding assistant in set of assistants
                Assistant assistantGrading = null;
                for (int i = 0; i < catalog.getNumberOfCourses(); i++) {
                    Course c = catalog.getCourse(i);
                    for(int j = 0; j < c.getAssistants().size(); j++) {
                        Assistant assistant = c.getAssistant(j);
                        if (assistantFirstName.equals(assistant.getFirstName()) && assistantLastName.equals(assistant.getLastName())) {
                            assistantGrading = assistant;
                            break;
                        }
                    }
                }
                // adding empty list of tuples to dictionary value of assistant
                if (partialScoreMap.get(assistantGrading) == null) {
                    partialScoreList = new ArrayList<>();
                    partialScoreMap.put(assistantGrading, partialScoreList);
                }

                JSONObject studentObject = (JSONObject) partialScore.get("student");
                String studentFirstName = (String) studentObject.get("firstName");
                String studentLastName = (String) studentObject.get("lastName");

                // finding student in list of students
                Student studentGraded = null;
                for (int i = 0; i < catalog.getNumberOfCourses(); i++) {
                    Course c = catalog.getCourse(i);
                    if (course.equals(c.getName())) {
                        for (Group group : c.getGroups().values())
                            for (int j = 0; j < group.numberOfStudents(); j++) {
                                Student student = group.getStudent(j);
                                if (studentFirstName.equals(student.getFirstName()) && studentLastName.equals(student.getLastName())) {
                                    studentGraded = student;
                                    break;
                                }
                            }
                    }
                }
                ScoreVisitor.Tuple<Student, String, Double> triplet = new ScoreVisitor.Tuple<>(studentGraded, course, grade);
                partialScoreMap.get(assistantGrading).add(triplet);
            }
            scoreVisitor.partialScores = partialScoreMap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Main NewTest = new Main("package.json");
        Catalog catalog = Catalog.getInstance();
        ScoreVisitor scoreVisitor = NewTest.getScoreVisitor();

        // todo test 1
        for(Course course : catalog.getCourses()) {
            // adding assistant's grades to catalog using Visitor
            for(Assistant assistant : course.getAssistants()) {
                assistant.accept(scoreVisitor);
            }
            // adding teacher's grades to catalog using Visitor
            Teacher teacher = course.getProfessor();
            if(course.getName().equals("Programare Orientata pe Obiecte"))
                continue;
            teacher.accept(scoreVisitor);
        }

        System.out.println("Observer List and their notifications:\n");

        for(Observer observer : catalog.observers) {
            System.out.println("Parent: " + observer);
            System.out.println(((Parent)observer).notifications);
        }

        System.out.println("\nCatalog courses list and information\n");

        for(Course course : catalog.getCourses()) {
            System.out.println(course);
            Student bestStudent = course.getBestStudent();
            System.out.println("  Best Student: " + course.getGrade(bestStudent));
            System.out.println("  Graduated Students: " + course.getGraduatedStudents());
            System.out.println();
        }

        // testing memento for OOP course
        System.out.println("All student grades for OOP course:\n");
        Course course = catalog.getCourse(0);
        System.out.println(course.getAllStudentGrades());

        ArrayList<Grade> newGrades = new ArrayList<>();
        for(int i = 0; i < course.getNumberOfStudents(); i++) {
            Grade newGrade = new Grade();
            newGrade.setCourse(course.getName());
            newGrade.setStudent(course.getAllStudents().get(i));
            newGrade.setExamScore(1.00);
            newGrade.setPartialScore(2.00);
            newGrades.add(newGrade);
        }

        System.out.println("\nTesting memento\n");
        Course c1 = catalog.getCourse(0);
        c1.makeBackup();
        Teacher teacherOOP = c1.getProfessor();
        teacherOOP.accept(scoreVisitor);
        System.out.println(c1.getGrades());
        c1.undo();
        System.out.println(c1.getGrades());

        //todo Test 2: Swing interfaces with random users
//        Random rand = new Random();
//        int indexCourse = rand.nextInt(catalog.getNumberOfCourses());
//        Course course = catalog.getCourse(indexCourse);
//        int indexGroup = rand.nextInt(course.getGroups().size());
//
//        Group group = null;
//        int i = 0;
//        for(Group g : course.getGroups().values()) {
//            if(i == indexGroup) {
//                group = g;
//                break;
//            }
//            i++;
//        }
//
//        int indexStudent = rand.nextInt(group.numberOfStudents());
//        Student student = group.getStudent(indexStudent);
//        Teacher teacher = course.getProfessor();
//        Assistant assistant = group.getAssistant();
//        Parent parent;
//        if(student.getMother() != null)
//            parent = student.getMother();
//        else
//            parent = student.getFather();
//
//        Swing.StudentPage(student);
//        Swing.TeacherPage(teacher, scoreVisitor);
//        Swing.AssistantPage(assistant, scoreVisitor);
//        if(parent != null)
//            Swing.ParentPage(parent);

        // TODO Test 3 : Login Page
        //Swing.LoginPage(scoreVisitor);
    }
}
