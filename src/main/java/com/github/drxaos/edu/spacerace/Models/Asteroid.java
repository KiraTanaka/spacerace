package com.github.drxaos.edu.spacerace.Models;

import com.github.drxaos.edu.spacerace.Models.Model;

import java.io.IOException;

/**
 * Created by Akira on 20.09.2016.
 */
public class Asteroid extends Model {
    public static String image = "/meteor.png";
    Asteroid() throws IOException {
    }
    public Asteroid(double value_x, double value_y)  {
        point.setLocation(value_x,value_y);
    }
}
