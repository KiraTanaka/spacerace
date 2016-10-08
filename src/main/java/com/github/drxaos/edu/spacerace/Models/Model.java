package com.github.drxaos.edu.spacerace.Models;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Akira on 20.09.2016.
 */
public abstract class Model {
    protected Point2D.Double point=point=new Point2D.Double();
    public Model(){

    }
    BufferedImage image;
    public  double getX(){
        return point.getX();
    }
    public  double getY(){
        return point.getY();
    }
    public Point2D.Double getPoint(){
        return point;
    }
    public void setPosition(double value_x, double value_y)
    {
        point.setLocation(value_x,value_y);
    }
    public void addPosition(double value_x, double value_y) {
        point.setLocation(point.getX()+value_x,point.getY()+value_y);
    }
    public void speedChangeInCollision(double ax, double ay,double multiplier_x,double multiplier_y){}
    public void speedChangeInCollision(double ax, double ay){}
    public void speedChangeInCollision(){}
    public void move(){}
}
