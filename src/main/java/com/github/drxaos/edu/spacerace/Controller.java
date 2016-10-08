package com.github.drxaos.edu.spacerace;

import com.github.drxaos.edu.spacerace.Models.Asteroid;
import com.github.drxaos.edu.spacerace.MovingModels.*;
import com.github.drxaos.edu.spacerace.Observers.*;
import com.github.drxaos.edu.spacerace.Views.View;
import com.github.drxaos.spriter.Spriter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Akira on 23.09.2016.
 */
public class Controller implements Observable {

    protected Gamer gamer ;
    protected Computer computer=new Computer();
    protected ArrayList<Observer> observers=new ArrayList<Observer>();
    protected Asteroid[] wall=new Asteroid[10000];
    protected UFO[] ufo_army=new UFO[10000];
    protected View view;
    protected Spriter.Control control;
    private int wall_counter=0;
    private int ufo_counter=0;

    Controller() throws Exception {
        gamer = new Gamer();
        view = new View(this);
        view.initialDrawing();
        placeObjects();
        computer.markMap();
        view.setDebug(true);
        control=view.getControl();
        observers.add(new CollisionObserver(this));
    }
    void game ()throws Exception{
        view.drawsAllDownloaded();
        while (true) {
            view.beginFrame(); // синхронизация логики и потока отрисовки, чтобы не было графических "артефактов"
            movePlayer();
            gamer.move();
            view.setViewportShift(gamer.getX(), gamer.getY());
            reduceGap();
            computer.calculationDirection();
            computer.move();
            notifyObservers(Events.COLLISION);
            moveUfo_army();
            view.endFrame(); // конец синхронизации
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void movePlayer() {
        if (control.isKeyDown(KeyEvent.VK_LEFT)) {
            // влево
            gamer.addAngle(-0.06);
            gamer.setAngleOfSprite();
        }
        if (control.isKeyDown(KeyEvent.VK_RIGHT)) {
            // вправо
            gamer.addAngle(0.06);
            gamer.setAngleOfSprite();
        }
        if (control.isKeyDown(KeyEvent.VK_UP)) {
            // ускорение
            gamer.addSpeed(Math.cos(gamer.getAngle()) * 0.005,Math.sin(gamer.getAngle()) * 0.005);
            gamer.setVisibleOfTail(true);
        } else {
            // нет ускорения - нет шлейфа
            gamer.setVisibleOfTail(false);
        }
    }
    private void moveUfo_army() {
        for (int i = 0; i < ufo_counter; i++) {
            ufo_army[i].move();
        }
    }
    void reduceGap(){
        int player_step = computer.ai_map[(int) Math.round(gamer.getX())][(int) Math.round(gamer.getY())];
        int computer_step = computer.ai_map[(int) Math.round(computer.getX())][(int) Math.round(computer.getY())];
        if (computer_step - player_step > 20) {
            double deltaX = computer.getX() - gamer.getX();
            double deltaY = computer.getY() - gamer.getY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (distance > 15) { // корабль компьютера сейчас не видно
                computer_step = player_step + 20; // надо отставать не больше чем на 20 шагов
                // ищем подходящее место для телепортации
                for (int x = 1; x < 99; x++) {
                    for (int y = 1; y < 99; y++) {
                        if (computer.ai_map[x][y] == computer_step) {
                            deltaX = x - gamer.getX();
                            deltaY = y - gamer.getY();
                            distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                            if (distance > 15) { // только если игрок не видит эту клетку
                                computer.setPosition(x,y);
                            }
                        }
                    }
                }
            }
        }

    }
    void placeObjects(){
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                int[] pixel = new int[4]; // RGBA
                view.map_image.getData().getPixel(x, y, pixel);
                // схлапываем RGB-составляющие цвета в 3-битное число
                int type = (pixel[0] & 1) + ((pixel[1] & 1) << 1) + ((pixel[2] & 1) << 2);
                switch (type) {
                    case (0):
                        // Черный - Стена
                        view.wallPrototype.createGhost().setPos(x, y).setVisible(true);
                        wall[wall_counter]=new Asteroid(x,y);
                        wall_counter++;
                        computer.ai_map[x][y] = -1; // стена
                        break;
                    case (1):
                        // Красный - Красный корабль
                        computer.setPositionSprite(x, y);
                        computer.setAngle(-Math.PI / 2);
                        computer.setPosition(x,y);
                        computer.setSpeed(0,0);

                        break;
                    case (2):
                        // Зеленый - Зеленый корабль
                        gamer.player_sprite.setPos(x, y).setAngle(-Math.PI / 2);
                        gamer.setAngle(-Math.PI / 2);
                        gamer.setPosition(x,y);
                        gamer.setSpeed(0,0);
                        break;
                    case (3):
                        // Желтый - Звезды
                        // у всех звезд разный размер, поэтому вместо createGhost() - clone() (у каждой звезды своя копия изображения)
                        view.starPrototype.clone().setPos(x, y).setWidthProportional(Math.random() * 0.4 + 0.4).setVisible(true);
                        break;
                    case (4):
                        // Синий - НЛО
                        ufo_army[ufo_counter]=new UFO(x,y,0,0);
                        ufo_army[ufo_counter].sprite=view.ufoPrototype.createGhost().setPos(x, y).setVisible(true);
                        ufo_counter++;
                        break;
                    case (5):
                        // Фиолетовый
                        break;
                    case (6):
                        // Голубой - Финиш
                        computer.ai_map[x][y] = 1; // цель
                        break;
                    case (7):
                        // Белый - Пусто
                        computer.ai_map[x][y] = 0; // неразмеченная клетка
                        break;
                }
            }
        }
    }
    public void notifyObservers(Object event) {
        for (Observer ob : observers){
            ob.onNotify(event);
        }
    }
    public void addObserver(Observer o) {
        observers.add(o);
    }
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    public Gamer Gamer(){
        return gamer;
    }
    public Computer Computer(){
        return computer;
    }
    public Asteroid[] Wall(){
        return wall;
    }
    public UFO[] Ufo_army(){
        return ufo_army;
    }
    public int Wall_counter(){
        return wall_counter;
    }
    public int Ufo_counter(){
        return ufo_counter;
    }
}
