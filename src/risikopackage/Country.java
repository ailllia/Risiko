package risikopackage;

import java.util.List;

public class Country {
    private String countryName;
    private List<String> neighboringCountries;
    private int armiesInCountry;
    private String colorOfOwner;

    public Country(String name, List<String> neighbors) {
        this.countryName = name;
        this.neighboringCountries = neighbors;
    }

    public void changeOwner(String color) {
        colorOfOwner = color;
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
        armiesInCountry--;
    }

    /**
     * Addiert einen Integer Wert auf die vorhandenen Armeen im Land.
     * zu benutzen wenn: Besatzer neue Armeen verteilt, Armeen umverteilt werden (nachdem Armeen auf 1 gesetzt wurden)
     */
    public void addArmies(int newArmies) {
        armiesInCountry += newArmies;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public int getArmiesInCountry() {
        return this.armiesInCountry;
    }

}
