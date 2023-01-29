package Tema_POO;

import java.util.ArrayList;

public class Parent extends User implements Observer {
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public ArrayList<Notification> notifications = new ArrayList<>();

    public void update(Notification notification) {
        notifications.add(notification);
    }
}
