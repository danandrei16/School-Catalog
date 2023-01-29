package Tema_POO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Swing {
    public static JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(480, 320);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        return frame;
    }

    public static JPanel titlePanel(String title) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(480, 35));
        panel.setBackground(new Color(194, 214, 214));
        JLabel labelCatalog = new JLabel(title.toUpperCase());
        labelCatalog.setFont(new Font("Calibri Light", Font.BOLD, 30));
        panel.add(labelCatalog);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
        return panel;
    }

    public static void StudentPage(Student student) {
        Catalog catalog = Catalog.getInstance();
        EmptyBorder border = new EmptyBorder(5, 5, 5, 5);
        Font titleFont = new Font("Calibri Light", Font.BOLD, 22);
        Font textFont = new Font("Georgia", Font.PLAIN, 14);
        Font smallFont = new Font("Georgia", Font.PLAIN, 12);
        Border titleBorder = BorderFactory.createCompoundBorder();

        JFrame frame = createFrame("Student Page");
        frame.setLocation(new Point(10, 10));

        JPanel panel = titlePanel("Student Page");
        frame.add(panel, BorderLayout.NORTH);
        // panel with student info (name and group)
        JPanel studentPanel = new JPanel();
        studentPanel.setPreferredSize(new Dimension(205, panel.getHeight() - 35));
        studentPanel.setBackground(new Color(224, 235, 235));
        studentPanel.setLayout(new GridLayout(10, 1));

        studentPanel.add(new JLabel());

        JLabel label1 = new JLabel();
        label1.setText(" First Name:   " + student.getFirstName());
        label1.setFont(new Font("Georgia", Font.PLAIN, 15));
        studentPanel.add(label1);

        JLabel label3 = new JLabel();
        label3.setText(" Last Name:    " + student.getLastName());
        label3.setFont(new Font("Georgia", Font.PLAIN, 15));
        studentPanel.add(label3);

        JLabel label5 = new JLabel();
        label5.setText(" Group:            " + student.getGroupID());
        label5.setFont(new Font("Georgia", Font.PLAIN, 15));
        studentPanel.add(label5);

        studentPanel.setBorder(BorderFactory.createMatteBorder(1, 2, 1, 1, Color.DARK_GRAY));
        frame.add(studentPanel, BorderLayout.WEST);
        // panel with course list
        JPanel coursePanel = new JPanel();
        coursePanel.setPreferredSize(new Dimension(275, panel.getHeight() - 35));
        coursePanel.setBackground(new Color(224, 233, 235));
        coursePanel.setLayout(new GridLayout(10, 1));

        coursePanel.add(new JLabel());

        JLabel text = new JLabel();
        text.setText(" Courses: ");
        text.setFont(titleFont);
        text.setHorizontalAlignment(SwingConstants.LEFT);
        text.setBorder(titleBorder);
        coursePanel.add(text);
        coursePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 2, Color.DARK_GRAY));

        for (int i = 0; i < student.getCourses().size(); i++) {
            Course course = student.getCourses().get(i);
            int courseIndex = i;
            JButton newLabel = new JButton();
            newLabel.setFocusPainted(false);
            newLabel.setText(course.getName());
            newLabel.setFont(new Font("Georgia", Font.ITALIC, 15));
            newLabel.setHorizontalAlignment(SwingConstants.LEFT);
            if (i % 2 == 0)
                newLabel.setBackground(new Color(194, 214, 214));
            else
                newLabel.setBackground(new Color(224, 233, 235));
            // action when button is clicked
            newLabel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    newLabel.setEnabled(false); // button no longer clickable for now
                    JFrame newFrame = new JFrame();
                    newFrame.setLocation(new Point(frame.getX() + frame.getWidth(), frame.getY()));
                    newFrame.setTitle(" " + catalog.getCourse(courseIndex).getName());
                    newFrame.setVisible(true);
                    newFrame.setResizable(false);
                    newFrame.setSize(350, 400);
                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    // course information panel
                    JPanel courseInfo = new JPanel();
                    courseInfo.setBackground(new Color(224, 233, 235));
                    courseInfo.setPreferredSize(new Dimension(350, 200));
                    courseInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
                    courseInfo.setLayout(new GridLayout(7, 1));

                    JLabel courseName = new JLabel();
                    courseName.setText(" Course: " + course.getName().toUpperCase());
                    courseName.setFont(smallFont);
                    courseInfo.add(courseName);

                    JLabel professor = new JLabel();
                    professor.setText(" Professor: " + course.getProfessor().toString().toUpperCase());
                    professor.setFont(smallFont);
                    courseInfo.add(professor);

                    JLabel assistants = new JLabel();
                    assistants.setText(" Assistants: ");
                    assistants.setFont(smallFont);
                    courseInfo.add(assistants);

                    for (int j = 0; j < course.getAssistants().size(); j++) {
                        JLabel assistant = new JLabel();
                        assistant.setFont(smallFont);
                        assistant.setText("  ▪ " + course.getAssistant(j).toString());
                        courseInfo.add(assistant);
                    }
                    newFrame.add(courseInfo, BorderLayout.SOUTH);
                    // student information panel (course grades)
                    JPanel studentInfo = new JPanel();
                    studentInfo.setBackground(new Color(194, 214, 214));
                    studentInfo.setPreferredSize(new Dimension(350, 200));
                    studentInfo.setBorder(border);
                    studentInfo.setLayout(new GridLayout(6, 2));

                    JLabel stud = new JLabel("Student: ");
                    stud.setFont(textFont);
                    studentInfo.add(stud);

                    JLabel studentName = new JLabel();
                    studentName.setText(student.toString().toUpperCase());
                    studentName.setFont(textFont);
                    studentInfo.add(studentName);

                    JLabel assist = new JLabel("Assistant: ");
                    assist.setFont(textFont);
                    studentInfo.add(assist);

                    JLabel assistantName = new JLabel();
                    assistantName.setText(course.getGroups().get(student.getGroupID()).getAssistant().toString());
                    assistantName.setFont(textFont);
                    studentInfo.add(assistantName);

                    JLabel partialGrade = new JLabel("Partial Grade: ");
                    partialGrade.setFont(textFont);
                    studentInfo.add(partialGrade);

                    Grade studentGrade = course.getGrade(student);

                    JLabel partialGradeValue = new JLabel();
                    if (studentGrade == null)
                        partialGradeValue.setText("TBA");
                    else
                        partialGradeValue.setText(studentGrade.getPartialScore().toString());
                    partialGradeValue.setFont(textFont);
                    studentInfo.add(partialGradeValue);

                    JLabel examGrade = new JLabel("Exam Grade: ");
                    examGrade.setFont(textFont);
                    studentInfo.add(examGrade);

                    JLabel examGradeValue = new JLabel();
                    if (studentGrade == null)
                        examGradeValue.setText("TBA");
                    else
                        examGradeValue.setText(studentGrade.getExamScore().toString());
                    examGradeValue.setFont(textFont);
                    studentInfo.add(examGradeValue);

                    JLabel finalGrade = new JLabel("Grade: ");
                    finalGrade.setFont(textFont);
                    studentInfo.add(finalGrade);

                    JLabel finalGradeValue = new JLabel();
                    if (studentGrade == null)
                        finalGradeValue.setText("TBA");
                    else
                        finalGradeValue.setText(studentGrade.getTotal().toString());
                    finalGradeValue.setFont(textFont);
                    studentInfo.add(finalGradeValue);

                    newFrame.add(studentInfo, BorderLayout.NORTH);
                    // making button re-clickable after closing the frame
                    newFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosed(WindowEvent e) {
                            newLabel.setEnabled(true);
                        }
                    });
                }
            });
            coursePanel.add(newLabel);
        }
        frame.add(coursePanel);
    }

    public static void TeacherPage(Teacher teacher, ScoreVisitor unverifiedGrades) {
        Font titleFont = new Font("Calibri Light", Font.BOLD, 22);
        Color color = new Color(224, 235, 235);

        JFrame frame = createFrame("Teacher Page");
        frame.setLocation(new Point(700, 400));
        JPanel title = titlePanel("Teacher Page");

        frame.add(title, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(7, 1));
        info.setBackground(new Color(224, 233, 235));

        JLabel name = new JLabel();
        name.setText(" Professor: " + teacher.toString());
        info.add(name);

        JLabel courses = new JLabel(" Courses: ");
        info.add(courses);

        for (int i = 0; i < teacher.courses.size(); i++) {
            JButton course = new JButton();
            course.setFocusPainted(false);
            course.setText(teacher.courses.get(i).getName());
            course.setBackground(title.getBackground());
            course.setHorizontalAlignment(SwingConstants.LEFT);
            int j = i;

            course.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame newFrame = createFrame(teacher.courses.get(j).getName());
                    newFrame.setSize(500, 400);
                    newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    course.setEnabled(false);

                    JPanel north = new JPanel();
                    north.setLayout(new GridLayout(1, 1));

                    JLabel title = new JLabel("Exam Grades to be verified:");
                    title.setFont(titleFont);

                    north.add(title);
                    north.setPreferredSize(new Dimension(500, 50));
                    north.setBackground(color);

                    newFrame.add(north, BorderLayout.NORTH);

                    JPanel grades = new JPanel();

                    ArrayList<ScoreVisitor.Tuple<Student, String, Double>> list = unverifiedGrades.examScores.get(teacher);
                    grades.setLayout(new GridLayout(14, 1));
                    grades.setPreferredSize(new Dimension(500, 300));

                    for (int k = 0; k < list.size(); k++) {
                        JLabel studentGrade = new JLabel();
                        studentGrade.setText(list.get(k).getFirst() + " " + list.get(k).getThird());
                        grades.add(studentGrade);
                    }
                    newFrame.add(grades, BorderLayout.CENTER);

                    JPanel south = new JPanel();
                    JButton verify = new JButton("Verify grades");
                    verify.setFocusPainted(false);

                    verify.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            teacher.accept(unverifiedGrades);
                            grades.setVisible(false);
                            System.out.println(teacher.courses.get(j).getGrades()); // prints newly verified grades
                        }
                    });

                    south.add(verify);
                    south.setPreferredSize(new Dimension(500, 50));
                    newFrame.add(south, BorderLayout.SOUTH);
                    newFrame.pack();
                    // making sure course is only clickable once, and re-clickable after closing the frame
                    newFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosed(WindowEvent e) {
                            course.setEnabled(true);
                        }
                    });
                }
            });
            info.add(course);
        }
        frame.add(info);
    }

    public static void AssistantPage(Assistant teacher, ScoreVisitor unverifiedGrades) {
        Font titleFont = new Font("Calibri Light", Font.BOLD, 22);
        Color color = new Color(224, 235, 235);

        JFrame frame = createFrame("Assistant Page");
        frame.setLocation(new Point(700, 10));
        JPanel title = titlePanel("Assistant Page");

        frame.add(title, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(8, 1));
        info.setBackground(new Color(224, 233, 235));

        JLabel name = new JLabel();
        name.setText(" Assistant: " + teacher.toString());
        info.add(name);

        JLabel courses = new JLabel(" Courses: ");
        info.add(courses);

        for (int i = 0; i < teacher.courses.size(); i++) {
            JButton course = new JButton();
            course.setFocusPainted(false);
            course.setText(teacher.courses.get(i).getName());
            course.setBackground(title.getBackground());
            course.setHorizontalAlignment(SwingConstants.LEFT);
            int j = i;

            course.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame newFrame = createFrame(teacher.courses.get(j).getName());
                    newFrame.setSize(300, 300);
                    newFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    course.setEnabled(false);

                    JPanel north = new JPanel();
                    north.setLayout(new GridLayout(1, 1));

                    JLabel title = new JLabel("  Partial Grades to be verified:");
                    title.setFont(titleFont);

                    north.add(title);
                    north.setPreferredSize(new Dimension(300, 50));
                    north.setBackground(color);

                    newFrame.add(north, BorderLayout.NORTH);

                    JPanel grades = new JPanel();

                    ArrayList<ScoreVisitor.Tuple<Student, String, Double>> list = unverifiedGrades.partialScores.get(teacher);

                    grades.setLayout(new GridLayout(9, 2));
                    grades.setPreferredSize(new Dimension(300, 200));

                    for (int k = 0; k < list.size(); k++) {
                        JLabel studentName = new JLabel();
                        studentName.setText("   " + list.get(k).getFirst().toString());
                        grades.add(studentName);
                        JLabel studentGrade = new JLabel();
                        studentGrade.setText(list.get(k).getThird().toString());
                        grades.add(studentGrade);
                    }
                    newFrame.add(grades, BorderLayout.CENTER);

                    JPanel south = new JPanel();
                    JButton verify = new JButton("Verify grades");
                    verify.setFocusPainted(false);

                    verify.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            teacher.accept(unverifiedGrades);
                            SwingUtilities.updateComponentTreeUI(newFrame);
                            grades.setVisible(false);
                            System.out.println(teacher.courses.get(j).getGrades()); // prints newly verified grades
                        }
                    });

                    south.add(verify);
                    south.setPreferredSize(new Dimension(300, 50));
                    newFrame.add(south, BorderLayout.SOUTH);
                    newFrame.pack();
                    // making sure course is only clickable once, and re-clickable after closing the frame
                    newFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosed(WindowEvent e) {
                            course.setEnabled(true);
                        }
                    });
                }
            });
            info.add(course);
        }
        frame.add(info);
    }

    public static void ParentPage(Parent parent) {
        Font titleFont = new Font("Calibri Light", Font.BOLD, 22);

        JFrame frame = createFrame("Parent Page");
        frame.setLocation(new Point(10, 400));
        JPanel title = titlePanel("Parent Page");
        title.setFont(titleFont);
        frame.add(title, BorderLayout.NORTH);

        // parent name
        JPanel center = new JPanel();
        center.setBackground(new Color(224, 233, 235));
        JLabel name = new JLabel(parent.toString());
        center.add(name);
        frame.add(center, BorderLayout.CENTER);

        // creating button. when pressed it shows the notifications
        JPanel notifications = new JPanel();
        JButton button = new JButton();
        button.setText("Check grades");
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame newFrame = new JFrame("Grades");
                newFrame.setSize(700, 500);
                newFrame.setVisible(true);

                JPanel grades = new JPanel();
                grades.setLayout(new GridLayout(3, 1));

                if (parent.notifications.size() == 0) {
                    JLabel label = new JLabel("No score was yet added");
                    grades.add(label);
                }
                for (int i = 0; i < parent.notifications.size(); i++) {
                    JLabel label = new JLabel();
                    label.setText(String.valueOf(parent.notifications.get(i)));
                    grades.add(label);
                }
                newFrame.add(grades);
            }
        });

        notifications.add(button);
        frame.add(notifications, BorderLayout.SOUTH);
    }

    public static void LoginPage(ScoreVisitor scoreVisitor) {
        Color color = new Color(224, 235, 235);
        Font titleFont = new Font("Calibri Light", Font.BOLD, 22);

        JFrame frame = createFrame("Login Page");
        frame.setSize(500, 350);
        frame.setResizable(true);

        JPanel northPanel = titlePanel("Catalog Authentificator");
        northPanel.setPreferredSize(new Dimension(600, 50));

        frame.add(northPanel, BorderLayout.NORTH);

        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(150, 200));
        westPanel.setBackground(color);

        frame.add(westPanel, BorderLayout.WEST);

        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(600, 100));
        southPanel.setBackground(color);

        frame.add(southPanel, BorderLayout.SOUTH);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(150, 200));
        eastPanel.setBackground(color);

        frame.add(eastPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(300, 220));
        centerPanel.setBackground(color);

        JLabel userName = new JLabel("User: ");
        JLabel passwordValue = new JLabel("Password: ");
        JTextField user = new JTextField(16);
        JPasswordField pass = new JPasswordField(16);
        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Catalog catalog = Catalog.getInstance();

                String username = user.getText();
                String password = String.valueOf(pass.getPassword());

                String[] names = username.split(" ");
                String firstName = names[0];
                String lastName = names[1];

                boolean found = false;
                for (Course course : catalog.getCourses()) {
                    // checking if user is student or parent
                    for (Student student : course.getAllStudents()) {
                        if (firstName.equals(student.getFirstName()) && lastName.equals(student.getLastName())) {
                            StudentPage(student);
                            found = true;
                            break;
                        }
                        Parent mother = student.getMother();
                        if (mother != null)
                            if (firstName.equals(mother.getFirstName()) && lastName.equals(mother.getLastName())) {
                                ParentPage(mother);
                                found = true;
                                break;
                            }
                        Parent father = student.getFather();
                        if (father != null)
                            if (firstName.equals(father.getFirstName()) && lastName.equals(father.getLastName())) {
                                ParentPage(father);
                                found = true;
                                break;
                            }
                    }

                    Teacher teacher = course.getProfessor();
                    if (firstName.equals(teacher.getFirstName()) && lastName.equals(teacher.getLastName())) {
                        TeacherPage(teacher, scoreVisitor);
                        found = true;
                        break;
                    }

                    for (Assistant assistant : course.getAssistants()) {
                        if (firstName.equals(assistant.getFirstName()) && lastName.equals(assistant.getLastName())) {
                            AssistantPage(assistant, scoreVisitor);
                            found = true;
                            break;
                        }
                    }
                    if (found == true)
                        break;
                }
                if(found == false) {
                    frame.add(new JLabel("User not found"));
                }

            }
        });
        centerPanel.add(userName);
        centerPanel.add(user);
        centerPanel.add(passwordValue);
        centerPanel.add(pass);
        centerPanel.add(login);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
