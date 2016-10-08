package com.github.drxaos.edu.spacerace.Views;

import com.github.drxaos.edu.spacerace.Controller;
import com.github.drxaos.edu.spacerace.Models.Asteroid;
import com.github.drxaos.edu.spacerace.Models.Star;
import com.github.drxaos.edu.spacerace.MovingModels.Computer;
import com.github.drxaos.edu.spacerace.MovingModels.Gamer;
import com.github.drxaos.edu.spacerace.MovingModels.Player;
import com.github.drxaos.edu.spacerace.MovingModels.UFO;
import com.github.drxaos.spriter.Spriter;
import com.github.drxaos.spriter.SpriterUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Akira on 25.09.2016.
 */
public class View {
    final int LAYER_BG = 0,
        LAYER_STAR = LAYER_BG + 50,
        LAYER_OBJECTS = 500,
        LAYER_SHIP = LAYER_OBJECTS,
        LAYER_SHIP_TAIL = LAYER_SHIP - 100,
        LAYER_WALL = LAYER_OBJECTS,
        LAYER_UFO = LAYER_WALL + 50;
    Controller controller;
    public Spriter.Sprite ufoPrototype,wallPrototype,starPrototype;
    Spriter spriter = new Spriter("Space Race");
    public BufferedImage map_image;

    public View(Controller controller_link){
        this.controller=controller_link;
        spriter.setViewportWidth(15);
        spriter.setViewportHeight(15);
    }
    public void initialDrawing() throws IOException{
        loading();
        drawBackground();
        drawObjects();
    }
    void loading() throws IOException {
        spriter.setBackgroundColor(Color.BLACK);
        beginFrame();
        Spriter.Sprite loading = spriter.createSprite(SpriterUtils.loadImageFromResource("/loading.png"), 367 / 2, 62 / 2, 5);
        endFrame();
        spriter.pause();
        loading.setVisible(false);
        drawBackground();
    }
    void drawBackground () throws IOException {
        BufferedImage background_image = SpriterUtils.loadImageFromResource("/background.jpg");
        Spriter.Sprite background = spriter.createSpriteProto(background_image, 512, 512).setWidth(25).setHeight(25).setLayer(LAYER_BG);
        for (int x = 0; x <= 100; x += 25) {
            for (int y = 0; y <= 100; y += 25) {
                background.createGhost().setPos(x, y).setVisible(true);
            }
        }
    }
    void drawObjects() throws IOException {
        BufferedImage player_green_image = SpriterUtils.loadImageFromResource(Gamer.image);
        BufferedImage player_red_image= SpriterUtils.loadImageFromResource(Computer.image);
        BufferedImage tail_image = SpriterUtils.loadImageFromResource(Player.tail_image);
        BufferedImage ufo_image = SpriterUtils.loadImageFromResource(UFO.image);
        BufferedImage star_image = SpriterUtils.loadImageFromResource(Star.image);
        BufferedImage meteor_image = SpriterUtils.loadImageFromResource(Asteroid.image);
         map_image = SpriterUtils.loadImageFromResource("/map.png");
        // Объекты
        ufoPrototype = spriter.createSpriteProto(ufo_image, 45, 45).setWidth(1).setHeight(1).setLayer(LAYER_UFO);
        wallPrototype = spriter.createSpriteProto(meteor_image, 50, 50).setWidth(1).setHeight(1).setLayer(LAYER_WALL);
        starPrototype = spriter.createSpriteProto(star_image, 50, 50).setWidth(0.5).setHeight(0.5).setLayer(LAYER_STAR);
        Spriter.Sprite trg = spriter.createSprite(SpriterUtils.loadImageFromResource("/point.png"), 256 / 2, 256 / 2, 0.5);

        // Корабли
        controller.Gamer().player_sprite = spriter.createSprite(player_green_image, 40, 50, 1).setLayer(LAYER_SHIP);
        controller.Computer().player_sprite = spriter.createSprite(player_red_image, 40, 50, 1).setLayer(LAYER_SHIP);

        // Шлейфы кораблей
        Spriter.Sprite tailPrototype = spriter.createSpriteProto(tail_image, 41, 8).setWidth(0.4).setHeight(0.2).setX(-0.2).setLayer(LAYER_SHIP_TAIL);
        // setParent закрепляет спрайт на другом спрайте и центром координат для него становится середина родительского
        controller.Gamer().player_tail = tailPrototype.clone().setParent(controller.Gamer().player_sprite).setVisible(true);
        controller.Computer().player_tail = tailPrototype.clone().setParent(controller.Computer().player_sprite).setVisible(true);
    }
    public void drawsAllDownloaded(){
        spriter.unpause();
    }
    public void beginFrame(){
        spriter.beginFrame();
    }
    public void endFrame(){
        spriter.endFrame(); // конец синхронизации
    }
    public void setViewportShift(double x,double y){
        spriter.setViewportShift(x, y);
    }
    public void setDebug(boolean flag){
        spriter.setDebug(flag);
    }
    public Spriter.Control getControl(){
        return spriter.getControl();
    }
}
