package risikopackage;

import java.awt.Color;
import java.util.List;

/**
 * Country handles data concerning the structure and functions of countries in the game Risc.
 *
 * @author Swantje Wiechmann
 */
public class Country {
    private String countryName, colorOfOwnerString;
    private List<String> neighboringCountries;
    private int armiesInCountry;
    private Color colorOfOwnerCode;

    /**
     * Creates a new Country.
     *
     * @param name      the name of the country
     * @param neighbors the list of neighboring countries
     */
    public Country(String name, List<String> neighbors) {
        this.countryName = name;
        this.neighboringCountries = neighbors;
    }

    /**
     * Sets value of colorOfOwnerString.
     *
     * @param color the color to become the new value of colorOfOwnerString
     */
    public void changeOwner(String color) {
        colorOfOwnerString = color;
    }

    /**
     * Sets value of armiesInCountry to 1.
     *
     * @see Country#loseArmy()
     * @see Country#addArmy()
     * @see Country#getArmiesInCountry()
     */
    public void setArmies() {
        armiesInCountry = 1;
    }

    /**
     * Reduces the value of armiesInCountry by 1.
     *
     * @see Country#setArmies()
     * @see Country#addArmy()
     * @see Country#getArmiesInCountry()
     */
    public void loseArmy() {
        this.armiesInCountry--;
    }

    /**
     * Raises the value of armiesInCountry by 1.
     *
     * @see Country#setArmies()
     * @see Country#loseArmy()
     * @see Country#getArmiesInCountry()
     */
    public void addArmy() {
        this.armiesInCountry++;
    }

    /**
     * Gets list of the neighboring countries.
     *
     * @return the list of neighboring countries
     */
    public List<String> getNeighboringCountries() {
        return neighboringCountries;
    }

    /**
     * Gets the name of the country.
     *
     * @return the name of the country
     */
    public String getCountryName() {
        return this.countryName;
    }

    /**
     * Gets the number of armies in the country.
     *
     * @return the number of armies in the country
     * @see Country#setArmies()
     * @see Country#loseArmy()
     * @see Country#addArmy()
     */
    public int getArmiesInCountry() {
        return this.armiesInCountry;
    }

    /**
     * Sets the color of the owner as code.
     *
     * @param rgbCodesOne the color to become the new color of the owner
     * @see Country#getColorOfOwnerCode()
     * @see Country#getColorOfOwnerString()
     * @see Country#setColorOfOwnerString(String colorOfOwnerString)
     */
    public void setColorOfOwnerCode(Color rgbCodesOne) {
        this.colorOfOwnerCode = rgbCodesOne;
    }

    /**
     * Gets the code of the color of the owner.
     *
     * @return the code of the color of the owner
     * @see Country#setColorOfOwnerCode(Color rgbCodesOne)
     * @see Country#setColorOfOwnerString(String colorOfOwnerString)
     * @see Country#getColorOfOwnerString()
     */
    public Color getColorOfOwnerCode() {
        return this.colorOfOwnerCode;
    }

    /**
     * Sets the color of the owner as string.
     *
     * @param colorOfOwnerString the color to become the new color of the owner
     * @see Country#getColorOfOwnerString()
     * @see Country#getColorOfOwnerCode()
     * @see Country#setColorOfOwnerCode(Color rgbCodesOne)
     */
    public void setColorOfOwnerString(String colorOfOwnerString) {
        this.colorOfOwnerString = colorOfOwnerString;
    }

    /**
     * Gets the string of the color of the owner.
     *
     * @return the string of the color of the owner
     * @see Country#setColorOfOwnerString(String colorOfOwnerString)
     * @see Country#setColorOfOwnerCode(Color rgbCodesOne)
     * @see Country#getColorOfOwnerCode()
     */
    public String getColorOfOwnerString() {
        return colorOfOwnerString;
    }

    /**
     * Returns whether two countries are neighbors.
     *
     * @param neighboringCountry the country to be checked of neighborhood
     * @return true if the countries are neighbors; false otherwise
     */
    public boolean isNeighbor(Country neighboringCountry) {
        for (String s : this.neighboringCountries) {
            if (s.equals(neighboringCountry.getCountryName())) {
                return true;
            }
        }
        return false;
    }
}
