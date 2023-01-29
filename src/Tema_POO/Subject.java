package Tema_POO;

import java.util.ArrayList;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Grade grade);
}

//class SubjectA implements Subject {
//    ArrayList<Observer> observers = new ArrayList<>();
//
//    public void addObserver(Observer observer) {
//        observers.add(observer);
//    }
//
//    public void removeObserver(Observer observer) {
//        observers.remove(observer);
//    }
//
//    public void notifyObservers(Grade grade) {
//        for(Observer observer : observers) {
//            Notification notification = new Notification(grade);
//            observer.update(notification);
//        }
//    }
//
//}
