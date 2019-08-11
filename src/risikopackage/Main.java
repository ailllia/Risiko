package risikopackage;

import java.io.FileNotFoundException;

/**
 * Main starts the program. It reads files into the program with Loadtxtfiles and initializes a PlayersGUI.
 *
 * @author Swantje Wiechmann
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //Reads countries, continents and missions from text file.
        Loadtxtfiles.readFiles(".");

        //Opens the GUI where the colors of the players must be chosen.
        new PlayersGUI();
    }
}