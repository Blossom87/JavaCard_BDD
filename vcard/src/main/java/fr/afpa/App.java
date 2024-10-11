package fr.afpa;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import javafx.scene.Group;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainWindow"), 1024, 768);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage successGif() {

        int size = 600;
        // chargement de l'image
        Image gif = new Image(App.class.getResource("yippee-happy.gif").toString());

        ImageView splash = new ImageView(gif);
        splash.setStyle("-fx-background-color: transparent;");
        splash.setFitWidth(size);
        splash.setFitHeight(size);
        splash.setPickOnBounds(true);
        Pane splashLayout = new Pane();
        splashLayout.getChildren().add(splash);
        final Stage initStage = new Stage();
        Group group = new Group();
        group.getChildren().add(splashLayout);
        Scene successScene = new Scene(group, size, size);
        successScene.setFill(Color.TRANSPARENT);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setWidth(size);
        initStage.setHeight(size);
        initStage.setScene(successScene);
        initStage.setAlwaysOnTop(true);
        initStage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> initStage.close());
        delay.play();

        return initStage;
    }

    public static Stage deleteGIF() {

        int size = 600;
        // chargement de l'image
        Image gif = new Image(App.class.getResource("thanos.gif").toString());

        ImageView splash = new ImageView(gif);
        splash.setStyle("-fx-background-color: transparent;");
        splash.setFitWidth(size);
        splash.setFitHeight(size);
        splash.setPickOnBounds(true);
        Pane splashLayout = new Pane();
        splashLayout.getChildren().add(splash);
        final Stage initStage = new Stage();
        Group group = new Group();
        group.getChildren().add(splashLayout);
        Scene successScene = new Scene(group, size, size);
        successScene.setFill(Color.TRANSPARENT);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setWidth(size);
        initStage.setHeight(size);
        initStage.setScene(successScene);
        initStage.setAlwaysOnTop(true);
        initStage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> initStage.close());
        delay.play();

        return initStage;
    }
}