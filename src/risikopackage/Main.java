package risikopackage;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //Das Einlesen
        Loadtxtfiles.readFiles(".");

        //Erste GUI, Startfenster
        new PlayersGUI();
    }
}