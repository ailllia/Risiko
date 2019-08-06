package risikopackage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmies;
    private int playernew;
    private int availableArmies;
    private List<String> occupiedCountriesNames;

    public Player(String pcolor) {
        color = pcolor;
        this.occupiedCountriesNames = new ArrayList<>();
        availableArmies = 0;
        playerArmies = 0;
    }

    public List<String> getCountryNames() {
        return occupiedCountriesNames;
    }

    public String getCountryName(int i) {
        return occupiedCountriesNames.get(i);
    }

    public void addCountryToList(String countryName) {
        this.occupiedCountriesNames.add(countryName);
    }

    public int numberOfCountries() {
        return this.occupiedCountriesNames.size();
    }


    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountriesNames));
    }

    public int getNewArmies() {
        for (Continent continent : Main.continents) {
            if (continentComplete(continent))
                availableArmies += continent.getBonusArmies();
        }
        if (occupiedCountriesNames.size() >= 9)
            availableArmies += (occupiedCountriesNames.size() / 3);
        else
            availableArmies = 2;    //geaendert von += zu =
        playerArmies += availableArmies;
        return availableArmies;
    }

    public boolean armiesAvailableToMove() {
        if (availableArmies > 0) {
            availableArmies--;
            return true;
        } else
            return false;
    }

    public boolean armiesAvailableToWithdraw(Country country) {
        if (country.getArmiesInCountry() > 1) {
            availableArmies++;
            return true;
        } else
            return false;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    // bestimmt und nennt Missionen
    public void setPlayerMission(String mission) {
        this.playerMission = mission;
    }

    public String getPlayerMission() {
        return playerMission;
    }

    @Override
    public String toString() {
        return "Farbe: " + color + ", Mission: " + playerMission + ", Anzahl der Armeen: " + playerArmies; // + playernew
    }

    public static Color PlayerColorCode(Player player) {
        Color color;
        if (player.getColor().equals("blau")) {              //switch?
            color = new java.awt.Color(0, 0, 255);
        } else if (player.getColor().equals("rot")) {
            color = new java.awt.Color(255, 0, 0);
        } else if (player.getColor().equals("lila")) {
            color = new java.awt.Color(157, 60, 156);
        } else if (player.getColor().equals("pink")) {
            color = new java.awt.Color(255, 0, 255);
        } else if (player.getColor().equals("grau")) {
            color = new java.awt.Color(76, 76, 76);
        } else {
            color = new java.awt.Color(0, 0, 0);
        }
        return color;
    }
}
