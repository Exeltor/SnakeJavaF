import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class snake extends Application{

    Stage window;
    Scene canvas;
    Pane pane;

    Circle snake;
    Food food;

    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean isRunning = false;

    int snakeX, snakeY, foodX, foodY;

    int xVelocity = 20;
    int yVelocity = 20;
    int prevX, prevY, range;

    ArrayList<TailPiece> tailPieces = new ArrayList<>();

    KeyFrame frame;

    Timeline timeline = new Timeline();

    Button ctrl;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        canvas = new Scene(pane(), 800,800);
        window.setScene(canvas);
        timeline.play();
        window.show();
    }

    private Pane pane(){
        pane = new Pane();

        pane.setStyle("-fx-background-color: black;");

        range = (800) + 1;
        snakeX = (int)(Math.random() * range) + 20;
        snakeY = (int)(Math.random() * range) + 20;
        foodX = (int)(Math.random() * range) + 20;
        foodY = (int)(Math.random() * range) + 20;

        snake = new Circle(snakeX, snakeY, 10);
        snake.setFill(Color.GRAY);
        food = new Food(foodX, foodY);
        pane.getChildren().add(snake);
        pane.getChildren().add(food.getFood());
        isRunning = true;

        tailPieces.add(new TailPiece());
        tailPieces.add(new TailPiece());
        pane.getChildren().add(tailPieces.get(0).getPiece());
        pane.getChildren().add(tailPieces.get(1).getPiece());

        addKeyHandler();

        frame = new KeyFrame(Duration.seconds(0.2), event -> {
                if(right){
                    prevX = (int)snake.getCenterX();
                    prevY = (int)snake.getCenterY();
                    snakeX+=xVelocity;
                    snake.setCenterX(snakeX);
                    tailPieces.get(0).moveToNextPos(prevX, prevY);

                    moveTail();

                    checkIntersect();

                } else if (left){
                    prevX = (int)snake.getCenterX();
                    prevY = (int)snake.getCenterY();
                    snakeX-=xVelocity;
                    snake.setCenterX(snakeX);
                    tailPieces.get(0).moveToNextPos(prevX, prevY);

                    moveTail();

                    checkIntersect();
                } else if (up){
                    prevX = (int)snake.getCenterX();
                    prevY = (int)snake.getCenterY();
                    snakeY-=yVelocity;
                    snake.setCenterY(snakeY);
                    tailPieces.get(0).moveToNextPos(prevX, prevY);

                    moveTail();

                    checkIntersect();
                } else if (down) {
                    prevX = (int)snake.getCenterX();
                    prevY = (int)snake.getCenterY();
                    snakeY+=yVelocity;
                    snake.setCenterY(snakeY);
                    tailPieces.get(0).moveToNextPos(prevX, prevY);

                    moveTail();

                    checkIntersect();
                }

                if(snake.intersects(food.getBounds())){
                    food.relocateFood();
                    tailPieces.add(new TailPiece());
                    pane.getChildren().add(tailPieces.get(tailPieces.size()-1).getPiece());
                }
            });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        return pane;
    }

    private void moveTail(){
        for(int i = 1; i < tailPieces.size(); i++){
            tailPieces.get(i).moveToNextPos(tailPieces.get(i-1).currX, tailPieces.get(i-1).currY);
        }
    }

    public void checkIntersect(){
        for (TailPiece piece : tailPieces){
            if(snake.getCenterX() == piece.currX && snake.getCenterY() == piece.currY){
                System.out.println("intersect");
                timeline.stop();
                piece.retCircle().setFill(Color.BLUE);
            }
        }
    }

    public void restartGame(){
        timeline.stop();
        timeline.getKeyFrames().clear();

        pane.getChildren().clear();

        tailPieces.clear();

        range = (800) + 1;
        snakeX = (int)(Math.random() * range) + 20;
        snakeY = (int)(Math.random() * range) + 20;
        foodX = (int)(Math.random() * range) + 20;
        foodY = (int)(Math.random() * range) + 20;

        snake = new Circle(snakeX, snakeY, 10);
        snake.setFill(Color.GRAY);
        food = new Food(foodX, foodY);
        pane.getChildren().add(snake);
        pane.getChildren().add(food.getFood());

        tailPieces.add(new TailPiece());
        tailPieces.add(new TailPiece());
        pane.getChildren().add(tailPieces.get(0).getPiece());
        pane.getChildren().add(tailPieces.get(1).getPiece());

        addKeyHandler();

        timeline.getKeyFrames().add(frame);
        timeline.play();

    }

    public void addKeyHandler(){
        ctrl = new Button();
        ctrl.relocate(-200, -200);
        pane.getChildren().add(ctrl);

        ctrl.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP && !down){
                up = true;
                down = false;
                right = false;
                left = false;

            } else if (e.getCode() == KeyCode.DOWN && !up){
                up = false;
                down = true;
                right = false;
                left = false;
            } else if (e.getCode() == KeyCode.RIGHT && !left){
                up = false;
                down = false;
                right = true;
                left = false;

            } else if (e.getCode() == KeyCode.LEFT && !right){
                up = false;
                down = false;
                right = false;
                left = true;
            } else if (e.getCode() == KeyCode.SPACE){
                restartGame();
            }
        });
    }

}
