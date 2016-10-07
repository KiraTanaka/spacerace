package com.github.drxaos.edu.spacerace.Observers;

import com.github.drxaos.edu.spacerace.Controller;
import com.github.drxaos.edu.spacerace.Observers.Observable;
import com.github.drxaos.edu.spacerace.Observers.Observer;

import java.awt.event.KeyEvent;

/**
 * Created by Akira on 25.09.2016.
 */
public class KeyboardObserver implements Observer {
    Controller controller;
    public KeyboardObserver(Controller controller){
        this.controller=controller;
    }
    public void onNotify(Object event)
    {
        if(event!=null) {
            switch ((Integer) event) {
                case KeyEvent.VK_LEFT:
                    controller.Gamer().addAngle(-0.06);
                    controller.Gamer().setAngleOfSprite();

                case KeyEvent.VK_RIGHT:
                    controller.Gamer().addAngle(0.06);
                    controller.Gamer().setAngleOfSprite();

                case KeyEvent.VK_UP:
                    controller.Gamer().addSpeed(Math.cos(controller.Gamer().getAngle()) * 0.005, Math.sin(controller.Gamer().getAngle()) * 0.005);
                    controller.Gamer().setVisibleOfTail(true);
                    break;
                default:
                    controller.Gamer().setVisibleOfTail(false);
                    break;
            }
        }
    }
}
