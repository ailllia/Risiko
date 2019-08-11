package risikopackage;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

    	/**
    	 * reads countries, continents and missions from text file
    	 */
        Loadtxtfiles.readFiles(".");

        /**
         * opens the GUI where the colors of the players must be chosen 
         */
        new PlayersGUI();
    }
}