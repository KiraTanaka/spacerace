package com.github.drxaos.edu.spacerace.Observers;


import com.github.drxaos.edu.spacerace.Models.Model;
import com.github.drxaos.edu.spacerace.MovingModels.MovingModel;

import java.lang.*;

/**
 * Created by Akira on 24.09.2016.
 */
public interface Observer {
    void onNotify(Object events);
}
