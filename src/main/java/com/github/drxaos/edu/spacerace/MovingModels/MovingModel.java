package com.github.drxaos.edu.spacerace.MovingModels;

import com.github.drxaos.edu.spacerace.Models.Model;
import com.github.drxaos.spriter.Spriter;

/**
 * Created by Akira on 25.09.2016.
 */
public class MovingModel extends Model{
    protected double speed_x, speed_y ;
    public MovingModel(){}
    public void setSpeed(double value_x, double value_y)
    {
        speed_x=value_x;
        speed_y=value_y;
    }
    public void addSpeed(double value_x, double value_y)
    {
        speed_x+=value_x;
        speed_y+=value_y;
    }
    public void toSlow(double value_x, double value_y)
    {
        speed_x*=value_x;
        speed_y*=value_y;
    }
    public double getSpeed_x()
    {
        return speed_x;
    }
    public double getSpeed_y()
    {
        return speed_y;
    }

}
