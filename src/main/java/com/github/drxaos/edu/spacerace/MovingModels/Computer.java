package com.github.drxaos.edu.spacerace.MovingModels;

import com.github.drxaos.spriter.SpriterUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Akira on 20.09.2016.
 */
public class Computer  extends Player{
    public static String image = "/player-red.png";
    // карта для разметки пути компьютера
    public int jh;
    public int[][] ai_map = new int[100][100];
    public Computer() {
    }
    public void markMap(){
        int cells = 1;
        while (cells > 0) {
            cells = 0; // если новых размеченных клеток не будет, значит надо выйти из цикла

            for (int y = 1; y < 99; y++) {
                for (int x = 1; x < 99; x++) {

                    if (ai_map[x][y] < 0) {
                        // -1 - это стены, пропускаем
                        continue;
                    }

                    // ищем рядом наименьшую пронумерованную клетку, и присваиваем текущей следующий номер
                    int min = Integer.MAX_VALUE;

                    // смотрим вправо, вниз, влево, вверх
                    if (ai_map[x + 1][y + 0] > 0 && ai_map[x + 1][y + 0] < min) {
                        min = ai_map[x + 1][y + 0];
                    }
                    if (ai_map[x + 0][y + 1] > 0 && ai_map[x + 0][y + 1] < min) {
                        min = ai_map[x + 0][y + 1];
                    }
                    if (ai_map[x - 1][y + 0] > 0 && ai_map[x - 1][y + 0] < min) {
                        min = ai_map[x - 1][y + 0];
                    }
                    if (ai_map[x + 0][y - 1] > 0 && ai_map[x + 0][y - 1] < min) {
                        min = ai_map[x + 0][y - 1];
                    }

                    if (min > 0 && min < Integer.MAX_VALUE && ai_map[x][y] == 0) {
                        ai_map[x][y] = min + 1;
                        cells++;
                    }
                }
            }
        }
    }
    public void calculationDirection() {
        int target_x = 0;
        int target_y = 0;
        searchForFreeCell(target_x,target_y);
        // текущая скорость
        double computer_current_velocity = Math.sqrt(Math.pow(speed_x,2) + Math.pow(speed_y,2));
        double computer_current_velocity_angle = Math.atan2(speed_y, speed_x);
        // азимут до цели
        double target_angle = Math.atan2(target_y - point.getY(), target_x - point.getX());

        // поправка от текущего угла
        double angle_shift = target_angle - angle;

        // поправка от текущего угла скорости
        double velocity_angle_shift = target_angle - computer_current_velocity_angle;

        // нормализуем поправку
        while (angle_shift > Math.PI) {
            angle_shift -= Math.PI * 2;
        }
        while (angle_shift < -Math.PI) {
            angle_shift += Math.PI * 2;
        }

        if (angle_shift > 0.1) {
            // направо
            addAngle(0.06);
        }
        if (angle_shift < -0.1) {
            // налево
            addAngle(-0.06);
        }

        // ускоряемся, если скорость < 0.3 или если она направлена не на цель
        boolean should_accelerate = (computer_current_velocity < 0.3 || Math.cos(velocity_angle_shift) < 0.6);
        if (Math.abs(angle_shift) < Math.PI / 4 && should_accelerate) {
            addSpeed(Math.cos(angle) * 0.005,Math.sin(angle) * 0.005);
            player_tail.setVisible(true);
        } else {
           player_tail.setVisible(false);
        }
        player_sprite.setAngle(angle);
    }
    void searchForFreeCell(int target_x,int target_y){
        // Корабль компьютера ищет минимальный номер в прямой видимости

        int min = Integer.MAX_VALUE;
        // смотрим в разные стороны и ищем минимальный номер
        for (int a = 0; a < 18; a++) { // 18 шагов по 20 градусов
            double search_x = point.getX();
            double search_y = point.getY();
            while (true) {
                int sx = (int) Math.round(search_x);
                int sy = (int) Math.round(search_y);
                if (ai_map[sx][sy] < 0) {
                    // стена, дельше не смотрим
                    break;
                }
                if (ai_map[sx][sy] < min) {
                    // запоминаем минимум
                    min = ai_map[sx][sy];
                    target_x = sx;
                    target_y = sy;
                }
                // продвигаемся в выбранном направлении
                search_x += Math.cos(Math.PI * 2 / 18 * a) * 0.3;
                search_y += Math.sin(Math.PI * 2 / 18 * a) * 0.3;
            }
        }
    }
}
