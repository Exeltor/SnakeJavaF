package SNAKE_PCKG;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

public class snake{
    Pane pane;

    Circle snake;
    Food food;

    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;

    int snakeX, snakeY, foodX, foodY;

    int xVelocity = 20;
    int yVelocity = 20;
    int prevX, prevY, range;
    int score = 0;
    Text scoreText, gameOverText, descText1, descText2;

    ArrayList<TailPiece> tailPieces = new ArrayList<>();

    KeyFrame frame;

    Timeline timeline = new Timeline();

    Button ctrl;

    public Pane pane(){
        pane = new Pane();
        pane.setMaxWidth(800);
        pane.setMaxHeight(800);

        pane.setStyle("-fx-background-color: black;");

        initializeFirstComponents();

        addKeyHandler();

        frame = new KeyFrame(Duration.seconds(0.1), event -> movementExec());
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        return pane;
    }

    private void moveTail(){
        for(int i = 1; i < tailPieces.size(); i++){
            tailPieces.get(i).moveToNextPos(tailPieces.get(i-1).currX, tailPieces.get(i-1).currY);
        }
    }

    private void checkIntersect(){
        for (TailPiece piece : tailPieces){
            if(snake.getCenterX() == piece.currX && snake.getCenterY() == piece.currY){
                System.out.println("intersect");
                timeline.stop();
                piece.retCircle().setFill(Color.BLUE);
                timeline.stop();
                showGameOver();
            }
        }
    }

    private void restartGame(){
        //limpieza del campo
        timeline.stop();
        timeline.getKeyFrames().clear();
        pane.getChildren().clear();
        tailPieces.clear();

        //inicializacion de los componentes
        initializeFirstComponents();

        //inicializacion del keylistener
        addKeyHandler();

        //adicion de las frames y comienzo
        timeline.getKeyFrames().add(frame);
        timeline.play();

    }

    private void addKeyHandler(){
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

    private void initializeFirstComponents(){
        score = 0;
        range = (800) + 1;
        snakeX = (int)Math.round((Math.random()*(800-1)+1)/10)*10;
        snakeY = (int)Math.round((Math.random()*(800-1)+1)/10)*10;
        foodX = (int)Math.round((Math.random()*(800-1)+1)/10)*10;
        foodY = (int)Math.round((Math.random()*(800-1)+1)/10)*10;

        snake = new Circle(snakeX, snakeY, 10);
        snake.setFill(Color.GRAY);
        food = new Food(foodX, foodY);
        pane.getChildren().add(snake);
        pane.getChildren().add(food.getFood());

        tailPieces.add(new TailPiece());
        tailPieces.add(new TailPiece());
        pane.getChildren().add(tailPieces.get(0).getPiece());
        pane.getChildren().add(tailPieces.get(1).getPiece());

        scoreText = new Text("Score: " + score);
        scoreText.setFill(Color.GRAY);
        scoreText.setFont(Font.font("Arial", 25));
        scoreText.setX(30);
        scoreText.setY(750);
        pane.getChildren().add(scoreText);
    }

    private void movementExec(){        //ejecucion de comandos de direcciones
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
            score += 100;
            scoreText.setText("Score: " + score);
        }

        if(snake.getCenterX() >= pane.getMaxWidth() || snake.getCenterX() <= 0 ||
                snake.getCenterY() >= pane.getMaxHeight() || snake.getCenterY() <= 0){
            timeline.stop();
            showGameOver();
        }
    }

    private void showGameOver(){
        gameOverText = new Text("Game Over");
        descText1 = new Text("Press SPACE to restart");
        descText2 = new Text("Press ESCAPE to exit to Main Menu");

        gameOverText.setFont(Font.font("Arial", 50));
        descText1.setFont(Font.font("Lucida Console", 20));
        descText2.setFont(Font.font("Lucida Console", 20));

        gameOverText.setFill(Color.WHITE);
        gameOverText.setY(200);
        gameOverText.setX(275);

        descText1.setFill(Color.WHITE);
        descText1.setY(500);
        descText1.setX(250);

        descText2.setFill(Color.WHITE);
        descText2.setY(700);
        descText2.setX(200);

        pane.getChildren().addAll(gameOverText, descText1, descText2);
    }

}
