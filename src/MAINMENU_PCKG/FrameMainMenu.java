package MAINMENU_PCKG;

import SNAKE_PCKG.snake;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FrameMainMenu extends Application {

    Stage window;
    Scene canvas, gameScene;
    BorderPane mainBorderPane;
    VBox centerVBox;
    Button play, exit;
    TitledPane settingsTitledPane;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage = new Stage(StageStyle.UNDECORATED);
        window = primaryStage;
        window.setResizable(false);

        initComponents();
        canvas = new Scene(mainPane(), 800,800);
        window.setScene(canvas);
        window.show();
    }

    public BorderPane mainPane(){
        mainBorderPane = new BorderPane();
        mainBorderPane.setCenter(centerVBox());
        mainBorderPane.setBottom(settingsTitledPane);

        return mainBorderPane;
    }

    public VBox centerVBox(){
        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(play, exit);

        return centerVBox;
    }

    public void initComponents(){
        play = new Button("Play");
        exit = new Button("Exit");
        play.setPrefSize(500, 100);
        exit.setPrefSize(500, 100);
        exit.setOnAction(e -> System.exit(1));
        play.setOnAction(e -> toGameScene());

        settingsTitledPane = new TitledPane();
        settingsTitledPane.setText("Settings");
        settingsTitledPane.setAnimated(true);
        settingsTitledPane.setCollapsible(true);
        settingsTitledPane.setExpanded(false);

    }


    public void toGameScene(){
        gameScene = new Scene(new snake().pane(), 800,800);
        window.setScene(gameScene);
        gameScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                window.setScene(canvas);
            }
        });
    }


}
