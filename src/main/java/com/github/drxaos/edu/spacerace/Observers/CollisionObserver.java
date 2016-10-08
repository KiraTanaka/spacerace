package com.github.drxaos.edu.spacerace.Observers;

import com.github.drxaos.edu.spacerace.Controller;
import com.github.drxaos.edu.spacerace.Models.Model;
import com.github.drxaos.edu.spacerace.MovingModels.MovingModel;
import com.github.drxaos.edu.spacerace.MovingModels.Player;

import java.awt.geom.Point2D;

/**
 * Created by Akira on 24.09.2016.
 */
public class CollisionObserver implements Observer {
    Controller controller;
    public CollisionObserver(Controller controller){
        this.controller=controller;
    }
    public void  onNotify(Object event){
        if (event==Events.COLLISION)
            collisionChecking();
    }
    void collisionChecking(){
        playerWithAsteroid(controller.Wall_counter(),controller.Wall(),controller.Gamer());
        playerWithAsteroid(controller.Wall_counter(),controller.Wall(),controller.Computer());
        playerWithUFO(controller.Ufo_counter(),controller.Ufo_army(),controller.Gamer());
        playerWithUFO(controller.Ufo_counter(),controller.Ufo_army(),controller.Computer());
        playerWithComputer(controller.Gamer(),controller.Computer());
    }
    double[] calculationOfChangeOfSpeed(Point2D.Double point1, Point2D.Double point2){
        // расчет изменения скоростей шаров при соударении
        double dx = point1.getX() - point2.getX();
        double dy = point1.getY() - point2.getY();
        double angle = Math.atan2(dy, dx);
        double targetX = point2.getX() + Math.cos(angle);
        double targetY = point2.getY() + Math.sin(angle);
        double ax = (targetX - point1.getX());
        double ay = (targetY - point1.getY());
        return new double[]{ax,ay};
    }
    public double calculatingDistancesBetweenObjects(Point2D.Double point1, Point2D.Double point2){
        double deltaX = point1.getX() - point2.getX();
        double deltaY = point1.getY() - point2.getY();
        return Math.sqrt(Math.pow(deltaX,2)+ Math.pow(deltaY,2));
    }
    public void playerWithComputer(Player gamer, Player computer) {
        double distance=calculatingDistancesBetweenObjects(gamer.getPoint(),computer.getPoint());
        if (distance <= 1) {
            double[] changeParam = calculationOfChangeOfSpeed(gamer.getPoint(),computer.getPoint());
            computer.speedChangeInCollision(-changeParam[0],-changeParam[1],0.7,0.7);
            gamer.speedChangeInCollision(changeParam[0],changeParam[1],0.7,0.7);
        }
    }

    public void playerWithUFO(int ufo_counter, MovingModel[] ufo_army, Player player) {
        for (int i = 0; i < ufo_counter; i++) {
            double distance=calculatingDistancesBetweenObjects(ufo_army[i].getPoint(),player.getPoint());
            if (distance <= 1) {
                double[] changeParam = calculationOfChangeOfSpeed(ufo_army[i].getPoint(),player.getPoint());
                player.speedChangeInCollision(-changeParam[0],-changeParam[1],0.7,0.7);
                ufo_army[i].speedChangeInCollision(changeParam[0],changeParam[1]);
            }
        }
    }

    public void playerWithAsteroid(int wall_counter, Model[] wall, Player player) {
        for (int i = 0; i < wall_counter; i++) {
            double distance=calculatingDistancesBetweenObjects(wall[i].getPoint(),player.getPoint());
            if (distance <= 1) {
                double[] changeParam = calculationOfChangeOfSpeed(wall[i].getPoint(),player.getPoint());
                player.speedChangeInCollision(-changeParam[0],-changeParam[1],0.7,0.7);
            }
        }
    }
}