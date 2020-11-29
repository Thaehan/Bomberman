package Game;

import Graphic.Sprite;
import Entity.Bomber;
import Entity.Entity;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    private Canvas canvas;
    private GraphicsContext gc;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private Controller controller = new Controller();
    protected Bomber bomber = Map.createBomber();

    public static void main(String[] args) {
        Application.launch(Game.class);
    }

    public void start(Stage stage) {
        canvas = new Canvas(WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        controller.getInput(scene, bomber);

        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                render();
                update();
            }

        };
        timer.start();

        Map.createBackgroundMap(stillObjects);
        Map.createMap(entities, bomber);
        Map.loadMap("test.txt", entities);

    }

    public void update() {
        bomber.update();
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
