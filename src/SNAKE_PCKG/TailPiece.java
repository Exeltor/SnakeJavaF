package SNAKE_PCKG;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class TailPiece {

    Circle piece;
    int currX, currY;

    public TailPiece(){

    }

    public Circle getPiece(){
        piece = new Circle(10);
        piece.setFill(Color.GRAY);
        return piece;
    }

    public void moveToNextPos(int prevX, int prevY){
        currX = (int)piece.getCenterX();
        currY = (int)piece.getCenterY();
        piece.setCenterX(prevX);
        piece.setCenterY(prevY);
    }

    public Circle retCircle(){
        return piece;
    }

}
