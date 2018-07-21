import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food {

    private Circle food;
    private int xCoord, yCoord;
    int range = (800) + 1;

    public Food(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Bounds getBounds(){
        return food.getBoundsInParent();
    }

    public Circle getFood(){
        food = new Circle(xCoord, yCoord, 10);
        food.setFill(Color.RED);
        return food;
    }

    public void relocateFood(){
        food.relocate((int)(Math.random() * range) + 20, (int)(Math.random() * range) + 20);
    }

}
