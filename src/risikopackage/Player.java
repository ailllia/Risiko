package risikopackage;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private String color;
    private String playerMission;
    private int playerArmies;
    private int availableArmies;
    private List<String> occupiedCountriesNames;
    private List<Country> occupiedCountries;

    /**
     * creates a new player
     * @param pcolor is the chosen color for this player
     */
    public Player(String pcolor) {
        color = pcolor;
        this.occupiedCountriesNames = new ArrayList<>();
        this.occupiedCountries = new ArrayList<>();
        availableArmies = 0;
        playerArmies = 0;
    }

    /**
     * returns the whole list with the names countries of one player
     * @return returns the names of occupied countries
     */
    public List<String> getCountryNames() {
        return occupiedCountriesNames;
    }

    /**
     * returns the whole list with countries of one player
     * @return returns the occupied countries
     */
    public List<Country> getCountries() {
        return occupiedCountries;
    }

    /**
     * returns the Name of one country
     * @param i is the index of the wanted country name
     * @return returns the Name of a country
     */
    public String getCountryName(int i) {
        return occupiedCountriesNames.get(i);
    }
    
    /**
     * returns one country owned by a certain player
     * @param i is the index of the wanted country
     * @return returns the country
     */
    public Country getCountry(int i) {
        return occupiedCountries.get(i);
    }

    /**
     * adds countries and their names to the lists
     * @param countryName is the country name added
     * @param country is the country added
     */
    public void addCountryToList(String countryName, Country country) {
        this.occupiedCountriesNames.add(countryName);
        this.occupiedCountries.add(country);
    }
    
    /**
     * deletes countries and their names from the list
     * @param i is the index of the country which shall be deleted
     */
    public void deleteCountryToList(int i) {
        this.occupiedCountriesNames.remove(i);
        this.occupiedCountries.remove(i);
    }

    /**
     * returns the numbers of countries a player owns
     * @return the returns the numbers of countries a player owns 
     */
    public int numberOfCountries() {
        return this.occupiedCountriesNames.size();
    }

    // wertet aus, wie viele Armeen bei einem Angriff zur Verfuegung stehen
    /**
     * tests whether a player owns a whole continent
     * @param continent is the testet continent
     * @return true if a whole continent is owned
     */
    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountriesNames));
    }

    /**
     * tests whether another attack is possible
     * @return true if another attack is possible
     */
    public boolean attackPossible() {
        boolean countryQualifies = false;
        List<Country> possibleCountries = new LinkedList<>();
        for (Country country : occupiedCountries) {
            if (country.getArmiesInCountry() > 1) {
                countryQualifies = true;
                possibleCountries.add(country);
            }
        }
        if (countryQualifies) {
            for (Country country : possibleCountries) {
                for (String s : country.getNeighboringCountries()) {
                    if (occupiedCountriesNames.contains(s)) {
                        countryQualifies = false;
                    } else {
                        countryQualifies = true;
                        break;
                    }
                }
            }
        }
        return countryQualifies;
    }

    /**
     * gets the armies of a player
     * @return returns the armies of a player
     */
    public int getPlayerArmies() {
        return playerArmies;
    }

    /**
     * calculates how may new armies a player gets
     * @return returns the number of new armies
     */
    public int getNewArmies() {
        if (occupiedCountriesNames.size() < 9)
            availableArmies = 2;
        else
            availableArmies = (occupiedCountriesNames.size() / 3);
        for (Continent continent : Gameplay.getInstance().getContinents()) {
            if (continentComplete(continent))
                availableArmies += continent.getBonusArmies();
        }
        playerArmies += availableArmies;
        return availableArmies;
    }

    /**
     * calculates the number of available armies
     */
    private void setAvailableArmies() {
        availableArmies = (playerArmies - this.numberOfCountries());
    }

    /**
     * returns the number of available armies
     * @return returns the number of available armies
     */
    public int getArmiesAvailableToMove() {
        this.setAvailableArmies();
        return availableArmies;
    }

    /**
     * calculates the number of armies on the playing field
     * @param countries is the list of countries
     * @param playerNow is the player who's armies shall be calculated
     */
    private void setNumberOfArmies(List<Country> countries, Player playerNow) {
        playerArmies = 0;
    	for (int i = 0; i < 14; i++) {
            for (int j = 0; j <  playerNow.numberOfCountries(); j++) {
                if (this.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    this.playerArmies += countries.get(i).getArmiesInCountry();
                }
            }
        }
    }

    /**
     * returns the number of armies a player has got in the playing field
     * @param playerNow is the current player
     * @return returns the number of armies 
     */
    public int numberOfArmies(Player playerNow) {
        setNumberOfArmies(Gameplay.getInstance().getCountries(), playerNow);
        return playerArmies;
    }
    
    /**
     * sets the color of a player
     * @param color is the chosen color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * gets the color of a player
     * @return returns the color
     */
    public String getColor() {
        return color;
    }

    /**
     * sets the mission for a player
     * @param mission is the chosen mission
     */
    public void setPlayerMission(String mission) {
        this.playerMission = mission;
    }

    /**
     * gets the mission of a player
     * @return returns the mission of a player
     */
    public String getPlayerMission() {
        return playerMission;
    }

    /**
     * clears all values for a new game
     */
    public void emptyAll() {
        this.color = "black";
        this.playerMission = "none";
        this.playerArmies = 0;
        this.availableArmies = 0;
        this.occupiedCountriesNames.clear();
    }

    /**
     * overrides the toString function to print all information about a player
     */
    @Override
    public String toString() {
        return "Farbe: " + color + ", Mission: " + playerMission + ", Anzahl der Armeen: " + playerArmies;
    }

    /**
     * creates rgb codes depending on a players color
     * @param player is the current player
     * @return returns the rgb code
     */
    public static Color PlayerColorCode(Player player) {
        Color color;
        switch (player.getColor()) {
            case "blau":
                color = new java.awt.Color(0, 0, 255);
                break;
            case "rot":
                color = new java.awt.Color(255, 0, 0);
                break;
            case "lila":
                color = new java.awt.Color(157, 60, 156);
                break;
            case "pink":
                color = new java.awt.Color(255, 0, 255);
                break;
            case "grau":
                color = new java.awt.Color(76, 76, 76);
                break;
            default:
                color = new java.awt.Color(0, 0, 0);
        }
        return color;
    }
}
