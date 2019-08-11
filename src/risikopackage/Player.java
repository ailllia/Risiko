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
     * Creates a new player.
     *
     * @param pcolor the chosen color for this player
     */
    public Player(String pcolor) {
        color = pcolor;
        this.occupiedCountriesNames = new ArrayList<>();
        this.occupiedCountries = new ArrayList<>();
        availableArmies = 0;
        playerArmies = 0;
    }

    /**
     * Returns the whole list with the names of the countries of the player.
     *
     * @return the names of occupied countries
     */
    public List<String> getCountryNames() {
        return occupiedCountriesNames;
    }

    /**
     * Returns the whole list with countries of the player.
     *
     * @return the occupied countries
     */
    public List<Country> getCountries() {
        return occupiedCountries;
    }

    /**
     * Returns the name of a country owned by the player.
     *
     * @param i the index of the wanted country name
     * @return the name of a country
     */
    public String getCountryName(int i) {
        return occupiedCountriesNames.get(i);
    }

    /**
     * Returns a country owned by the player.
     *
     * @param i the index of the wanted country
     * @return the country
     */
    public Country getCountry(int i) {
        return occupiedCountries.get(i);
    }

    /**
     * Adds countries and their names to the lists.
     *
     * @param countryName the country name to be added
     * @param country     the country to be added
     */
    public void addCountryToList(String countryName, Country country) {
        this.occupiedCountriesNames.add(countryName);
        this.occupiedCountries.add(country);
    }

    /**
     * Deletes a country and its name from the lists.
     *
     * @param i the index of the country to be deleted
     */
    public void deleteCountryFromList(int i) {
        this.occupiedCountriesNames.remove(i);
        this.occupiedCountries.remove(i);
    }

    /**
     * Returns the number of countries a player owns.
     *
     * @return the number of countries a player owns
     */
    public int numberOfCountries() {
        return this.occupiedCountriesNames.size();
    }

    /**
     * Tests whether a player owns a whole continent.
     *
     * @param continent the continent to be tested
     * @return true if the whole continent is owned; false otherwise
     */
    public boolean continentComplete(Continent continent) {
        return (continent.completeContinent(occupiedCountriesNames));
    }

    /**
     * Tests whether another attack is possible.
     *
     * @return true if another attack is possible; false otherwise
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
     * Gets the number of armies of a player.
     *
     * @return the number of armies of a player
     */
    public int getPlayerArmies() {
        return playerArmies;
    }

    /**
     * Calculates the number of new armies a player gets and adds them to playerArmies.
     *
     * @return the number of new armies
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
     * Calculates the number of available armies.
     */
    private void setAvailableArmies() {
        availableArmies = (playerArmies - this.numberOfCountries());
    }

    /**
     * Returns the number of available armies.
     *
     * @return the number of available armies
     */
    public int getArmiesAvailableToMove() {
        this.setAvailableArmies();
        return availableArmies;
    }

    /**
     * Calculates the number of armies on the playing field.
     *
     * @param countries the list of countries
     * @param playerNow the player whose armies get calculated
     */
    private void setNumberOfArmies(List<Country> countries, Player playerNow) {
        playerArmies = 0;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < playerNow.numberOfCountries(); j++) {
                if (this.getCountryName(j).equals(countries.get(i).getCountryName())) {
                    this.playerArmies += countries.get(i).getArmiesInCountry();
                }
            }
        }
    }

    /**
     * Returns the number of armies a player has got in the playing field.
     *
     * @param playerNow the current player
     * @return the number of armies
     */
    public int numberOfArmies(Player playerNow) {
        setNumberOfArmies(Gameplay.getInstance().getCountries(), playerNow);
        return playerArmies;
    }

    /**
     * Sets the color of a player.
     *
     * @param color the color to become the color of the player
     * @see getColor()
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the color of a player.
     *
     * @return the color of the player
     * @see setColor(String color)
     */
    public String getColor() {
        return color;
    }

    /**
     * sets the mission for a player
     *
     * @param mission is the chosen mission
     */
    public void setPlayerMission(String mission) {
        this.playerMission = mission;
    }

    /**
     * gets the mission of a player
     *
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
     *
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
