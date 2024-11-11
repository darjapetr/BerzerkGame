package coursework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameRules game = new GameRules();
        Scene scene = game.getScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Berzerk");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}