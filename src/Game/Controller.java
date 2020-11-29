package Game;

import Entity.Bomber;
import Entity.Entity;
import Entity.Wall;

import Graphic.Sprite;
import javafx.scene.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public void getInput(Scene scene, Bomber bomber) {
        ArrayList<String> input = new ArrayList<>();
        scene.setOnKeyPressed(keyEvent -> {
            String code = keyEvent.getCode().toString();
            System.out.println("Input detected: " + code);
            int x = bomber.getX() / Sprite.SCALED_SIZE;
            int y = bomber.getY() / Sprite.SCALED_SIZE;

            switch(code) {
                case "UP":
                    if (Map.map[x][y - 1] == 1) {
                        break;
                    }
                    else {
                        bomber.setValY(-Sprite.SCALED_SIZE);
                    }
                    break;
                case "DOWN":
                    if (Map.map[x][y + 1] == 1) {
                        break;
                    }
                    else {
                        bomber.setValY(Sprite.SCALED_SIZE);
                    }
                    break;
                case "RIGHT":
                    if (Map.map[x + 1][y] == 1) {
                        break;
                    }
                    else {
                        bomber.setValX(Sprite.SCALED_SIZE);
                    }
                    break;
                case "LEFT":
                    if (Map.map[x - 1][y] == 1) {
                        break;
                    }
                    else {
                        bomber.setValX(-Sprite.SCALED_SIZE);
                    }
                    break;
            }

            if (!input.contains(code))
                input.add(code);
        });
        scene.setOnKeyReleased(keyEvent -> {
            String code = keyEvent.getCode().toString();
            System.out.println("Input released: " + code);
            input.remove(code);
            bomber.setValX(0);
            bomber.setValY(0);
        });
    }





}
