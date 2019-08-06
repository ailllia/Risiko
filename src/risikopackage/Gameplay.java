package risikopackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gameplay extends JFrame {
    // hier werden die spielzuege in einzelnen funktionen aufgerufen
    boolean continuing = false; // wird duch klicken des buttons "weiter" auf true gesetzt sodass
    // die nächste funktion aufgerufen wird

    public static void initialising() { // Erste Textausgaben, klick auf "weiter" ruft dann spreadCountries auf
    	Random chance = new Random();
    	spreadCountries(Main.playerOne, Main.playerTwo, Main.countries, chance);
        paintCountries(Main.playerOne, Main.playerTwo, Main.countries);
        chooseMission(Main.playerOne, Main.playerTwo, Main.missions, chance);
    }

    public static void welcome() { // Erste Textausgaben, klick auf "weiter" ruft dann spreadCountries auf
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!/n");
    }

    // verteilt Laender an die beiden Spieler
    private static void spreadCountries(Player playerOne, Player playerTwo, List<Country> countries, Random chance) {
        List<Country> countriesCopy = new ArrayList<>(countries);
        int j = 13;

        for (int i = 0; i < 7; i++) {
            int k = chance.nextInt(j);
            playerOne.addCountryToList(countriesCopy.get(k).getCountryName());
            countriesCopy.remove(k);
            j -= 1;
        }

        for (int i = 0; i < 7; i++) {
            playerTwo.addCountryToList(countriesCopy.get(i).getCountryName());
        }
    }

    // faerbt beim Spielaufbau die Laender ein und setzt in jedes eine Armee
    private static void paintCountries(Player playerOne, Player playerTwo, List<Country> countries) {
        Color rgbCodesOne = Player.PlayerColorCode(playerOne);
        Color rgbCodesTwo = Player.PlayerColorCode(playerTwo);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 7; j++) {
                if (playerOne.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    countries.get(i).setColorOfOwner(rgbCodesOne);
                    countries.get(i).setArmies();
                }
            }
        }
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 7; j++) {
                if (playerTwo.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    countries.get(i).setColorOfOwner(rgbCodesTwo);
                    countries.get(i).setArmies();
                }
            }
        }
    }

    //teilt Missionen zu
    private static void chooseMission(Player playerOne, Player playerTwo, List<Mission> missions, Random chance)
    {   	
    	List<Mission> missionsCopy = new ArrayList<>(missions);   	
    	int j = 5;
    	int k = chance.nextInt(j);
    	playerOne.setPlayerMission(missionsCopy.get(k).getMissionTitle());
    	missionsCopy.remove(k);
    	j -= 1;
    	k = chance.nextInt(j);
    	playerTwo.setPlayerMission(missionsCopy.get(k).getMissionTitle());
    }
    
    public static void addArmiesInCountry(Country country, Player player) {
        if (player.armiesAvailableToMove()) {
            country.addArmy();
        } else {
            //Ausgabe, dass keine Armeen bewegt werden können
        }
    }

    public static void loseArmyInCountry(Country country, Player player) {
        if (player.armiesAvailableToWithdraw(country)) {
            country.loseArmy();
        } else {
            //Ausgabe, dass aus diesem Land keine Armee abgezogen werden kann
        }
    }
}
