package MAINMENU_PCKG;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class SettingsPane {

    private RadioButton setEasy, setMedium, setHard;
    private VBox panel;
    private ToggleGroup rbuttons;

    private void initsSettingsPanel(){
        panel = new VBox();

        panel.getChildren().addAll(setEasy, setMedium,setHard);

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

    public VBox getPanel(){
        initializeSelectors();
        initsSettingsPanel();

        return panel;
    }
}
