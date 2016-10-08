package com.github.drxaos.edu.spacerace.MovingModels;

import com.github.drxaos.edu.spacerace.Models.Model;
import com.github.drxaos.edu.spacerace.MovingModels.MovingModel;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;

/**
 * Created by Akira on 20.09.2016.
 */
public class Player extends MovingModel {
    protected double angle;
    //BufferedImage player_image;
    public Spriter.Sprite player_sprite, player_tail;
    public static String tail_image = "/tail.png";
    Player(){
    }
    public void setAngle(double value) {
        angle = value;
    }
    public void addAngle(double value) {
        angle += value;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngleOfSprite() {
        player_sprite.setAngle(angle);
    }
    public void setVisibleOfTail(boolean flag){
        player_tail.setVisible(flag);
    }
    public void setPositionSprite(double x,double y){
        player_sprite.setPos(x, y);
    }
    @Override
    public void move(){
        addPosition(speed_x,speed_y);
        player_sprite.setPos(point.getX(),point.getY());
    }
    @Override
    public void speedChangeInCollision(double ax, double ay,double multiplier_x,double multiplier_y){
        addSpeed(ax,ay);
        toSlow(multiplier_x,multiplier_y);
    }
}
