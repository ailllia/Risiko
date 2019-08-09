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
    private List<Country> occupiedCountries;

    // erstellt Spieler
    public Player(String pcolor) {
        color = pcolor;
        this.occupiedCountriesNames = new ArrayList<>();
        this.occupiedCountries = new ArrayList<>();
        availableArmies = 0;
        playerArmies = 0;
    }

    // erstellt und benennt die Laender eines Spielers sowie deren Anzahl
    public List<String> getCountryNames() {
        return occupiedCountriesNames;
    }

    public List<Country> getCountries() {
        return occupiedCountries;
    }

    public String getCountryName(int i) {
        return occupiedCountriesNames.get(i);
    }

    public void addCountryToList(String countryName, Country country) {
        this.occupiedCountriesNames.add(countryName);
        this.occupiedCountries.add(country);
    }

    public int numberOfCountries() {
        return this.occupiedCountriesNames.size();
    }

    // wertet aus, wie viele Armeen bei einem Angriff zur Verfuegung stehen
    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountriesNames));
    }

    public int getPlayerArmies() {
        return playerArmies;
    }

    public int getNewArmies() {
        if (occupiedCountriesNames.size() < 9)
            availableArmies = 2;
        else
            availableArmies = (occupiedCountriesNames.size() / 3);
        for (Continent continent : Main.continents) {
            if (continentComplete(continent))
                availableArmies += continent.getBonusArmies();
        }
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

    public void readyArmiesToMove() {
        for (Country country : occupiedCountries) {
            country.setArmies();
        }
        availableArmies = (playerArmies - this.numberOfCountries());
    }

    public int getArmiesAvailableToMove() {
        return availableArmies;
    }
    
    // bestimmt und nennt Armeen im Spiel
    public void setNumberOfArmies(List<Country> countries)
    {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 7; j++) {
                if (this.getCountryName(j).equals(countries.get(i).getCountryName())) {
                	this.playerArmies += countries.get(i).getArmiesInCountry();
                }
            }
        }
    }
    
    public int numberOfArmies()
    {
    	setNumberOfArmies(Main.countries);
    	return playerArmies;
    }

    // bestimmt und nennt Farben
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
    
    // leert Spielerangaben f�r neues Spiel
    public void emptyAll()
    {
    	this.color = "black";
    	this.playerMission = "none";
    	this.playerArmies = 0;
    	this.playernew = 0;
    	this.availableArmies = 0;
    	this.occupiedCountriesNames.clear();
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
