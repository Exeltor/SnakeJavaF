package TOOLKIT_PCKG;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SettingsManager {

    File settingsFile = new File("settings.txt");
    private String[] settings = new String[2];
    private int resWidth, resHeight;


    public SettingsManager() {
        createFile();
    }

    private void createFile() {
        if(!settingsFile.exists()){

            try {
                settingsFile.createNewFile();
                populateFields();
                write();
            } catch (Exception e) {
                System.out.println("No se puede crear el archivo");
            }
        } else {
            System.out.println("Archivo ya existe");

        }

    }

    private void populateFields() {
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        settings[0] = "Resolution '" + resolution.getWidth() + "x" + resolution.getHeight() + "'";
        settings[1] = "Diff 'NORMAL'";
    }
    public void populateFields(Object res, Object diff){
        settings[0] = "Resolution '" + res + "'";
        settings[1] = "Diff '" + diff + "'";
        write();
    }

    public void resolveResolution() throws Exception{
        String resStringFull = Files.readAllLines(Paths.get(String.valueOf(settingsFile))).get(0);
        String resString = resStringFull.substring(resStringFull.indexOf("'")+1, resStringFull.lastIndexOf("'"));
        String[] widthHeight = resString.split("x");
        resWidth = Integer.parseInt(widthHeight[0]);
        resHeight = Integer.parseInt(widthHeight[1]);
    }

    public int getResWidth(){
        return resWidth;
    }

    public int getResHeight(){
        return resHeight;
    }

    private void write() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(settingsFile, false));
            for (int i = 0; i < settings.length; i++) {
                writer.println(settings[i]);
            }
            writer.close();
        } catch (Exception e){
            System.out.println("No se puede escribir al archivo");
        }
    }
}
