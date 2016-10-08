package com.github.drxaos.edu.spacerace;

    public class SpaceRace {
        static Controller controller;
            public static void main(String[] args) throws Exception {
                // TODO Отрефакторить логику игры, используя ООП и принципы S.O.L.I.D.
                controller = new Controller();
                controller.game();

            }
    }
