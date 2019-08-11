package risikopackage;

import java.awt.*;
import java.util.List;

public class Country {
    private String countryName;
    private List<String> neighboringCountries;
    private int armiesInCountry;
    private Color colorOfOwnerCode;
    private String colorOfOwnerString;

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
     * @see loseArmy()
     * @see addArmy()
     * @see getArmiesInCountry()
     */
    public void setArmies() {
        armiesInCountry = 1;
    }

    /**
     * Reduces the value of armiesInCountry by 1.
     *
     * @see setArmies()
     * @see addArmy()
     * @see getArmiesInCountry()
     */
    public void loseArmy() {
        this.armiesInCountry--;
    }

    /**
     * Raises the value of armiesInCountry by 1.
     *
     * @see setArmies()
     * @see loseArmy()
     * @see getArmiesInCountry()
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
     * Gets the number of armies in the country
     *
     * @return the number of armies in the country
     * @see setArmies()
     * @see loseArmy()
     * @see addArmy()
     */
    public int getArmiesInCountry() {
        return this.armiesInCountry;
    }

    /**
     * Sets the color of the owner as code.
     *
     * @param rgbCodesOne the color to become the new color of the owner
     * @see getColorOfOwnerCode()
     * @see getColorOfOwnerString()
     * @see setColorOfOwnerString(String colorOfOwnerString)
     */
    public void setColorOfOwnerCode(Color rgbCodesOne) {
        this.colorOfOwnerCode = rgbCodesOne;
    }

    /**
     * Gets the code of the color of the owner
     *
     * @return the code of the color of the owner
     * @see setColorOfOwnerCode(Color rgbCodesOne)
     * @see setColorOfOwnerString(String colorOfOwnerString)
     * @see getColorOfOwnerString()
     */
    public Color getColorOfOwnerCode() {
        return this.colorOfOwnerCode;
    }

    /**
     * Sets the color of the owner as string.
     *
     * @param colorOfOwnerString the color to become the new color of the owner
     * @see getColorOfOwnerString()
     * @see getColorOfOwnerCode()
     * @see setColorOfOwnerCode(Color rgbCodesOne)
     */
    public void setColorOfOwnerString(String colorOfOwnerString) {
        this.colorOfOwnerString = colorOfOwnerString;
    }

    /**
     * Gets the string of the color of the owner
     *
     * @return the string of the color of the owner
     * @see setColorOfOwnerString(String colorOfOwnerString)
     * @see setColorOfOwnerCode(Color rgbCodesOne)
     * @see getColorOfOwnerCode()
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
