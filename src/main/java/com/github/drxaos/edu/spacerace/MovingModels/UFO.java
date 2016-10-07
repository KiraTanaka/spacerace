package com.github.drxaos.edu.spacerace.MovingModels;

import com.github.drxaos.edu.spacerace.MovingModels.MovingModel;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;

/**
 * Created by Akira on 20.09.2016.
 */
public class UFO extends MovingModel {
    public static String image = "/ufo.png";
    // спрайты НЛО
     public Spriter.Sprite sprite;
    public UFO(double value_x, double value_y, double valueSpeed_x, double valueSpeed_y)  {
        point.setLocation(value_x,value_y);
        valueSpeed_x=speed_x;
        valueSpeed_y=speed_y;
    }
    @Override
    public void speedChangeInCollision(double ax, double ay,double multiplier_x,double multiplier_y){
        addSpeed(ax,ay);
    }
    @Override
    public void move(){
        addPosition(speed_x,speed_y);
        // гасим скорость НЛО, чтобы он быстрее остановился
        toSlow(0.9,0.9);
        sprite.setPos(point.x, point.y);
    }
}
