package Game;

import Entity.Enemy;
import Entity.Bomber;
import Entity.Entity;
import Entity.Grass;
import Entity.Wall;
import Graphic.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Game.Game.*;


public class Map {
    protected static int[][] map = new int[HEIGHT][WIDTH];

    public static void loadMap(String mapName, List<Entity> entities) {
        ArrayList<Integer> tempMap = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mapName))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                String[] values = currentLine.trim().split("");
                for (String string:values) {
                    if (!string.isEmpty()) {
                        int id = Integer.parseInt(string);
                        tempMap.add(id);
                    }
                }

            }

        } catch(IOException e) {
            System.out.println("Map File not found");
        }

        int index = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x] = tempMap.get(index++);
                switch(map[y][x]) {
                    case 1:
                        entities.add(new Wall(x, y, Sprite.wall.getFxImage()));
                        break;
                    case 2:
                        entities.add(new Enemy(x, y, Sprite.doll_left1.getFxImage()));
                        break;
                    default:
                        break;
                }
            }
        }


    }

    public static Bomber createBomber() {
        return new Bomber(1, 1, Sprite.player_down.getFxImage());
    }

    public static void createMap(List<Entity> entities, Entity bomber) {
        entities.add(bomber);
    }

//    public static void createBackgroundMap(List<Entity> stillObject) {
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object;
//                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                }
//                else {
//                    object = new Grass(i, j, Sprite.grass.getFxImage());
//                }
//                stillObject.add(object);
//            }
//        }
//
//    }

    public static void createBackgroundMap(List<Entity> stillObject) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                object = new Grass(i, j, Sprite.grass.getFxImage());
                stillObject.add(object);
            }
        }

    }

}