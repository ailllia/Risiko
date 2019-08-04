package risikopackage;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Gameplay extends JFrame {
    // hier werden die spielzuege in einzelnen funktionen aufgerufen
    boolean continuing = false; // wird duch klicken des buttons "weiter" auf true gesetzt sodass
                                // die nächste funktion aufgerufen wird

    public static void initialising() { // Erste Textausgaben, klick auf "weiter" ruft dann spreadCountries auf
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!");
        spreadCountries(Main.playerOne, Main.playerTwo, Main.countries);
    }

    private static void spreadCountries(Player playerOne, Player playerTwo, List<Country> countries) {
        List<Country> countriesCopy = countries;
        Random chance = new Random();
        int j = 13;

        for (int i = 0; i < 7; i++) {
            int k = chance.nextInt(j);
            playerOne.addCountryToList(countriesCopy.get(k).getCountryName());
            countriesCopy.remove(k);
            j -= 1;
        }

        for (int i = 0; i < 7; i++) {
            playerTwo.getCountryNames().add(countriesCopy.get(i).getCountryName());
        }
    }
}
