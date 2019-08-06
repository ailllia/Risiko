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
        spreadCountries(Main.playerOne, Main.playerTwo, Main.countries);
        paintCountries(Main.playerOne, Main.playerTwo, Main.countries);
    }
    
    public static void welcome() { // Erste Textausgaben, klick auf "weiter" ruft dann spreadCountries auf
        FieldGUI.textfield.append("Willkommen bei einer Runde Risiko!");
    }

    private static void spreadCountries(Player playerOne, Player playerTwo, List<Country> countries) {
        List<Country> countriesCopy = new ArrayList<>(countries);
        Random chance = new Random();
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
    
    private static void paintCountries(Player playerOne, Player playerTwo, List<Country> countries) 
    {
       	Color rgbCodesOne = Player.PlayerColorCode(playerOne);
       	Color rgbCodesTwo = Player.PlayerColorCode(playerTwo);
       	for (int i = 0; i < 14; i++)
       	{
           	for (int j = 0; j < 7; j++)
           	{
           		if (playerOne.getCountryName(j) == countries.get(i).getCountryName())
           		{
           			countries.get(i).setColorOfOwner(rgbCodesOne);
           			countries.get(i).setArmies();
           		}
           	}
       	}
       	for (int i = 0; i < 14; i++)
       	{
           	for (int j = 0; j < 7; j++)
           	{
           		if (playerTwo.getCountryName(j) == countries.get(i).getCountryName())
           		{
           			countries.get(i).setColorOfOwner(rgbCodesTwo);
           			countries.get(i).setArmies();
           		}
           	}
       	}     	
    }
    
    public static void addArmiesInCountry(Country country, Player player) {
    	if (player.armiesAvailableToMove()) {
    		country.addArmy();
    	}
    }
    
    public static void loseArmyInCountry(Country country, Player player) {
    	if (player.armiesAvailableToWithdraw(country)) {
    		country.loseArmy();
    	}
    }
    	 

    
}
