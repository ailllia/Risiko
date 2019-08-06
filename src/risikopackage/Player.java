package risikopackage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmies;
    private int playernew;
    private List<String> occupiedCountryNames;
    private List<Country> occupiedCountries;

    public List<String> getCountryNames() {
        return occupiedCountryNames;
    }

    public String getCountryName(int i) {
        return occupiedCountryNames.get(i);
    }

    public void addCountryToList(String countryName) {
        this.occupiedCountryNames.add(countryName);
    }

    public Player(String pcolor) {
        color = pcolor;
        this.occupiedCountryNames = new ArrayList<>();
    }

    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountryNames));
    }

    public int getNewArmies() {
        int newArmies = 0;
        for (Continent continent : Main.continents) {
            if (continentComplete(continent))
                newArmies += continent.getBonusArmies();
        }
        if (occupiedCountries.size() >= 9)
            newArmies += (occupiedCountries.size() / 3);
        else
            newArmies += 2;
        return newArmies;
    }

    public void moveArmies() {
        int newArmies = playerArmies - occupiedCountries.size();
        if (newArmies == 0) {
            // Ausgabe, dass keine Bewegung moeglich ist
        } else {
            for (Country country : occupiedCountries) {
                country.setArmies();
            }
            // newArmies koennen jetzt verteilt werden
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static Color PlayerColorCode(Player player) {
        Color color;
        if (player.getColor().equals("blau")) {              //switch?
            color = new java.awt.Color(0, 0, 255);
        } else if (player.getColor().equals("rot")) {
            color = new java.awt.Color(255, 0, 0);
        } else if (player.getColor().equals("lila")) {
            color = new java.awt.Color(127, 0, 127);
        } else if (player.getColor().equals("pink")) {
            color = new java.awt.Color(255, 0, 255);
        } else if (player.getColor().equals("grau")) {
            color = new java.awt.Color(76, 76, 76);
        } else {
            color = new java.awt.Color(0, 0, 0);
        }
        return color;
    }

    @Override
    public String toString() {
        return "Farbe: " + color + ", Mission: " + playerMission + ", Anzahl der Armeen: " + playerArmies; // + playernew
    }
}
