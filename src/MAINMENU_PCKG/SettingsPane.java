package MAINMENU_PCKG;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.awt.*;

public class SettingsPane {

    private RadioButton setEasy, setMedium, setHard;
    private VBox panelLeft;
    private ToggleGroup rbuttons;
    private BorderPane mainPanel;

    private void initMainPanel(){
        mainPanel = new BorderPane();
        mainPanel.setLeft(getPanelLeft());
    }

    private void initLeftPanel(){
        panelLeft = new VBox();

        Label titleDiff = new Label("Difficulty");
        panelLeft.getChildren().addAll(titleDiff, setEasy, setMedium,setHard);

    }

    private void initializeSelectors(){
        setEasy = new RadioButton("Easy");
        setMedium = new RadioButton("Medium");
        setHard = new RadioButton("Hard");

        rbuttons = new ToggleGroup();
        setEasy.setToggleGroup(rbuttons);
        setMedium.setToggleGroup(rbuttons);
        setHard.setToggleGroup(rbuttons);

        setMedium.setSelected(true);
    }

    private VBox getPanelLeft(){
        initializeSelectors();
        initLeftPanel();

        return panelLeft;
    }

    public BorderPane returnMainPane(){
        initMainPanel();
        return mainPanel;
    }
}
