package risikopackage;

import java.util.List;
import java.awt.*;

public class Country {
	private String countryName;
    private List<String> neighboringCountries;
    private int armiesInCountry;
    private Color colorOfOwner; //java.awt.Color? - ja

    public Country(String name, List<String> neighbors) {
        this.countryName = name;
        this.neighboringCountries = neighbors;
        // this.armiesInCountry = 1;
    }

    public void changeOwner(Color color) {
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
     * Addiert eine Einheit auf die vorhandenen Armeen im Land.
     * zu benutzen wenn: Besatzer neue Armeen verteilt, Armeen umverteilt werden (nachdem Armeen auf 1 gesetzt wurden)
     */
    public void addArmy() {
        armiesInCountry++;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public int getArmiesInCountry() {
        return this.armiesInCountry;
    }
   
    public Color getColorOfOwner() {
    	return this.colorOfOwner;
    }
    
    public void setColorOfOwner(Color rgbCodesOne)
    {
    	this.colorOfOwner = rgbCodesOne;
    }
}
