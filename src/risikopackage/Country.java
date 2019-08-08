package risikopackage;

import java.awt.*;
import java.util.List;

public class Country {
    private String countryName;
    private List<String> neighboringCountries;
    private boolean isNeighbor;
    private int armiesInCountry;
    private Color colorOfOwnerCode;
    private String colorOfOwnerString;

    public Country(String name, List<String> neighbors) {
        this.countryName = name;
        this.neighboringCountries = neighbors;
    }

    public void changeOwner(String color) {
        colorOfOwnerString = color;
    }

    /**
     * Setzt Armeen im Land auf 1.
     * zu benutzen wenn: die Armeen am Spielanfang verteilt werden, Armeen umverteilt werden sollen, ein Land befreit wird
     */
    public void setArmies() {
        armiesInCountry = 1;
    }

    /**
     * Zieht eine Einheit von den Armeen ab.
     * zu benutzen wenn: beim Befreiungsversuch Armeen verloren gehen
     */
    public void loseArmy() {
        this.armiesInCountry--;
    }

    /**
     * Addiert eine Einheit auf die vorhandenen Armeen im Land.
     * zu benutzen wenn: Besatzer neue Armeen verteilt, Armeen umverteilt werden (nachdem Armeen auf 1 gesetzt wurden)
     */
    public void addArmy() {
        this.armiesInCountry++;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public int getArmiesInCountry() {
        return this.armiesInCountry;
    }

    public void setColorOfOwnerCode(Color rgbCodesOne) {
        this.colorOfOwnerCode = rgbCodesOne;
    }

    public Color getColorOfOwnerCode() {
        return this.colorOfOwnerCode;
    }

    public void setColorOfOwnerString(String colorOfOwnerString) {
        this.colorOfOwnerString = colorOfOwnerString;
    }

    public String getColorOfOwnerString() {
        return colorOfOwnerString;
    }

    public boolean isNeighbor(Country neighboringCountry) {
        for (String s : this.neighboringCountries) {
            if (s.equals(neighboringCountry.getCountryName())) {
                return true;
            }
        }
        return false;
    }
}
