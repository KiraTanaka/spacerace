package com.github.drxaos.edu.spacerace.Observers;

import java.util.ArrayList;

/**
 * Created by Akira on 24.09.2016.
 */
public interface Observable {
    ArrayList<Observer> observers = new ArrayList<Observer>();
    public void notifyObservers(Object event);
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
}
