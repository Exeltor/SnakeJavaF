import javafx.scene.shape.Circle;

public class TailPiece {

    Circle piece;
    int currX, currY;

    public TailPiece(){

    }

    public Circle getPiece(){
        piece = new Circle(10);
        return piece;
    }

    public void moveToNextPos(int prevX, int prevY){
        currX = (int)piece.getCenterX();
        currY = (int)piece.getCenterY();
        piece.setCenterX(prevX);
        piece.setCenterY(prevY);
    }

}
